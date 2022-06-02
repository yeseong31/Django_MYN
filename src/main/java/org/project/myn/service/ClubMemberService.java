package org.project.myn.service;

import org.project.myn.dto.ClubMemberDTO;
import org.project.myn.entity.ClubMember;

public interface ClubMemberService {
    // 등록
    String register(ClubMemberDTO dto);
    // 조회
    ClubMemberDTO get(String email);
    //수정
    void modify(ClubMemberDTO dto);
    // 삭제
    void remove(Long id);

    default ClubMember dtoToEntity(ClubMemberDTO dto) {
        return ClubMember.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .address(dto.getAddress())
                .phoneNum(dto.getPhoneNum())
                .fromSocial(dto.isFromSocial())
                .build();
    }

    default ClubMemberDTO entityToDto(ClubMember user) {
        return ClubMemberDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress())
                .phoneNum(user.getPhoneNum())
                .fromSocial(user.isFromSocial())
                .regDate(user.getRegDate())
                .modDate(user.getModDate())
                .build();
    }
}
