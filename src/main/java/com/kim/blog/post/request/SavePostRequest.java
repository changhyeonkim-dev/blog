package com.kim.blog.post.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SavePostRequest {
    private String title;
    private String contents;
    private String category;
    private String previewImagePath;
}
