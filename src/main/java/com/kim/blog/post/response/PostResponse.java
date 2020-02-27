package com.kim.blog.post.response;

import com.kim.blog.account.response.AccountResponse;

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

}
