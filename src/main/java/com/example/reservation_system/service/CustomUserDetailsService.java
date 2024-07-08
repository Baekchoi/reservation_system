package com.example.reservation_system.service;

import com.example.reservation_system.entity.Member;
import com.example.reservation_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found with email: " + email));
        System.out.println("Loaded member: " + member.getEmail() + ", " + member.getPassword());
        return new org.springframework.security.core.userdetails.User(member.getEmail(), member.getPassword(), getAuthorities(member));
    }

    private List<SimpleGrantedAuthority> getAuthorities(Member member) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
    }

}
