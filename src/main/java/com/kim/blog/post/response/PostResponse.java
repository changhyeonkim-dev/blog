package com.kim.blog.post.response;

import com.kim.blog.account.response.AccountResponse;
import com.kim.blog.category.response.CategoryResponse;
import com.kim.blog.comment.response.CommentResponse;

import java.time.LocalDate;
import java.util.List;

public interface PostResponse {

    Long getId();

    String getTitle();

    String getContents();

    AccountResponse getAccount();

    List<CommentResponse> getComments();

    CategoryResponse getCategory();

    long getViewsCount();

    int getUp();

    int getDown();

    String getPreviewImagePath();

    LocalDate getCreatedTime();

    LocalDate getModifiedTime();

    default PostResponseDto toPostResponseDtop(){
        return new PostResponseDto(getId(),getTitle(),getContents(),getViewsCount(),getUp(),getDown(),getPreviewImagePath(),getCreatedTime(),getModifiedTime());
    }

}
