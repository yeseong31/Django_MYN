package org.project.myn.service;

import org.project.myn.dto.AccountDTO;
import org.project.myn.entity.Account;
import org.project.myn.entity.ClubMember;

import java.util.List;

public interface AccountService {
    // 등록
    Long register(AccountDTO accountDTO);
    // 조회
    AccountDTO get(Long id);
    //수정
    void modify(AccountDTO accountDTO);
    // 삭제
    void remove(Long id);

    // 해당 username을 가지는 사용자 정보 조회
    List<AccountDTO> getAllWithUsername(String username);

    default Account dtoToEntity(AccountDTO dto) {
        return Account.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .address(dto.getAddress())
                .clubMember(ClubMember.builder().email(dto.getEmail()).build())
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
