package org.project.myn.security.dto;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("----------------------------------------");
        log.info("userRequest: " + userRequest);    // org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest 객체

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName: " + clientName);      // Google로 출력됨
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("========================================");
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + ": " + v);                 // sub, picture, email, email_verified, ENAIL 등이 출력됨
        });

        return oAuth2User;
    }

}
