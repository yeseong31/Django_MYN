package org.project.myn.service;

import org.project.myn.dto.AccountDTO;
import org.project.myn.entity.Account;
import org.project.myn.entity.ClubMember;

public interface AccountService {
    // 등록
    String register(AccountDTO dto);
    // 조회

    //수정

    // 삭제

    default Account dtoToEntity(AccountDTO dto) {
        ClubMember clubMember = ClubMember.builder()
                .email(dto.getEmail())
                .build();

        return Account.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .address(dto.getAddress())
                .clubMember(clubMember)
                .build();
    }

    default AccountDTO entityToDto(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .address(account.getAddress())
                .username(account.getUsername())
                .email(account.getClubMember().getEmail())
                .regDate(account.getRegDate())
                .modDate(account.getModDate())
                .build();
    }

}
