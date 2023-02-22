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

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    public static final String GET_ONE_USER_DETAILS_URL = "http://USER/api/v0/users/details/{username}";
    public static final String GET_ONE_ROLE_ROLE = "http://ROLE/api/v0/roles/{roleId}";
    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    public UserDetails getUserDetails(String username) {
        String token = this.jwtTokenProvider.generateInternalJwtToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, this.jwtTokenProvider.getTokenPrefix() + token);
        ResponseEntity<UserResponse> userEntity = this.restTemplate.exchange(GET_ONE_USER_DETAILS_URL, HttpMethod.GET, new HttpEntity<>(headers), UserResponse.class, username);
        UserResponse userResponse = userEntity.getBody();
        if (Objects.isNull(userResponse)) throw new IllegalStateException("Not Found");
        ResponseEntity<RoleResponse> roleEntity = this.restTemplate.exchange(GET_ONE_ROLE_ROLE, HttpMethod.GET, new HttpEntity<>(headers), RoleResponse.class, userResponse.getRoleId());
        RoleResponse roleResponse = roleEntity.getBody();
        if (Objects.isNull(roleResponse)) throw new IllegalStateException("Not Found");
        return new AuthUserDetails(
                userResponse.getUserId(),
                userResponse.getUsername(),
                userResponse.getPassword(),
                List.of(new SimpleGrantedAuthority(roleResponse.getName()))
        );
    }

}