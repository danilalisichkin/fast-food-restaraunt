package com.fastfoodrestaraunt.backend.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSortField {
    PHONE("phone"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email"),
    ACTIVE("active");

    private final String value;
}
