package org.project.myn.security.config;

import lombok.extern.log4j.Log4j2;
import org.project.myn.security.handler.ClubLoginSuccessHandler;
import org.project.myn.security.service.ClubUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClubUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

// -------------------- ClubUserDetailsService를 사용하기 위해 주석 처리 --------------------
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user1")
//                .password("$2a$10$eQh0nq.Z1WAzkCbnlxFR2eLNl7M1aBr511Xj7UVVi.THIruAAZRJ6")
//                .roles("USER");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    .antMatchers("/sample/all").permitAll()
                    .antMatchers("/sample/member").hasRole("USER")
                .and()
                    .logout()
                        .logoutSuccessUrl("/");

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