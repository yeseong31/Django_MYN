package org.project.myn.security.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.entity.ClubMember;
import org.project.myn.entity.ClubMemberRole;
import org.project.myn.repository.ClubMemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final ClubMemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("----------------------------------------");
        log.info("userRequest: " + userRequest);    // OAuth2UserRequest 객체

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName: " + clientName);      // Google로 출력됨
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("========================================");
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + ": " + v);                 // sub, picture, email, email_verified, ENAIL 등이 출력됨
        });

        String email = null;

        // Google로 로그인 수행 시 이메일 정보를 추출하여 회원가입 처리
        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }
        log.info("EMAIL: " + email);

//        ClubMember member = saveSocialMember(email);
//
//        return oAuth2User;

        ClubMember member = saveSocialMember(email);

        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getPhoneNum(),
                member.getAddress(),
                true,
                member.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        // clubAuthMember.setName(member.getName);
        return clubAuthMember;
    }


    private ClubMember saveSocialMember(String email) {

        // 기존에 동일한 이메일로 가입한 회원이 있다면 조회만 수행
        Optional<ClubMember> result = repository.findByEmail(email, true);
        if (result.isPresent()) {
            return result.get();
        }

        // 해당 이메일을 가진 회원이 없다면 회원가입 진행
        // 회원가입 처리 시 비밀번호와 이름이 고정된 형태이므로 추후 수정 필요
        ClubMember clubMember = ClubMember.builder()
                // .name(email)
                .password(passwordEncoder.encode("1111"))
                .email(email)
                .fromSocial(true)
                // 소셜 회원가입 시 주소와 전화번호 정보는 우선 NULL로 저장
                .address(null)
                .phoneNum(null)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);
        repository.save(clubMember);
        return clubMember;

    }

}
