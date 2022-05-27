package org.project.myn.security.config;

import lombok.extern.log4j.Log4j2;
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

        http
                .csrf().disable()

                // Spring Security HSTS
                .headers()
                    .httpStrictTransportSecurity()
                        .maxAgeInSeconds(31536000)    // 브라우저가 HSTS 정책을 적용할 시간 설정
                        .includeSubDomains(true)      // 도메인의 서브 도메인에도 HSTS 설정을 적용
                        .preload(true);               // HSTS 헤더가 없더라도 HTTP 요청을 HTTPS로 강제 변환하여 전송함

//                .and()
//                    .authorizeRequests()
//                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
//                    .antMatchers("/sample/all").permitAll()
//                    .antMatchers("/sample/member").hasRole("USER")
//                .and()
//                    .logout()
//                        .logoutSuccessUrl("/");

        // 인가 및 인증에 문제가 있을 때 로그인 화면 출력
        http.formLogin();
        // 소셜 로그인
        http.oauth2Login().successHandler(successHandler());
        // 자동 로그인
        http.rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7).userDetailsService(userDetailsService);  // 7days
    }

    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }
}