package com.example.reservation_system.security;

import com.example.reservation_system.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfig) throws Exception {
        return authenticationConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        // 가입은 누구나 접근 가능
                        .requestMatchers("/api/members/register").permitAll()
                        .requestMatchers("/api/members/login").permitAll()
                        // 매장관련 기능은 파트너권한만 접근 가능
                        .requestMatchers("/api/stores/**").hasRole("PARTNER")
                        // 리뷰 작성, 수정 : 사용자 / 리뷰 삭제 : 사용자, 파트너
                        .requestMatchers(HttpMethod.POST, "/api/reviews/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/reviews/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasAnyRole("USER", "PARTNER")
                        .requestMatchers("/api/reservations/**").authenticated()
                        .anyRequest().authenticated())
                /*
                 * 처음 시도 - spring security의 formLogin으로 로그인을 구현하려고 했으나
                 * 올바른 데이터로 시도해도 계속해서 bad credentials 에러가 발생 .. 해결방안을 찾지 못함
                 *
                 * 암호화되어 데이터베이스에 저장된 비밀번호와 입력된 비밀번호를 직접 비교하기 위해 formLogin 비활성화 후
                 * AuthController에 직접 로그인 구현
                 * ㄴ 403 에러 .. /login permitAll을 작성하지 않아서 발생한 오류인 줄 알았지만 작성해도 해결되지 않음
                 *    검색을 통해 securitycontext로 인증된 사용자를 인식하는 코드 작성해 보았지만 해결되지 않음
                 *
                 * 시간이 부족해 로그인을 해결하지 못했습니다 .. ㅠㅠ
                 * 로그인 구현에 관해 많은 공부가 필요할 것 같습니다 제출 후에도 계속 해결방안을 찾아보려고 합니다
                 */
                .formLogin(formLogin -> formLogin.disable());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

}
