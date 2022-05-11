package org.project.myn.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.project.myn.entity.Account;
import org.project.myn.entity.AccountRole;
import org.project.myn.entity.QAccounts;
import org.project.myn.security.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

@SpringBootTest
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository accountsRepository;

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

            Account accounts = Account.builder()
                    .username("test" + i)
                    .address(sha_address)
                    .password(passwordEncoder.encode("test" + i))
                    .email("test" + i + "@test.com")
                    .build();

            accounts.addMemberRole(AccountRole.USER);
            if (i > 50) accounts.addMemberRole(AccountRole.MEMBER);
            if (i > 180) accounts.addMemberRole(AccountRole.ADMIN);

            accountsRepository.save(accounts);
        });
    }

    // Querydsl --------------------------------------------------
    // 단일 항목 검색 테스트: username에 '1'이 포함되어 있는 Accounts 확인
    @Transactional
    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        // 1) 동적 처리를 위해 Q도메인 클래스 활용
        QAccounts qAccounts = QAccounts.accounts;

        String keyword = "1";

        // 2) BooleanBuilder는 where 문에 들어가는 조건들을 넣어주는 컨테이너
        BooleanBuilder builder = new BooleanBuilder();

        // 3) 원하는 조건은 필드 값과 같이 결합하여 생성 (com.querydsl.core.types.Predicate 타입)
        BooleanExpression expression = qAccounts.username.contains(keyword);

        // 4) 조건을 키워드와 결합
        builder.and(expression);

        // 5) QuerydslPredicateExcutor 인터페이스의 findAll() 등의 메서드를 사용할 수 있음
        Page<Account> result = accountsRepository.findAll(builder, pageable);

        result.stream().forEach(System.out::println);
    }


}
