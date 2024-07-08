package com.example.reservation_system.controller;

import com.example.reservation_system.entity.Member;
import com.example.reservation_system.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public void login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {
        Optional<Member> optionalMember = memberRepository.findByEmail(loginRequest.getEmail());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(member.getEmail(), member.getPassword(), authorities);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("로그인 성공");
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("비밀번호가 일치하지 않습니다.");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("사용자를 찾을 수 없습니다.");
        }
    }

    private static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
