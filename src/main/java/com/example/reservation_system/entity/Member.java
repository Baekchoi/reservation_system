package com.example.reservation_system.entity;

import com.example.reservation_system.entity.constants.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
