package com.kim.blog.reply.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SaveReplyRequest {
    @NotNull
    private String contents;
}
