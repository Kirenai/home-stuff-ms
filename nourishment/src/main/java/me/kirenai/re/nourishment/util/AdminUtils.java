package me.kirenai.re.nourishment.util;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;

public class AdminUtils {

    public static boolean adminMatch(Map<String, Object> principal) {
        return ((List<SimpleGrantedAuthority>) principal.get("authorities"))
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }

}
