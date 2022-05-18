package org.project.myn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.AccountDTO;
import org.project.myn.entity.Account;
import org.project.myn.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public Long register(AccountDTO accountDTO) {
        Account account = dtoToEntity(accountDTO);
        repository.save(account);
        return account.getId();
    }

    @Override
    public AccountDTO get(Long id) {
        Optional<Account> result = repository.findById(id);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public void modify(AccountDTO accountDTO) {
        Optional<Account> result = repository.findById(accountDTO.getId());

        if (result.isPresent()) {
            Account entity = result.get();

            entity.changeUsername(accountDTO.getUsername());

            repository.save(entity);
        }
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
