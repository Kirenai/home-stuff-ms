package me.kirenai.re.auth.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String GET_USER_USERNAME_URL = "http://USER/api/v0/users/details/{username}";
    public static final String GET_ROLES_USER_ID_URL = "http://ROLE/api/v0/roles/user/{userId}";

}
