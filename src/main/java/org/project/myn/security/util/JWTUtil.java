package org.project.myn.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

// 사용 1) 인증 성공 시 JWT 문자열을 만들어서 클라이언트에 전송
// 사용 2) 클라이언트가 보낸 토큰의 값을 검증하는 경우
@Log4j2
public class JWTUtil {

    // Signature 생성의 기준이 되는 문자열
    private String secretKey = "myn12345678";

    // 유효 기간: 1 days
    private long expire = 60 * 24;

    // JWT 토큰 생성
    public String generateToken(String content) throws Exception {
        return Jwts.builder()
                .setIssuedAt(new Date())    // 토큰 발생 시간 정보
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))    // 토큰 만료 시간
                .claim("sub", content)    // sub라는 이름을 가지는 Claim 생성: 사용자 이메일 주소를 입력하여 다시 사용할 수 있도록 구성
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    // 인코딩된 문자열에서 원하는 값을 추출 및 검증
    // JWT가 이미 만료 기간이 지난 것이라면 여기서 Exception을 발생하게 됨
    // 또한 위에서 'sub'으로 저장한 이메일을 반환하게 딤
    public String validateAndExtract(String tokenStr) throws Exception {
        String contentValue = null;

        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser().
                    setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(tokenStr);
            log.info(defaultJws);
            log.info(defaultJws.getBody().getClass());
            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();

            log.info("----------------------------------------");
            contentValue = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return contentValue;
    }

}
