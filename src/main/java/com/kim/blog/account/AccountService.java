package com.kim.blog.account;

import com.kim.blog.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;


    public String save(Account account) {
        account.generateEmailToken();
        return accountRepository.save(account).getCheckValidEmailToken();
    }

    public void updateUserRole(Account account) {
        account.setRole(Role.GUEST);
        accountRepository.save(account);
    }
}
