package com.fastfoodrestaraunt.backend.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSortField {
    PHONE("phone"),
    NAME("name"),
    LAST_NAME("lastName"),
    EMAIL("email");

    private final String value;
}
