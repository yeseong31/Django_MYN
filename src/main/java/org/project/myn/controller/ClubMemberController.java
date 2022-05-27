package org.project.myn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.ClubMemberDTO;
import org.project.myn.service.ClubMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    // 사용자 정보 등록
    @PostMapping(value = "")
    public ResponseEntity<String> register(@RequestBody ClubMemberDTO clubMemberDTO) {
        log.info("-------------------- register --------------------");
        log.info(clubMemberDTO);

        String email = clubMemberService.register(clubMemberDTO);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    // 사용자 정보 조회
    @GetMapping(value = "/read", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClubMemberDTO> get(String email) {
        log.info("-------------------- read --------------------");
        log.info(email);

        return new ResponseEntity<>(clubMemberService.get(email), HttpStatus.OK);
    }

    // 사용자 이메일을 통해 계정 삭제
    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(String email) {
        log.info("-------------------- remove --------------------");
        log.info(email);

        clubMemberService.delete(email);

        return new ResponseEntity<>("removed", HttpStatus.OK);
    }

}
