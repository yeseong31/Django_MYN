package org.project.myn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.ClubMemberDTO;
import org.project.myn.entity.Account;
import org.project.myn.entity.ClubMember;
import org.project.myn.entity.ClubMemberRole;
import org.project.myn.repository.AccountRepository;
import org.project.myn.repository.ClubMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubMemberServiceImpl implements ClubMemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ClubMemberRepository clubMemberRepository;
    private final AccountRepository accountRepository;

    @Override
    public String register(ClubMemberDTO dto) {
        // 해당 이메일을 가지는 사용자가 DB에 존재하는지 먼저 파악해야 함
        Optional<ClubMember> result = clubMemberRepository.findByEmailWithSocial(dto.getEmail(), dto.isFromSocial());
        if (result.isPresent()) {
            return null;
        }

        ClubMember clubMember = dtoToEntity(dto);
        clubMember.changePassword(passwordEncoder.encode(dto.getPassword()));

        // 접근 권한 설정
        // 처음 회원가입을 진행할 때에는 Account가 없으므로 '0' 부여
        clubMember.addMemberRole(ClubMemberRole.USER);

        clubMemberRepository.save(clubMember);
        return clubMember.getEmail();
    }

    @Override
    public ClubMemberDTO get(String email) {
        Optional<ClubMember> result = clubMemberRepository.findByEmail(email);
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
            clubMember.changePhoneNum(dto.getPhoneNum());
            clubMemberRepository.save(clubMember);
        }
    }

    @Transactional
    @Override
    public void remove(Long id) {
        // Account 삭제
        accountRepository.deleteByClubMemberId(id);

        // ClubMember Role 삭제
        Optional<ClubMember> checkClubMember = clubMemberRepository.findById(id);
        // 권한을 삭제할 ClubMember가 없다면 그대로 종료
        if (checkClubMember.isEmpty()) {
            System.out.println("존재하지 않는 사용자입니다.");
            return;
        }

        ClubMember clubMember = checkClubMember.get();
        clubMember.deleteMemberRole(ClubMemberRole.MEMBER);
        clubMember.deleteMemberRole(ClubMemberRole.USER);

        // ClubMember 삭제
        clubMemberRepository.deleteById(id);
    }
}
