package com.ll.backend.domain.admin.service;

import com.ll.backend.domain.admin.entity.Admin;
import com.ll.backend.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    //관리자 회원가입
    public Admin registerAdmin(String username, String password){
        if (adminRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        return adminRepository.save(admin);
    }

    //관리자 로그인 검증
    public Admin validateAdmin(String username, String password){
        Optional<Admin> oa=adminRepository.findByUsername(username);
        if(oa.isEmpty()){
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        Admin admin=oa.get();
        if(!passwordEncoder.matches(password,admin.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return admin;
    }

    //관리자 조회
    public Optional<Admin> getAdmin(String username){
        return adminRepository.findByUsername(username);
    }

    public Optional<Admin> findByUsername(String user1) {
        return adminRepository.findByUsername(user1);
    }

    public long count() {
        return adminRepository.count();
    }
}
