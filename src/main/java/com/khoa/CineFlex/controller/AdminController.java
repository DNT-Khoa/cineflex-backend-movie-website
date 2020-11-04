package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

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
}
