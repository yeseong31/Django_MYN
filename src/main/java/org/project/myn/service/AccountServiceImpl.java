package org.project.myn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.AccountDTO;
import org.project.myn.entity.Account;
import org.project.myn.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public String register(AccountDTO dto) {
        Account account = dtoToEntity(dto);
        accountRepository.save(account);
        return account.getClubMember().getEmail();
    }

    @Override
    public AccountDTO get(Long id) {
        Optional<Account> result = accountRepository.findAccountWithEmail(id);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public void modify(AccountDTO dto) {
        Long id = dto.getId();
        Optional<Account> result = accountRepository.findById(id);
        if (result.isPresent()) {
            Account account = result.get();
            account.changeUsername(dto.getUsername());
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
