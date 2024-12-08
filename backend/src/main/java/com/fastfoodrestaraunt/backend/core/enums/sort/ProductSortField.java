package com.fastfoodrestaraunt.backend.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSortField {
    ID("id"),
    FIRST_NAME("firstName"),
    PRICE("price"),
    WEIGHT("weight");

    private final String value;
}
