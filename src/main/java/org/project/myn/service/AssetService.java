package org.project.myn.service;

import org.project.myn.dto.AccountDTO;
import org.project.myn.dto.AssetDTO;
import org.project.myn.dto.AssetImageDTO;
import org.project.myn.entity.Account;
import org.project.myn.entity.Asset;
import org.project.myn.entity.AssetImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface AssetService {

    default Map<String, Object> dtoToEntity(AssetDTO assetDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        // Asset 처리
        Asset asset = Asset.builder()
                .id(assetDTO.getId())
                .name(assetDTO.getName())
                .description(assetDTO.getDescription())
                .contract_date(assetDTO.getContract_date())
                .url(assetDTO.getUrl())
                .build();
        entityMap.put("asset", asset);

        // Asset Image 처리
        List<AssetImageDTO> imageDTOList = assetDTO.getImageDTOList();
        if (imageDTOList != null && imageDTOList.size() > 0) {
            List<AssetImage> assetImageList =imageDTOList.stream().map(assetImageDTO -> {
                return AssetImage.builder()
                        .uuid(assetImageDTO.getUuid())
                        .name(assetImageDTO.getName())
                        .path(assetImageDTO.getPath())
                        .asset(asset)
                        .build();
            }).collect(Collectors.toList());

            entityMap.put("imgList", assetImageList);
        }

        // Account 처리
        AccountDTO accountDTO = assetDTO.getAccount();
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

    default AssetDTO entityToDto(Asset asset, Account account, List<AssetImage> assetImages) {
        AssetDTO assetDTO = AssetDTO.builder()
                .id(asset.getId())
                .name(asset.getName())
                .description(asset.getDescription())
                .contract_date(asset.getContract_date())
                .url(asset.getUrl())
                .regDate(asset.getRegDate())
                .modDate(asset.getModDate())
                .build();

        List<AssetImageDTO> assetImageDTOList = assetImages.stream().map(assetImage -> {
            return AssetImageDTO.builder()
                    .name(assetImage.getName())
                    .path(assetImage.getPath())
                    .uuid(assetImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

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

        assetDTO.setImageDTOList(assetImageDTOList);
        assetDTO.setAccount(accountDTO);

        return assetDTO;
    }

}
