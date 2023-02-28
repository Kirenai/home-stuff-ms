package me.kirenai.re.security.util;

import lombok.RequiredArgsConstructor;
import me.kirenai.re.security.dto.RoleResponse;
import me.kirenai.re.security.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.service.AuthUserDetails;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    public static final String GET_ONE_USER_DETAILS_URL = "http://USER/api/v0/users/details/{username}";
    public static final String GET_ROLES_USER_URL = "http://ROLE/api/v0/roles/user/{userId}";
    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    public UserDetails getUserDetails(String username) {
        String token = this.jwtTokenProvider.generateInternalJwtToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, this.jwtTokenProvider.getTokenPrefix() + token);
        ResponseEntity<UserResponse> userEntity = this.restTemplate.exchange(
                GET_ONE_USER_DETAILS_URL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserResponse.class,
                username
        );
        UserResponse userResponse = userEntity.getBody();
        if (Objects.isNull(userResponse)) throw new IllegalStateException("Not Found");
        ResponseEntity<RoleResponse[]> roleEntity = this.restTemplate.exchange(
                GET_ROLES_USER_URL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                RoleResponse[].class,
                userResponse.getUserId()
        );
        RoleResponse[] roleResponse = roleEntity.getBody();
        if (Objects.isNull(roleResponse)) throw new IllegalStateException("Not Found");
        return new AuthUserDetails(
                userResponse.getUserId(),
                userResponse.getUsername(),
                userResponse.getPassword(),
                Stream.of(roleResponse).map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );
    }

}