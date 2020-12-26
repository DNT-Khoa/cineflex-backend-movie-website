package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.*;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.AdminInvitationToken;
import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.repository.*;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AdminInvitationTokenRepository adminInvitationTokenRepository;
    private final MailService mailService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MovieRepository movieRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void inviteAdmin(String email) throws MailException{
        // First add a verification to the database

        String token = this.generateAdminInvitationToken(email);

        InviteAdminEmail inviteAdminEmail = new InviteAdminEmail();
        inviteAdminEmail.setSubject("Yo yo! You have been invited to join CineFlex administration team!");
        inviteAdminEmail.setRecipient(email);
        inviteAdminEmail.setBody("We have all agreed to add you to our CineFlex team. You can join us by clicking the button below and finish some required tasks!");
        inviteAdminEmail.setJoinLink("https://cineflex-angular-frontend.herokuapp.com/home/adminCredentials/" + token);

        this.mailService.sendMail(inviteAdminEmail);
    }

    @Transactional
    public boolean registerAdmin(RegisterAdminRequest registerAdminRequest) {
        AdminInvitationToken adminInvitationToken = this.adminInvitationTokenRepository.findByToken(registerAdminRequest.getToken());
        if (adminInvitationToken == null) {
            throw new CineFlexException("INVALID_TOKEN");
        }

        String email = adminInvitationToken.getEmail();
        User check = this.userRepository.findAdminByEmail(email);
        if (check != null) {
            throw new CineFlexException("ADMIN_ALREADY_REGISTERED");
        }

        checkAdminInvitationToken(adminInvitationToken);

        // If everything is okay then register the admin
        String firstName = registerAdminRequest.getFirstName();
        String lastName = registerAdminRequest.getLastName();
        String password = registerAdminRequest.getPassword();

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName(firstName);
        registerRequest.setLastName(lastName);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);

        this.authService.signup(registerRequest, "Admin");

        return true;
    }

    @Transactional(readOnly = true)
    public UserDto getAdminByEmail(String email) {
        return this.userMapper.userToDto(this.userRepository.findAdminByEmail(email));
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
    public String generateAdminInvitationToken(String email) {
        String token = UUID.randomUUID().toString();
        AdminInvitationToken adminInvitationToken = new AdminInvitationToken();
        adminInvitationToken.setEmail(email);
        adminInvitationToken.setToken(token);
        adminInvitationToken.setExpiryDate(Instant.now());

        this.adminInvitationTokenRepository.save(adminInvitationToken);

        return token;
    }

    private void checkAdminInvitationToken(AdminInvitationToken adminInvitationToken) {
        // Check if token is expired (over 24 hours)
        if (adminInvitationToken.getExpiryDate().plus(24, ChronoUnit.HOURS).isBefore(Instant.now())) {
            throw new CineFlexException("EXPIRED_TOKEN");
        }
    }

    @Transactional
    public UserDto editAdminDetails(UserEditRequest userEditRequest) {
        if (!userEditRequest.getOldEmail().equals(userEditRequest.getNewEmail())) {
            User checkUser = this.userRepository.findByEmail(userEditRequest.getNewEmail());

            if (checkUser != null) {
                throw new CineFlexException("Email already exits in the database");
            }
        }

        User user = this.userRepository.findByEmail(userEditRequest.getOldEmail());
        user.setFirstName(userEditRequest.getFirstName());
        user.setLastName(userEditRequest.getLastName());
        user.setEmail(userEditRequest.getNewEmail());

        return this.userMapper.userToDto(this.userRepository.save(user));
    }

    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword()));
        } catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        User user = this.userRepository.findByEmail(changePasswordRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));

        this.userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(String email, String password, String refreshToken) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        User user = this.userRepository.findByEmail(email);

        // Delete all tokens in the admin invitation token table
        this.adminInvitationTokenRepository.deleteAllByEmail(email);

        this.userRepository.deleteByEmail(email);
    }

    @Transactional(readOnly = true)
    public int getCountAllUsers() {
        return this.userRepository.countAllUsers();
    }

    @Transactional(readOnly = true)
    public int getCountAllMovies() {
        return this.movieRepository.getCountAllMovies();
    }

    @Transactional(readOnly = true)
    public int getCountAllPosts() {
        return this.postRepository.getCountAllPosts();
    }

    @Transactional(readOnly = true)
    public int getCountAllComments() {
        return this.commentRepository.getCountAllComments();
    }
}
