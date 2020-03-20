package com.kim.blog.account.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SaveAccountRequest {
    @NotBlank
//    @Pattern(regexp = "\"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,6}$\"")
    private String userId;

    @NotNull
    private String password;
}
