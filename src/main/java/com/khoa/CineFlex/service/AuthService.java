package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.AuthenticationResponse;
import com.khoa.CineFlex.DTO.LoginRequest;
import com.khoa.CineFlex.DTO.RefreshTokenRequest;
import com.khoa.CineFlex.DTO.RegisterRequest;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final Environment environment;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public boolean signup(RegisterRequest registerRequest, String role) {

        User user = userMapper.registerRequestToUser(registerRequest);

        // Check if email already exits
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }

        user.setCreated(Instant.now());
        user.setEnabled(true);
        user.setRole(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));


        userRepository.save(user);
        return true;

    }

    @Transactional
    public AuthenticationResponse login(LoginRequest loginRequest) throws Exception{
        User returnUser = userRepository.findByEmail(loginRequest.getEmail());

        if (returnUser == null) {
            throw new CineFlexException("EMAIL_NOT_EXISTS");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String role = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")) ? "User" : "Admin";
        String token = jwtUtil.generateToken(userDetails);

        return new AuthenticationResponse(token, refreshTokenService.generateRefreshToken().getToken(), Instant.now().plusMillis(Long.parseLong(environment.getProperty("token.expiration_time"))), loginRequest.getEmail(), role);
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshTokenRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return new AuthenticationResponse(token, refreshTokenRequest.getRefreshToken(), Instant.now().plusMillis(Long.parseLong(environment.getProperty("token.expiration_time"))), refreshTokenRequest.getEmail(), refreshTokenRequest.getRole());
    }
}
