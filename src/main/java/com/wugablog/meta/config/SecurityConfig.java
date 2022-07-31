package com.wugablog.meta.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration // 빈등록
@EnableWebSecurity // 시큐리티 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소를 접근 권한 및 인증을 미리 체크
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoderPassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // csrf 토큰 비활성화 (테스트 시 걸어두는게 좋음)
            .authorizeRequests()
            .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**", "/test/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/auth/loginForm")
            .loginProcessingUrl("/auth/loginProc")
            .defaultSuccessUrl("/");
        return http.build();
    }

}
