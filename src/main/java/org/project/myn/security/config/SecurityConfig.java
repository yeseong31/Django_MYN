package org.project.myn.security.config;

import lombok.extern.log4j.Log4j2;
import org.project.myn.security.filter.ApiCheckFilter;
import org.project.myn.security.filter.ApiLoginFilter;
import org.project.myn.security.handler.ClubLoginSuccessHandler;
import org.project.myn.security.service.ClubUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)  // 어노테이션 기반의 접근 제한
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClubUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 인가 및 인증에 문제가 있을 때 로그인 화면 출력
        http.formLogin();
        // CSRF 비활성화
        http.csrf().disable();
        // 소셜 로그인
        http.oauth2Login().successHandler(successHandler());
        // 자동 로그인
        http.rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7).userDetailsService(userDetailsService);  // 7days
        // ApiFilter 위치 조정
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception {
        // 로그인은 '/api/login' 경로를 통해 진행
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
        apiLoginFilter.setAuthenticationManager(authenticationManager());
        return apiLoginFilter;
    }

    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/clubs/**/*");
    }
}