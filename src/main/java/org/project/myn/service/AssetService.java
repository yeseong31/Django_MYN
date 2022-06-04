package org.project.myn.service;

import org.project.myn.dto.AssetDTO;
import org.project.myn.entity.Account;
import org.project.myn.entity.Asset;

public interface AssetService {
    // 등록
    Long register(AssetDTO assetDTO);
    // 조회
    AssetDTO get(Long id);
    // 수정
    void modify(AssetDTO assetDTO);
    // 삭제
    void remove(Long id);

    default Asset dtoToEntity(AssetDTO dto) {
        return Asset.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .url(dto.getUrl())
                .account(Account.builder().id(dto.getAccountId()).build())
                .build();
    }

    default AssetDTO entityToDto(Asset asset) {
        return AssetDTO.builder()
                .id(asset.getId())
                .name(asset.getName())
                .description(asset.getDescription())
                .url(asset.getUrl())
                .accountId(asset.getAccount().getId())
                .regDate(asset.getRegDate())
                .modDate(asset.getModDate())
                .build();
    }
}
