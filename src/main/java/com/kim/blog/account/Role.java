package com.kim.blog.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST","손님"),
    ADMIN("ROLE_ADMIN","관리자"),
    NONE("ROLE_NONE","인증되지않은유저");

    private final String key;
    private final String title;

}
