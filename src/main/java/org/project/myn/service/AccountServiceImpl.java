package org.project.myn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.AccountDTO;
import org.project.myn.entity.Account;
import org.project.myn.entity.ClubMember;
import org.project.myn.entity.ClubMemberRole;
import org.project.myn.repository.AccountRepository;
import org.project.myn.repository.ClubMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Override
    public Long register(AccountDTO accountDTO) {
        // Account 개설 전 사용자 회원가입이 완료된 회원인지 판단해야 함
        Optional<ClubMember> result = clubMemberRepository.findByEmail(accountDTO.getEmail());

        // 해당 계정이 존재한다면 권한 추가 후 Account 개설
        if (result.isPresent()) {
            ClubMember clubMember = result.get();
            clubMember.addMemberRole(ClubMemberRole.MEMBER);
            clubMemberRepository.save(clubMember);

            Account account = dtoToEntity(accountDTO);
            accountRepository.save(account);
            return account.getId();
        }
        // 그렇지 않으면 Account를 개설하지 않음
        return null;
    }

    @Override
    public AccountDTO get(Long id) {
        Optional<Account> result = accountRepository.findAccountWithEmail(id);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public void modify(AccountDTO accountDTO) {
        Long id = accountDTO.getId();
        Optional<Account> result = accountRepository.findById(id);
        if (result.isPresent()) {
            Account account = result.get();
            account.changeUsername(accountDTO.getUsername());
            accountRepository.save(account);
        }
    }

    @Override
    public void remove(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountDTO> getAllWithUsername(String username) {
        List<Account> accountList = accountRepository.getList(username);
        return accountList.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
