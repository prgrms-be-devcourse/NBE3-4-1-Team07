package com.ll.backend.domain.admin.controller;

import com.ll.backend.domain.admin.dto.AdminRequestDto;
import com.ll.backend.domain.admin.entity.Admin;
import com.ll.backend.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin") //
public class AdminController {
    private final AdminService adminService;

    //회원가입
    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminRequestDto request) {
        try {
            adminService.registerAdmin(request.getUsername(), request.getPassword());
            return ResponseEntity.ok("Admin registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody AdminRequestDto request) {
        try {
            Admin admin = adminService.validateAdmin(request.getUsername(), request.getPassword());
            return ResponseEntity.ok("Welcome, " + admin.getUsername());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}



