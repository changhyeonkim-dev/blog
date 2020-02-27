package com.kim.blog.post.service;

import com.kim.blog.post.Post;
import com.kim.blog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;


    public void savePost(Post post) {
        postRepository.save(post);
    }

    public void increaseViews(Post post) {
        post.increaseViewsCount();
        postRepository.save(post);
    }

}
