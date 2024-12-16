package com.fastfoodrestaraunt.backend.security.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContextUtils {

    public static String getUserId() {
        return (String) getAuthentication().getPrincipal();
    }

    public static List<String> getRoles() {
        return getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
