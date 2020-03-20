package com.kim.blog.account;

import com.kim.blog.account.repository.AccountRepository;
import com.kim.blog.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @GetMapping("/valid-email-token")
    public String validEmailToken(String token, String userId) {
        Account account = accountRepository.findByUserId(userId).orElseThrow(EntityNotFoundException::new);
        if(account.getCheckValidEmailToken().equals(token)) {
            accountService.updateUserRole(account);
            return "redirect:/";
        }
        return "/error";
    }
}
