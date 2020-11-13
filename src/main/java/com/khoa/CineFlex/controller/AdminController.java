package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.ChangePasswordRequest;
import com.khoa.CineFlex.DTO.RegisterAdminRequest;
import com.khoa.CineFlex.DTO.UserDto;
import com.khoa.CineFlex.DTO.UserEditRequest;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.service.AdminService;
import com.khoa.CineFlex.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @ResponseBody
    @GetMapping(value = "/admin", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAdminByEmail(@RequestParam("email") String email) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.adminService.getAdminByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/admin/countUsers")
    public ResponseEntity<?> getCountAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.adminService.getCountAllUsers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/admin/inviteAdmin")
    public ResponseEntity<?> inviteAdmin(@RequestParam("email") String email) {
        try {
            this.adminService.inviteAdmin(email);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (MailException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/api/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterAdminRequest registerAdminRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.adminService.registerAdmin(registerAdminRequest));
        } catch (CineFlexException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllAdmins() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.adminService.getAllAdmins());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/admin/search")
    public ResponseEntity<?> searchAdminByEmailKey(@RequestParam("key") String key) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.adminService.searchAdminByEmailKey(key));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/admin/edit")
    public ResponseEntity<?> editAdminDetails(@RequestBody UserEditRequest userEditRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.adminService.editAdminDetails(userEditRequest));
        } catch (CineFlexException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/admin/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            this.adminService.changePassword(changePasswordRequest);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/deleteAccount")
    public ResponseEntity<?> deleteAccount(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("refreshToken") String refreshToken) {
        try {
            this.adminService.deleteAccount(email, password, refreshToken);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
