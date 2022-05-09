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
public class NftSalesRepositoryTests {

    @Autowired
    private NftSalesRepository nftSalesRepository;

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

            NftSales nftSales = NftSales.builder()
                    .time(LocalDateTime.now())
                    .asset(Assets.builder().id((long)(Math.random() * 100) + 1).build())
                    .collection(Collections.builder().id((long)(Math.random() * 100) + 1).build())
                    .contract_address(sha_address)
                    .quantity((long)(Math.random() * 10) + 1)
                    .total_price(Math.random())
                    .seller_account(Accounts.builder().id((long)(Math.random() * 200) + 1).build())
                    .winner_account(Accounts.builder().id((long)(Math.random() * 200) + 1).build())
                    .build();
            nftSales.addPaymentSet(Payment.NONE);
            if (nftSales.getContract_address() != null) {
                if (i < 50) nftSales.addPaymentSet(Payment.ETH);
                else nftSales.addPaymentSet(Payment.WETH);
            }
            nftSales.addAuctionSet(Auction.DUTCH);
            if (i > 30) nftSales.addAuctionSet(Auction.ENGLISH);
            if (i > 60) nftSales.addAuctionSet(Auction.MIN_PRICE);

            nftSalesRepository.save(nftSales);
        });
    }

}
