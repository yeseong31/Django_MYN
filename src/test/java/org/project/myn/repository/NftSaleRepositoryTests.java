package org.project.myn.repository;

import org.junit.jupiter.api.Test;
import org.project.myn.entity.*;
import org.project.myn.security.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
public class NftSaleRepositoryTests {

    @Autowired
    private NftSaleRepository nftSalesRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 500).forEach(i -> {
            SHA256 sha256 = new SHA256();
            String address = "test" + i;
            String sha_address = null;

            try {
                int rand = (int)(Math.random() * 10) + 1;
                if (rand > 2)
                    sha_address = sha256.encrypt(address);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            NftSale nftSale = NftSale.builder()
                    .time(LocalDateTime.now())
                    .asset(Asset.builder().id((long)(Math.random() * 100) + 1).build())
                    .collection(Collection.builder().id((long)(Math.random() * 100) + 1).build())
                    .contract_address(sha_address)
                    .quantity((long)(Math.random() * 10) + 1)
                    .total_price(Math.random())
                    .seller_account(Account.builder().id((long)(Math.random() * 200) + 1).build())
                    .winner_account(Account.builder().id((long)(Math.random() * 200) + 1).build())
                    .build();
            nftSale.addPaymentSet(Payment.NONE);
            if (nftSale.getContract_address() != null) {
                if (i < 50) nftSale.addPaymentSet(Payment.ETH);
                else nftSale.addPaymentSet(Payment.WETH);
            }
            nftSale.addAuctionSet(Auction.DUTCH);
            if (i > 30) nftSale.addAuctionSet(Auction.ENGLISH);
            if (i > 60) nftSale.addAuctionSet(Auction.MIN_PRICE);

            nftSalesRepository.save(nftSale);
        });
    }

}
