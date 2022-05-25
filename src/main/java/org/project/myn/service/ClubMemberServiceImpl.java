package org.project.myn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.ClubMemberDTO;
import org.project.myn.entity.ClubMember;
import org.project.myn.repository.ClubMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubMemberServiceImpl implements ClubMemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ClubMemberRepository clubMemberRepository;

    @Override
    public String register(ClubMemberDTO dto) {
        // 해당 이메일을 가지는 사용자가 DB에 존재하는지 먼저 파악해야 함
        Optional<ClubMember> result = clubMemberRepository.findByEmail(dto.getEmail(), dto.isFromSocial());
        if (result.isPresent()) {
            return null;
        }

        ClubMember clubMember = dtoToEntity(dto);
        clubMember.changePassword(passwordEncoder.encode(dto.getPassword()));

        clubMemberRepository.save(clubMember);
        return clubMember.getEmail();
    }

    @Override
    public ClubMemberDTO get(String email) {
        Optional<ClubMember> result = clubMemberRepository.findById(email);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public void modify(ClubMemberDTO dto) {
        String email = dto.getEmail();
        Optional<ClubMember> result = clubMemberRepository.findById(email);
        if (result.isPresent()) {
            ClubMember clubMember = result.get();
            clubMember.changeAddress(dto.getAddress());
            clubMember.changePassword(passwordEncoder.encode(dto.getPassword()));
            clubMemberRepository.save(clubMember);
        }
    }

    @Override
    public void delete(String email) {
        clubMemberRepository.deleteById(email);
    }
}
