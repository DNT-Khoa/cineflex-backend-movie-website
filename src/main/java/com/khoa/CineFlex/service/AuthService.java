package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.*;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.model.VerificationToken;
import com.khoa.CineFlex.repository.UserRepository;
import com.khoa.CineFlex.repository.VerificationTokenRepository;
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
import java.time.temporal.ChronoUnit;
import java.util.UUID;

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
    private final MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;

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

    @Transactional
    public void forgotPassword(String email) {
        User user = this.userRepository.findByEmail(email);

        if (user == null) {
            throw new CineFlexException("NO_EXIST_EMAIL");
        }

        VerificationToken verificationToken = new VerificationToken();
        String token = UUID.randomUUID().toString();
        verificationToken.setToken(token);
        verificationToken.setEmail(email);
        verificationToken.setExpiryDate(Instant.now());
        this.verificationTokenRepository.save(verificationToken);

        ResetPasswordEmail resetPasswordEmail = new ResetPasswordEmail();
        resetPasswordEmail.setSubject("Password reset for CineFlex");
        resetPasswordEmail.setBody("Your CineFlex password can be reset by clicking the button below. If you did not request a new password, please ignore this email");
        resetPasswordEmail.setRecipient(email);
        resetPasswordEmail.setJoinLink("http://localhost:4502/home/resetPassword/" + token);

        this.mailService.sendMail(resetPasswordEmail);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        VerificationToken verificationToken = this.verificationTokenRepository.findByToken(resetPasswordRequest.getToken());

        if (verificationToken == null) {
            throw new CineFlexException("INVALID_TOKEN");
        }

        checkToken(verificationToken);

        User user = this.userRepository.findByEmail(verificationToken.getEmail());
        user.setPassword(this.bCryptPasswordEncoder.encode(resetPasswordRequest.getPassword()));

        this.userRepository.save(user);
    }

    private void checkToken(VerificationToken verificationToken) {
        if (verificationToken.getExpiryDate().plus(24, ChronoUnit.HOURS).isBefore(Instant.now())) {
            throw new CineFlexException("EXPIRED_TOKEN");
        }
    }
}
