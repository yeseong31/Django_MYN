package org.project.myn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.myn.entity.Auction;
import org.project.myn.entity.Payment;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NFTSaleDTO {

    private Long id;

    private LocalDateTime time;
    private String contract_address;
    private Long quantity;
    private Double total_price;

    private Set<Auction> auctionSet;
    private Set<Payment> payment_symbol;

    @Builder.Default
    private AccountDTO seller_account_dto = new AccountDTO();
    @Builder.Default
    private AccountDTO winner_account_dto = new AccountDTO();
    @Builder.Default
    private AccountDTO from_account_dto = new AccountDTO();
    @Builder.Default
    private AccountDTO to_account_dto = new AccountDTO();
    @Builder.Default
    private AssetDTO asset_dto = new AssetDTO();
    @Builder.Default
    private CollectionDTO collection_dto = new CollectionDTO();

    private LocalDateTime regDate, modDate;

}
