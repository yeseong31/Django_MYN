package org.project.myn.security.handler;

import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// ApiLoginFilter에서 인증에 실패하는 경우 API 서버는 JSON 결과가 전송되도록 해야 하고,
// 성공하는 경우에는 클라이언트가 보관할 인증 토큰이 전송되어야 함
@Log4j2
public class ApiLoginFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.info("Login fail handler........................................");
        log.info(exception.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // return json
        response.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        String message = exception.getMessage();
        json.put("code", "401");    // 인증 실패 시 401 상태 코드 반환
        json.put("message", message);

        PrintWriter out = response.getWriter();
        out.print(json);

    }

}
