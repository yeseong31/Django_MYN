package org.project.myn.service;

import org.project.myn.dto.AccountDTO;
import org.project.myn.dto.AssetDTO;
import org.project.myn.entity.Account;
import org.project.myn.entity.Asset;

import java.util.HashMap;
import java.util.Map;

public interface AssetService {

    default Map<String, Object> dtoToEntity(AssetDTO assetDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Asset asset = Asset.builder()
                .id(assetDTO.getId())
                .name(assetDTO.getName())
                .description(assetDTO.getDescription())
                .contract_date(assetDTO.getContract_date())
                .url(assetDTO.getUrl())
                .build();
        entityMap.put("asset", asset);

        AccountDTO accountDTO = assetDTO.getAccountDTO();
        Account account = Account.builder()
                .id(accountDTO.getId())
                .username(accountDTO.getUsername())
                .address(accountDTO.getAddress())
                .email(accountDTO.getEmail())
                .fromSocial(accountDTO.isFromSocial())
                .password(accountDTO.getPassword())
                .build();
        entityMap.put("account", account);

        return entityMap;
    }

    default AssetDTO entityToDto(Asset asset, Account account) {
        AssetDTO assetDTO = AssetDTO.builder()
                .id(asset.getId())
                .name(asset.getName())
                .description(asset.getDescription())
                .contract_date(asset.getContract_date())
                .url(asset.getUrl())
                .regDate(asset.getRegDate())
                .modDate(asset.getModDate())
                .build();

        AccountDTO accountDTO = AccountDTO.builder()
                .id(account.getId())
                .username(account.getUsername())
                .address(account.getAddress())
                .email(account.getEmail())
                .fromSocial(account.isFromSocial())
                .password(account.getPassword())
                .regDate(account.getRegDate())
                .modDate(account.getModDate())
                .build();

        assetDTO.setAccountDTO(accountDTO);

        return assetDTO;
    }

}
