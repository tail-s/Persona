package com.ssafy.project.common.db.entity.base;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleEnum {

    CLIENT,
    ADMIN

/*  다른 방법:
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String key; */
}
