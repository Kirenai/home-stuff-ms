package me.kirenai.re.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.security.service.AuthUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtTokenProvider {

    public static final String AUTHORITIES = "authorities";
    public static final String AUTHORITY = "authority";

    @Value(value = "${application.jwt.secretKey}")
    private String secretKey;
    @Value(value = "${application.jwt.tokenPrefix}")
    private String tokenPrefix;
    @Value(value = "${application.jwt.tokenExpirationAfterDays}")
    private Integer tokenExpirationAfterDays;
    @Value(value = "${application.jwt.internal.username}")
    private String username;
    @Value(value = "${application.jwt.internal.roleName}")
    private String roleName;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

    public String generateJwtToken(Authentication authentication) {
        return Jwts.builder()
                .setId(((AuthUserDetails) authentication.getPrincipal()).getUserId().toString())
                .setSubject(authentication.getName())
                .claim(AUTHORITIES, authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now()
                        .plusDays(this.getTokenExpirationAfterDays())))
                .signWith(Keys.hmacShaKeyFor(this.getSecretKey().getBytes()))
                .compact();
    }

    @Deprecated
    public HttpHeaders getCurrentTokenAsHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(this.getAuthorizationHeader(), "");
        return headers;
    }

    public String generateInternalJwtToken() {
        return Jwts.builder()
                .setSubject(this.getUsername())
                .claim(AUTHORITIES, List.of(new SimpleGrantedAuthority(this.getRoleName())))
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now()
                        .plusDays(this.getTokenExpirationAfterDays())))
                .signWith(Keys.hmacShaKeyFor(this.getSecretKey().getBytes()))
                .compact();
    }

    public String getJwtTokenFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(this.getAuthorizationHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(this.getTokenPrefix())) {
            return bearerToken.substring(this.getTokenPrefix().length());
        }
        return null;
    }

    public Claims getJwtBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(this.getSecretKey().getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public List<SimpleGrantedAuthority> getJwtGrantedAuthorities(String token) {
        Claims claims = this.getJwtBody(token);
        List<LinkedHashMap<String, String>> claimsAuthorities = claims.get(AUTHORITIES, List.class);
        return claimsAuthorities
                .stream()
                .map(map -> map.get(AUTHORITY))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(this.getSecretKey().getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}