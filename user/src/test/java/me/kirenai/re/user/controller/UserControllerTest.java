package me.kirenai.re.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.service.UserService;
import me.kirenai.re.user.util.UserMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    private final StringBuilder URL = new StringBuilder("/api/v0/users");

    @Test
    @DisplayName("Should get users")
    void shouldGetUsersTest() throws Exception {
        List<UserResponse> response = UserMocks.getUserResponseList();
        when(this.userService.findAll(any(Pageable.class)))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.toString());

        this.mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userId").value(response.get(0).getUserId()))
                .andExpect(jsonPath("$[0].username").value(response.get(0).getUsername()))
                .andExpect(jsonPath("$[0].firstName").value(response.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(response.get(0).getLastName()))
                .andExpect(jsonPath("$[0].age").value(response.get(0).getAge()))
                .andExpect(jsonPath("$[1].userId").value(response.get(1).getUserId()))
                .andExpect(jsonPath("$[1].username").value(response.get(1).getUsername()))
                .andExpect(jsonPath("$[1].firstName").value(response.get(1).getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(response.get(1).getLastName()))
                .andExpect(jsonPath("$[1].age").value(response.get(1).getAge()))
                .andExpect(jsonPath("$[2].userId").value(response.get(2).getUserId()))
                .andExpect(jsonPath("$[2].username").value(response.get(2).getUsername()))
                .andExpect(jsonPath("$[2].firstName").value(response.get(2).getFirstName()))
                .andExpect(jsonPath("$[2].lastName").value(response.get(2).getLastName()))
                .andExpect(jsonPath("$[2].age").value(response.get(2).getAge()));
    }


    @Test
    @DisplayName("Should get user")
    void shouldGetUserTest() throws Exception {
        UserResponse response = UserMocks.getUserResponse();
        when(this.userService.findOne(anyLong()))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.append("/1").toString());

        this.mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(response.getUserId()))
                .andExpect(jsonPath("$.username").value(response.getUsername()))
                .andExpect(jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.age").value(response.getAge()));
    }

    @Test
    @DisplayName("Should create user")
    void shouldCreateUser() throws Exception {
        UserResponse response = UserMocks.getUserResponse();
        when(this.userService.create(any()))
                .thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .post(this.URL.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(UserMocks.getUserRequest()));

        this.mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.userId").value(response.getUserId()))
                .andExpect(jsonPath("$.response.username").value(response.getUsername()))
                .andExpect(jsonPath("$.response.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.response.lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.response.age").value(response.getAge()));
    }
}