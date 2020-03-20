package com.kim.blog.account;

import com.kim.blog.account.request.SaveAccountRequest;
import com.kim.blog.config.AccountPasswordEncoder;
import com.kim.blog.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AccountRestController {
    private final AccountService accountService;
    private final AccountPasswordEncoder accountPasswordEncoder;
    private final SignUpValidator signUpValidator;
    private final EmailService emailService;

    @InitBinder("saveAccountRequest")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpValidator);
    }


    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid SaveAccountRequest saveAccountRequest){
        Account account = Account.builder()
                .isSocialUser(false)
                .userId(saveAccountRequest.getUserId())
                .password(accountPasswordEncoder.encode(saveAccountRequest.getPassword()))
                .role(Role.NONE)
                .build();
        String emailToken = accountService.save(account);
        emailService.sendMail(account.getUserId(),"memory-by 인증 메일 입니다","http://localhost:8080/valid-email-token?token="+emailToken+"&userId="+account.getUserId());
    }

}
