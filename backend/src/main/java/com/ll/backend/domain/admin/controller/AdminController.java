package com.ll.backend.domain.admin.controller;

import com.ll.backend.domain.admin.dto.AdminRequestDto;
import com.ll.backend.domain.admin.entity.Admin;
import com.ll.backend.domain.admin.service.AdminService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
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
            // 응답을 JSON 형식으로 반환
            return ResponseEntity.ok().body(null);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/checkSession")
    public ResponseEntity<String> checkSession(@RequestBody Map<String, String> request,
                                               HttpSession session,
                                               HttpServletResponse response) {
        String sessionId = request.get("sessionId");

        if (sessionId == null || !sessionId.equals(session.getId())) {
            // 세션이 유효하지 않으면 로그인 페이지로
            session.invalidate();
            // 새 세션 생성
            String newSessionId = session.getId(); // 새 세션 ID

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok("");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logoutAdmin(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().body("");
    }
}



