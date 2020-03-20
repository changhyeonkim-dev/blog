package com.kim.blog.post;

import com.kim.blog.account.Account;
import com.kim.blog.category.Category;
import com.kim.blog.category.repository.CategoryRepository;
import com.kim.blog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class PostFixtureGenerator {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public Post generatePost(Account account){
        Category category = categoryRepository.save(Category.builder().categoryName("java").build());
        return postRepository.save(
                Post.builder()
                        .title("title")
                        .contents("content")
                        .account(account)
                        .category(category)
                        .build()
        );
    }

    public List<Post> generatePosts100(Account account){
        Category category = categoryRepository.findByCategoryName("develop").orElseThrow(IllegalArgumentException::new);
        Category category2 = categoryRepository.findByCategoryName("pictures").orElseThrow(IllegalArgumentException::new);
        ArrayList<Post> posts= new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Post build = Post.builder()
                    .title("develop" + i)
                    .contents("content" + i)
                    .account(account)
                    .category(category)
                    .viewsCount(new Random().nextInt(10000))
                    .build();
            posts.add(build);
        }
        for (int i = 0; i < 50; i++) {
            Post build = Post.builder()
                    .title("picture" + i)
                    .contents("content" + i)
                    .account(account)
                    .category(category2)
                    .viewsCount(new Random().nextInt(10000))
                    .build();
            posts.add(build);
        }
        return postRepository.saveAll(posts);
    }

}
