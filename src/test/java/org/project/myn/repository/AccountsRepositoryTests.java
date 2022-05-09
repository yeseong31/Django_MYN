package org.project.myn.repository;

import org.junit.jupiter.api.Test;
import org.project.myn.entity.Accounts;
import org.project.myn.security.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

@SpringBootTest
public class AccountsRepositoryTests {

    @Autowired
    private AccountsRepository accountsRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            SHA256 sha256 = new SHA256();

            String address = "test" + i;
            String sha_address = null;

            try {
                sha_address = sha256.encrypt(address);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            Accounts accounts = Accounts.builder()
                    .username("test" + i)
                    .address(sha_address)
                    .build();

            accountsRepository.save(accounts);
        });
    }

}
