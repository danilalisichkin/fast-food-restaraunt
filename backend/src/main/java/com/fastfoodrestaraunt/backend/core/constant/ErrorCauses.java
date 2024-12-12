package com.fastfoodrestaraunt.backend.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCauses {
    public static final String BAD_REQUEST = "bad request";
    public static final String VALIDATION = "validation error";
    public static final String UNAUTHORIZED = "unauthorized";
    public static final String FORBIDDEN = "access denied";
    public static final String NOT_FOUND = "not found";
    public static final String UNIQUENESS_CONFLICT = "uniqueness conflict";
    public static final String INTERNAL = "internal error";
    public static final String CANT_READ_REQUEST = "can not read request";
    public static final String CONTACT_DEVELOPERS = "contact developers if error persists";
}
