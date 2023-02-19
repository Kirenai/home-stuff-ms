package me.kirenai.re.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kirenai.re.auth.dto.ApiResponse;
import me.kirenai.re.auth.dto.UserResponse;
import me.kirenai.re.auth.service.AuthService;
import me.kirenai.re.auth.util.AuthMocks;
import me.kirenai.re.security.jwt.JwtTokenFilter;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.service.AuthUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AuthController.class},
    excludeFilters = {@ComponentScan.Filter(classes = {JwtTokenFilter.class}, type = FilterType.ASSIGNABLE_TYPE)})
@WithMockUser
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private AuthService authService;

    private final StringBuilder URL = new StringBuilder("/api/v0/auth");

    @Test
    @DisplayName("should login")
    public void loginTest() throws Exception {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new AuthUserDetails(1L, "username", "password", List.of(new SimpleGrantedAuthority("ROLE_USER"))),
                "password");
        AuthUserDetails response = (AuthUserDetails) authentication.getPrincipal();
        when(this.authService.login(any())).thenReturn(authentication);

        RequestBuilder request = MockMvcRequestBuilders
                .post(this.URL.append("/login").toString())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(AuthMocks.getLoginRequest()))
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(response.getUserId()))
                .andExpect(jsonPath("$.username").value(response.getUsername()))
                .andExpect(jsonPath("$.authorities").isArray());
    }

    @Test
    @DisplayName("should register")
    public void registerTest() throws Exception {
        ApiResponse<UserResponse> response = AuthMocks.getApiResponse();
        when(this.authService.register(any())).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .post(this.URL.append("/register").toString())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(AuthMocks.getRegisterRequest()))
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response").value(response.getResponse()));
    }

}