package org.project.myn.security.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 특정한 URL로 외부에서 로그인이 가능하도록 하고, 로그인 성공 시 클라이언트가 Authorization 헤더의 값으로 데이터 전송
// '/api/login'을 이용하여 외부의 클라이언트가 자신의 아이디와 패스워드로 로그인한다로 가넝함
@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    public ApiLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        log.info("-------------------- ApiLoginFilter --------------------");
        log.info("attemptAuthentication");

        String email = request.getParameter("email");
        String pw = "1111";

        if (email == null) {
            throw new BadCredentialsException("Email cannot be null!!");
        }

        return null;
    }

}
