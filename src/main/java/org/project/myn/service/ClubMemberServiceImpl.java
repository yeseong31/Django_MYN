package org.project.myn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.ClubMemberDTO;
import org.project.myn.entity.ClubMember;
import org.project.myn.repository.ClubMemberRepository;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubMemberServiceImpl implements ClubMemberService {

    private final ClubMemberRepository repository;

    @Override
    public String register(ClubMemberDTO dto) {
        ClubMember clubMember = dtoToEntity(dto);
        repository.save(clubMember);
        return clubMember.getEmail();
    }
}
