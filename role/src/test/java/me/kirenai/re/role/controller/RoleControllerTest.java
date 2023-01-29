package me.kirenai.re.role.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.service.RoleService;
import me.kirenai.re.role.util.RoleMocks;
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

@WebMvcTest(controllers = RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RoleService roleService;

    private final StringBuilder URL = new StringBuilder("/api/v0/roles");

    @Test
    @DisplayName("Should return all roles")
    void getAllRolesTest() throws Exception {
        List<RoleResponse> roles = RoleMocks.getRoleResponseList();
        when(this.roleService.findAll(any(Pageable.class)))
                .thenReturn(roles);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.toString());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].roleId").value(roles.get(0).getRoleId()))
                .andExpect(jsonPath("$[0].name").value(roles.get(0).getName()))
                .andExpect(jsonPath("$[1].roleId").value(roles.get(1).getRoleId()))
                .andExpect(jsonPath("$[1].name").value(roles.get(1).getName()))
                .andExpect(jsonPath("$[2].roleId").value(roles.get(2).getRoleId()))
                .andExpect(jsonPath("$[2].name").value(roles.get(2).getName()));
    }

    @Test
    @DisplayName("Should return role by id")
    void getRoleTest() throws Exception {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();
        when(this.roleService.findOne(anyLong()))
                .thenReturn(roleResponse);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.append("/1").toString());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleId").value(roleResponse.getRoleId()))
                .andExpect(jsonPath("$.name").value(roleResponse.getName()));
    }

    @Test
    @DisplayName("Should create role")
    void createRoleTest() throws Exception {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();
        when(this.roleService.create(any()))
                .thenReturn(roleResponse);

        RequestBuilder request = MockMvcRequestBuilders
                .post(this.URL.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(RoleMocks.getRoleRequest()))
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.roleId").value(roleResponse.getRoleId()))
                .andExpect(jsonPath("$.response.name").value(roleResponse.getName()));
    }

    @Test
    @DisplayName("Should update role")
    void updateRoleTest() throws Exception {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();
        when(this.roleService.update(anyLong(), any()))
                .thenReturn(roleResponse);

        RequestBuilder request = MockMvcRequestBuilders
                .put(this.URL.append("/1").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(RoleMocks.getRoleRequest()))
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.roleId").value(roleResponse.getRoleId()))
                .andExpect(jsonPath("$.response.name").value(roleResponse.getName()));
    }

}