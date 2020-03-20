package com.kim.blog.account;

import com.kim.blog.account.repository.AccountRepository;
import com.kim.blog.account.request.SaveAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {
    private final AccountRepository accountRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SaveAccountRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SaveAccountRequest request = (SaveAccountRequest) target;
        if(accountRepository.findByUserId(request.getUserId()).isPresent()){
            errors.rejectValue("userId","이미 존재하는 Id 입니다");
        }
    }
}
