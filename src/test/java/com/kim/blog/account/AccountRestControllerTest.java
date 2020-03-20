package com.kim.blog.account;

import com.kim.blog.account.repository.AccountRepository;
import com.kim.blog.exception.EntityNotFoundException;
import com.kim.blog.util.email.EmailService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AccountRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmailService emailService;

    @DisplayName("회원가입 성공 테스트")
    @Test
    public void success_sign_up() throws Exception {
        String email = "kimchanghyeon1201@gmail.com";
        mockMvc.perform(post("/api/account")
                .param("userId", email)
                .param("password", "test1234")
        )
                .andExpect(status().isCreated())
                .andExpect(handler().handlerType(AccountRestController.class))
                .andExpect(handler().methodName("signUp"));

        Account account = accountRepository.findByUserId(email).orElseThrow(EntityNotFoundException::new);
        assertThat(account.getRole()).isEqualTo(Role.NONE);

    }

}