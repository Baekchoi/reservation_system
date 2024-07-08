package com.example.reservation_system.controller;

import com.example.reservation_system.entity.Member;
import com.example.reservation_system.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입 기능
    @PostMapping("/register")
    public Member registerMember(@RequestBody Member member) {
        return memberService.registerMember(member);
    }
}
