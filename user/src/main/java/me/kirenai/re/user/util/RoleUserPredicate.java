package me.kirenai.re.user.util;

import me.kirenai.re.user.dto.RoleResponse;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class RoleUserPredicate implements Predicate<RoleResponse> {

    private static final String ROLE_USER = "ROLE_USER";

    @Override
    public boolean test(RoleResponse roleResponse) {
        return roleResponse.getName().equals(ROLE_USER);
    }

}
