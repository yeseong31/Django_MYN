package org.project.myn.service;

import org.project.myn.dto.AccountDTO;
import org.project.myn.entity.Account;

public interface AccountService {

    default Account dtoToEntity(AccountDTO accountDTO) {
        return Account.builder()
                .id(accountDTO.getId())
                .username(accountDTO.getUsername())
                .address(accountDTO.getAddress())
                .email(accountDTO.getEmail())
                .fromSocial(accountDTO.isFromSocial())
                .password(accountDTO.getPassword())
                .build();
    }

    default AccountDTO entityToDto(Account entity) {
        return AccountDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .address(entity.getAddress())
                .email(entity.getEmail())
                .fromSocial(entity.isFromSocial())
                .password(entity.getPassword())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

}
