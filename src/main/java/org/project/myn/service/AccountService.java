package org.project.myn.service;

import org.project.myn.dto.AccountDTO;
import org.project.myn.entity.Account;

public interface AccountService {

    default Account dtoToEntity(Account account) {
        return Account.builder()
                .id(account.getId())
                .username(account.getUsername())
                .address(account.getAddress())
                .email(account.getEmail())
                .fromSocial(account.isFromSocial())
                .password(account.getPassword())
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
