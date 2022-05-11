package org.project.myn.service;

import org.project.myn.dto.AccountDTO;
import org.project.myn.dto.AssetDTO;
import org.project.myn.dto.NFTSaleDTO;
import org.project.myn.entity.Account;
import org.project.myn.entity.Asset;
import org.project.myn.entity.NftSale;

import java.util.HashMap;
import java.util.Map;

public interface NFTSaleService {

    default Map<String, Object> dtoToEntity(NFTSaleDTO nftSaleDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        // NFTSale 처리
        NftSale nftSale = NftSale.builder()
                .id(nftSaleDTO.getId())
                .time(nftSaleDTO.getTime())
                .contract_address(nftSaleDTO.getContract_address())
                .quantity(nftSaleDTO.getQuantity())
                .total_price(nftSaleDTO.getTotal_price())
                .build();
        entityMap.put("nftSale", nftSale);

        // asset_id 처리
        AssetDTO asset_id_dto = nftSaleDTO.getAsset_dto();
        Asset asset_id = Asset.builder()
                .id(asset_id_dto.getId())
                .name(asset_id_dto.getName())
                .description(asset_id_dto.getDescription())
                .contract_date(asset_id_dto.getContract_date())
                .url(asset_id_dto.getUrl())
                .build();  // owner를 넣지 않은 이유는... asset_id로 owner를 찾는 기능으로 해결하기 위함
        entityMap.put("asset_id", asset_id);

        // seller_account 처리
        AccountDTO seller_account_dto = nftSaleDTO.getSeller_account_dto();
        Account seller_account = Account.builder()
                .id(seller_account_dto.getId())
                .username(seller_account_dto.getUsername())
                .address(seller_account_dto.getAddress())
                .email(seller_account_dto.getEmail())
                .fromSocial(seller_account_dto.isFromSocial())
                .password(seller_account_dto.getPassword())
                .build();
        entityMap.put("seller_account", seller_account);

        // winner_account 처리
        AccountDTO winner_account_dto = nftSaleDTO.getSeller_account_dto();
        Account winner_account = Account.builder()
                .id(winner_account_dto.getId())
                .username(winner_account_dto.getUsername())
                .address(winner_account_dto.getAddress())
                .email(winner_account_dto.getEmail())
                .fromSocial(winner_account_dto.isFromSocial())
                .password(winner_account_dto.getPassword())
                .build();
        entityMap.put("winner_account", winner_account);

        // from_account 처리
        AccountDTO from_account_dto = nftSaleDTO.getSeller_account_dto();
        Account from_account = Account.builder()
                .id(from_account_dto.getId())
                .username(from_account_dto.getUsername())
                .address(from_account_dto.getAddress())
                .email(from_account_dto.getEmail())
                .fromSocial(from_account_dto.isFromSocial())
                .password(from_account_dto.getPassword())
                .build();
        entityMap.put("from_account", from_account);

        // to_account 처리
        AccountDTO to_account_dto = nftSaleDTO.getSeller_account_dto();
        Account to_account = Account.builder()
                .id(to_account_dto.getId())
                .username(to_account_dto.getUsername())
                .address(to_account_dto.getAddress())
                .email(to_account_dto.getEmail())
                .fromSocial(to_account_dto.isFromSocial())
                .password(to_account_dto.getPassword())
                .build();
        entityMap.put("to_account", to_account);

        // collection_id 처리


        return entityMap;
    }

}
