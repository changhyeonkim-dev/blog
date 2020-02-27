package com.kim.blog;


import com.kim.blog.account.Account;
import com.kim.blog.account.repository.AccountRepository;
import com.kim.blog.post.CategoryFixtureGenerator;
import com.kim.blog.post.Post;
import com.kim.blog.post.repository.CategoryRepository;
import com.kim.blog.post.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BlogRestControllerTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CategoryFixtureGenerator categoryFixtureGenerator;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getPostsInCategory() throws Exception {
        //given
        categoryFixtureGenerator.generateCategories();
        Account account = accountRepository.findByUserId("admin").orElseThrow(IllegalArgumentException::new);
        Post develop = Post.builder()
                .account(account)
                .title("title1")
                .contents("content1")
                .category(categoryRepository.findByCategoryName("develop").get())
                .build();

        Post picture = Post.builder()
                .account(account)
                .title("title2")
                .contents("content2")
                .category(categoryRepository.findByCategoryName("picture").get())
                .build();

        postRepository.save(develop);
        postRepository.save(picture);

        //when,then
        mockMvc.perform(get("/api/posts/categories/" + develop.getCategory().getCategoryName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("title1"));
        mockMvc.perform(get("/api/posts/categories/" + picture.getCategory().getCategoryName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("title2"));
    }


}