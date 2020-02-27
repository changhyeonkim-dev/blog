package com.kim.blog.post.repository;

import com.kim.blog.post.Post;
import com.kim.blog.post.response.PostResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<PostResponse> getOneById(Long id);

    List<PostResponse> getAllBy(Pageable pageable);

    List<PostResponse> getAllByCategoryCategoryName(String categoryName, Pageable pageable);

    List<PostResponse> getTop3ByOrderByViewsCountDesc();
}
