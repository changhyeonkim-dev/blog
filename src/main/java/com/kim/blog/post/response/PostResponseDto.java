package com.kim.blog.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PostResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String contents;

//    private AccountResponse account;
//
//    private List<CommentResponse> comments;
//
//    private CategoryResponse category;

    private long viewsCount;

    private int up;

    private int down;

    private String previewImagePath;

    private LocalDate createdTime;

    private LocalDate modifiedTime;

}
