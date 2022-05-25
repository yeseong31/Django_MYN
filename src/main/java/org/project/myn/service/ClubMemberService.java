package org.project.myn.service;

import org.project.myn.dto.ClubMemberDTO;
import org.project.myn.entity.ClubMember;

public interface ClubMemberService {
    // 등록
    String register(ClubMemberDTO dto);
    // 조회

    //수정

    // 삭제

    default ClubMember dtoToEntity(ClubMemberDTO dto) {
        return ClubMember.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .address(dto.getAddress())
                .phoneNum(dto.getPhone())
                .fromSocial(dto.isFromSocial())
                .build();
    }

    default ClubMemberDTO entityToDTO(ClubMember user) {
        return ClubMemberDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress())
                .phone(user.getPhoneNum())
                .fromSocial(user.isFromSocial())
                .regDate(user.getRegDate())
                .modDate(user.getModDate())
                .build();
    }
}
