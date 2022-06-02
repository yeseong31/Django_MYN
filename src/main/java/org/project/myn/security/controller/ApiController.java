package org.project.myn.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.ClubMemberDTO;
import org.project.myn.service.ClubMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ClubMemberService clubMemberService;

    // 회원가입 진행
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody ClubMemberDTO clubMemberDTO) {
        log.info("-------------------- register --------------------");
        log.info(clubMemberDTO);

        String email = clubMemberService.register(clubMemberDTO);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

}

