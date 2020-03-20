package com.kim.blog;


import com.kim.blog.account.Account;
import com.kim.blog.account.repository.AccountRepository;
import com.kim.blog.category.repository.CategoryRepository;
import com.kim.blog.comment.Comment;
import com.kim.blog.post.CategoryFixtureGenerator;
import com.kim.blog.post.Post;
import com.kim.blog.post.repository.PostRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private Post develop;

    private Post picture;

    private Account account;



//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void createPost() throws Exception {
//        mockMvc.perform(post("/api/posts")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .with(csrf())
//
//                    .content("{\"title\":\"mytitle\",\"contents\":\"myContents\",\"category\":\"develop\"}"))
//                .andExpect(status().isCreated());
//
//    }

    @Before
    public void setUp(){
        categoryFixtureGenerator.generateCategories();
        account = accountRepository.findByUserId("admin").orElseThrow(IllegalArgumentException::new);
        List<Comment> comments = new ArrayList<>();

        develop = Post.builder()
                .account(account)
                .title("title1")
                .contents("content1")
                .category(categoryRepository.findByCategoryName("develop").get())
                .comments(comments)
                .build();

        picture = Post.builder()
                .account(account)
                .title("title2")
                .contents("content2")
                .category(categoryRepository.findByCategoryName("picture").get())
                .build();

        comments.add(Comment.builder()
                .account(account)
                .commentContents("testComment")
                .post(develop)
                .build());

        postRepository.save(develop);
        postRepository.save(picture);
    }

    @Test
    public void getPostWithComment() throws Exception{

        mockMvc.perform(get("/api/posts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("testComment")));
    }

    @Test
    public void getPostsInCategory() throws Exception {
        //given


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