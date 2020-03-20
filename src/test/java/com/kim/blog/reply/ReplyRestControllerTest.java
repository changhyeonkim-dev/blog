package com.kim.blog.reply;


import com.kim.blog.account.Account;
import com.kim.blog.account.repository.AccountRepository;
import com.kim.blog.comment.repository.CommentRepository;
import com.kim.blog.config.JwtTokenProvider;
import com.kim.blog.post.Post;
import com.kim.blog.post.PostFixtureGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReplyRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostFixtureGenerator postFixtureGenerator;


    @Test
    public void postReply() throws Exception {
        //given
        Account account = accountRepository.findByUserId("admin").orElseThrow(EntityNotFoundException::new);
        String token = jwtTokenProvider.createToken(account.getUserId(), account.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        Post post = postFixtureGenerator.generatePost(account);

        //when
        mockMvc.perform(post("/api/posts/"+post.getId()+"/reply")
                    .header("X-AUTH-TOKEN",token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"contents\":\"ReplyContent\"}"))
                .andExpect(status().isCreated());

    }
}