package org.project.myn.repository;

import org.junit.jupiter.api.Test;
import org.project.myn.entity.Accounts;
import org.project.myn.entity.AccountsRole;
import org.project.myn.security.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

@SpringBootTest
public class AccountsRepositoryTests {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {
        // USER: 1~50 | USER&MANAGER: 51~180 | USER&MANAGER&ADMIN: 180~100
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
                    .password(passwordEncoder.encode("test" + i))
                    .email("test" + i + "@test.com")
                    .build();

            accounts.addMemberRole(AccountsRole.USER);
            if (i > 50) accounts.addMemberRole(AccountsRole.MEMBER);
            if (i > 180) accounts.addMemberRole(AccountsRole.ADMIN);

            accountsRepository.save(accounts);
        });
    }

}
