package org.project.myn.repository;

import org.junit.jupiter.api.Test;
import org.project.myn.entity.Account;
import org.project.myn.entity.Asset;
import org.project.myn.entity.AssetImage;
import org.project.myn.security.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;


@SpringBootTest
public class AssetRepositoryTests {

    @Autowired
    private AssetRepository assetsRepository;

    @Autowired
    private AssetImageRepository assetsImageRepository;

    @Test
    public void insertDummies() {
        // contract_date 값은 임의의 날짜로 설정
        long minDay = LocalDate.of(2018, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        IntStream.rangeClosed(1, 100).forEach(i -> {
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

            SHA256 sha256 = new SHA256();
            String name = "assets" + i;
            String sha_url = null;

            try {
                sha_url = sha256.encrypt(name);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            int n = (int) (Math.random() * 200) + 1;

            Asset asset = Asset.builder()
                    .name(name)
                    .description("description..." + i)
                    .contract_date(randomDate)
                    .url("https://opensea.io/assets/" + sha_url)
                    .owner(Account.builder().id((long) n).build())
                    .build();
            System.out.println("————————————————————————————————————————");
            assetsRepository.save(asset);

            String url = "img_url" + i;
            String sha_img_url = null;

            try {
                sha_img_url = sha256.encrypt(url);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            AssetImage assetsImage = AssetImage.builder()
                    .uuid(UUID.randomUUID().toString())
                    .assets(asset)
                    .name("image" + i)
                    .path("https://lh3.googleusercontent.com/" + sha_img_url)
                    .build();
            assetsImageRepository.save(assetsImage);
            System.out.println("————————————————————————————————————————");
        });
    }

}