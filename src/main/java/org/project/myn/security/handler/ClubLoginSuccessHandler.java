package org.project.myn.security.handler;

import lombok.extern.log4j.Log4j2;
import org.project.myn.security.dto.ClubAuthMemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 로그인 성공 이후의 처리를 담당
@Log4j2
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    // 리다이렉트 처리
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final PasswordEncoder passwordEncoder;

    public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("----------------------------------------");
        log.info("onAuthenticationSuccess...");

        ClubAuthMemberDTO authMember = (ClubAuthMemberDTO) authentication.getPrincipal();

        boolean fromSocial = authMember.isFromSocial();
        log.info("Need Modify Member? " + fromSocial);

        boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());
        if (fromSocial && passwordResult) {
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
        }
    }
}
