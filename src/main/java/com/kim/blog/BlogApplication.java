package com.kim.blog;

import com.kim.blog.account.Account;
import com.kim.blog.account.Role;
import com.kim.blog.account.repository.AccountRepository;
import com.kim.blog.config.AccountPasswordEncoder;
import com.kim.blog.category.Category;
import com.kim.blog.category.repository.CategoryRepository;
import com.kim.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
@EnableScheduling
public class BlogApplication implements ApplicationRunner {

    private final PostService postService;
    private final AccountRepository accountRepository;
    private final AccountPasswordEncoder accountPasswordEncoder;

    private final CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        if(!accountRepository.findByUserId("admin").isPresent()){
            Account admin = accountRepository.save(Account.builder()
                    .role(Role.ADMIN)
                    .userId("admin")
                    .password(accountPasswordEncoder.encode("!@#$"))
                    .isSocialUser(false)
                    .build());

            accountRepository.save(Account.builder()
                    .role(Role.GUEST)
                    .userId("guest")
                    .password(accountPasswordEncoder.encode("guest1234!!"))
                    .isSocialUser(false)
                    .build());
        }
        if(categoryRepository.count() == 0){
            categoryRepository.save(Category.builder()
                    .categoryName("develop")
                    .build());
            categoryRepository.save(Category.builder()
                    .categoryName("pictures")
                    .build());
        }

    }

}
