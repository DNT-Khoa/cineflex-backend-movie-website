package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.ChangePasswordRequest;
import com.khoa.CineFlex.DTO.InviteAdminEmail;
import com.khoa.CineFlex.DTO.UserDto;
import com.khoa.CineFlex.DTO.UserEditRequest;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.AdminInvitationToken;
import com.khoa.CineFlex.model.Movie;
import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.model.VerificationToken;
import com.khoa.CineFlex.repository.AdminInvitationTokenRepository;
import com.khoa.CineFlex.repository.UserRepository;
import com.khoa.CineFlex.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AdminInvitationTokenRepository adminInvitationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void inviteAdmin(String email) throws MailException{
        // First add a verification to the database

        String token = this.generateAdminInvitationToken();

        InviteAdminEmail inviteAdminEmail = new InviteAdminEmail();
        inviteAdminEmail.setSubject("Yo yo! You have been invited to join CineFlex administration team!");
        inviteAdminEmail.setRecipient(email);
        inviteAdminEmail.setBody("We have all agreed to add you to our CineFlex team. You can join us by clicking the button below and finish some required tasks!");
        inviteAdminEmail.setJoinLink("http://localhost:8080/home/adminCredentials/" + token);

        this.mailService.sendMail(inviteAdminEmail);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllAdmins() {
        List<User> users = this.userRepository.findAllAdmins();

        return this.userMapper.listUsersToListDtos(users);
    }

    @Transactional(readOnly = true)
    public List<UserDto> searchAdminByEmailKey(String key) {
        return this.userMapper.listUsersToListDtos(this.userRepository.searchAdminByEmailKey(key));
    }

    @Transactional
    public String generateAdminInvitationToken() {
        String token = UUID.randomUUID().toString();
        AdminInvitationToken adminInvitationToken = new AdminInvitationToken();
        adminInvitationToken.setToken(token);
        adminInvitationToken.setExpiryDate(Instant.now());

        this.adminInvitationTokenRepository.save(adminInvitationToken);

        return token;
    }

}
