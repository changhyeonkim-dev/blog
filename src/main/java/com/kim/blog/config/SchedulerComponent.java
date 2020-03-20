package com.kim.blog.config;

import com.kim.blog.post.repository.PostRepository;
import com.kim.blog.post.response.PostResponse;
import com.kim.blog.post.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerComponent {
    private final PostRepository postRepository;
    private final RedisTemplate<String, PostResponseDto> postRedisTemplate;
    private static final String key = "popular-list";

    @Scheduled(cron = "00 30 00 * * ?")
    public void setDailyPopularPosts(){
        ListOperations<String, PostResponseDto> stringPostListOperations = postRedisTemplate.opsForList();
        List<PostResponse> top3ByOrderByViewsCountDesc = postRepository.getTop3ByOrderByViewsCountDesc();
        ArrayList<PostResponseDto> dtoList = (ArrayList<PostResponseDto>) top3ByOrderByViewsCountDesc.stream().map(PostResponse::toPostResponseDtop).collect(Collectors.toList());
        stringPostListOperations.leftPushAll(key,dtoList);
        log.info("UpdatedPopularPosts");
    }

}
