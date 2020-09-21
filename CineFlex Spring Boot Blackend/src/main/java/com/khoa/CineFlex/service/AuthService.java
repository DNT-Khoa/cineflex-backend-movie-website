package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.RegisterRequest;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

    @Transactional
    public boolean signup(RegisterRequest registerRequest, String role) {

//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

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


}
