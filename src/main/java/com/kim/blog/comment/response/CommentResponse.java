package com.kim.blog.comment.response;

import com.kim.blog.account.response.AccountResponse;

import java.time.LocalDate;

public interface CommentResponse {

    Long getId();

    String getCommentContents();

    AccountResponse getAccount();

    int getUp();

    int getDown();

    LocalDate getCreatedTime();

    LocalDate getModifiedTime();
}
