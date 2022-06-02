package org.project.myn.security.filter;

import lombok.extern.log4j.Log4j2;
import org.project.myn.security.dto.ClubAuthMemberDTO;
import org.project.myn.security.util.JWTUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// 특정한 URL로 외부에서 로그인이 가능하도록 하고, 로그인 성공 시 클라이언트가 Authorization 헤더의 값으로 데이터 전송
// '/api/login'을 이용하여 외부의 클라이언트가 자신의 아이디와 패스워드로 로그인한다로 가정함
@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final JWTUtil jwtUtil;

    public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil) {
        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        log.info("-------------------- ApiLoginFilter --------------------");
        log.info("attemptAuthentication");

        String email = request.getParameter("email");
        String pw = request.getParameter("password");
        log.info("attemptAuthentication --- email: " + email);
        log.info("attemptAuthentication --- password: " + pw);

        // 인증 진행
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, pw);

        return getAuthenticationManager().authenticate(token);
    }

    // 인증(로그인) 성공 처리
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("---------------------- ApiLoginFilter ----------------------");
        log.info("successfulAuthentication: " + authResult);

        // authResult는 인증에 성공한 사용자의 인증 정보를 가지는 Authentication 객체
        log.info(authResult.getPrincipal());

        // email address
        String email = ((ClubAuthMemberDTO) authResult.getPrincipal()).getUsername();

        String token = null;
        try {
            token = jwtUtil.generateToken(email);

            response.setContentType("text/plain");
            response.getOutputStream().write(token.getBytes(StandardCharsets.UTF_8));

            log.info(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
