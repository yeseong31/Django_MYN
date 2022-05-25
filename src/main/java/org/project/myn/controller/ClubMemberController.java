package org.project.myn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.ClubMemberDTO;
import org.project.myn.repository.ClubMemberRepository;
import org.project.myn.service.ClubMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/club/")
@RequiredArgsConstructor
public class ClubMemberController {

    private final ClubMemberService service;

    @PostMapping(value = "")
    public ResponseEntity<String> register(@RequestBody ClubMemberDTO clubMemberDTO) {
        log.info("-------------------- register --------------------");
        log.info(clubMemberDTO);

        String email = service.register(clubMemberDTO);
        if (email.isEmpty())
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

}
