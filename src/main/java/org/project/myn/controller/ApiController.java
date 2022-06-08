package org.project.myn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.ClubMemberDTO;
import org.project.myn.service.ClubMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ApiController {

    private final ClubMemberService clubMemberService;

    // '/explore'
    @GetMapping(value = "/userinfo/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClubMemberDTO> explore(@PathVariable("email") String email) {
        log.info("yes");
        return new ResponseEntity<>(clubMemberService.get(email), HttpStatus.OK);
    }

}
