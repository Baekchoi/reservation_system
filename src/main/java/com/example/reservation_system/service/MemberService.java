package com.example.reservation_system.service;

import com.example.reservation_system.entity.Member;
import com.example.reservation_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 기능
    public Member registerMember(Member member) {
        // 이메일로 중복가입 제외
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }
        // 비밀번호 암호화 확인 위한 로그
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        System.out.println("Encoded password: " + encodedPassword);

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

}
