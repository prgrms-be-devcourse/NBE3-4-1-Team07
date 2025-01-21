package com.ll.backend.domain.admin.controller;

import com.ll.backend.domain.admin.dto.AdminRequestDto;
import com.ll.backend.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final AuthenticationManager authenticationManager;

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
//    @GetMapping("/login")
//    public ResponseEntity<String> loginAdmin(@RequestBody AdminRequestDto request) {
//        try {
//            Admin admin = adminService.validateAdmin(request.getUsername(), request.getPassword());
//            return ResponseEntity.ok("Welcome, " + admin.getUsername());
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginAdmin(@RequestBody AdminRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Map<String, String> response = new HashMap<>();
            response.put("message", "로그인 성공");
            response.put("username", authentication.getName());
            response.put("role", "ADMIN");
            
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "로그인 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }



}



