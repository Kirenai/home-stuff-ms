package me.kirenai.re.user.service;

import me.kirenai.re.user.api.RoleManager;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.mapper.UserMapper;
import me.kirenai.re.user.repository.UserRepository;
import me.kirenai.re.user.util.UserMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleManager roleManager;

    @Test
    @DisplayName("Should find a list of users")
    void shouldFindUsersTest() {
        List<UserResponse> usersResponse = UserMocks.getUserResponseList();

        Pageable pageableMock = mock(Pageable.class);
        when(this.userRepository.findAll(any(Pageable.class)))
                .thenReturn(UserMocks.getUserPage());
        when(this.userMapper.mapOutUserToUserResponse(any()))
                .thenReturn(usersResponse.get(0), usersResponse.get(1), usersResponse.get(2));

        List<UserResponse> response = this.userService.findAll(pageableMock);

        assertNotNull(response);
        assertEquals(usersResponse.size(), response.size());
        assertEquals(usersResponse, response);

        verify(this.userRepository, times(1)).findAll(pageableMock);
        verify(this.userMapper, times(3)).mapOutUserToUserResponse(any());
    }

    @Test
    @DisplayName("Should find a user")
    void shouldFindUserTest() {
        UserResponse userResponse = UserMocks.getUserResponse();

        when(this.userRepository.findById(anyLong())).thenReturn(Optional.of(UserMocks.getUser()));
        when(this.userMapper.mapOutUserToUserResponse(any())).thenReturn(userResponse);

        UserResponse response = userService.findOne(1L);

        assertEquals(userResponse, response);

        verify(this.userRepository, times(1)).findById(anyLong());
        verify(this.userMapper, timeout(1)).mapOutUserToUserResponse(any());
    }

    @Test
    @DisplayName("Should create an user")
    void shouldCreateUser() {
        UserResponse userResponse = UserMocks.getUserResponse();

        when(this.userMapper.mapInUserRequestToUser(any())).thenReturn(UserMocks.getUser());
        when(this.userMapper.mapOutUserToUserResponse(any())).thenReturn(userResponse);

        UserResponse response = this.userService.create(new UserRequest());

        assertEquals(userResponse, response);

        verify(this.userMapper, times(1)).mapInUserRequestToUser(any());
        verify(this.roleManager, times(1)).createRoleUser(any());
        verify(this.userMapper, times(1)).mapOutUserToUserResponse(any());
    }

    @Test
    @DisplayName("Should update an user")
    void shouldUpdateUserTest() {
        UserResponse userResponse = UserMocks.getUserResponse();

        when(this.userRepository.findById(anyLong())).thenReturn(Optional.of(UserMocks.getUser()));
        when(this.userMapper.mapOutUserToUserResponse(any())).thenReturn(userResponse);

        UserResponse response = this.userService.update(1L, new UserRequest());

        assertEquals(userResponse, response);

        verify(this.userRepository, times(1)).findById(anyLong());
        verify(this.userMapper, times(1)).mapOutUserToUserResponse(any());
    }

    @Test
    @DisplayName("Should delete an user")
    void shouldDeleteUserTest() {
        doNothing().when(this.userRepository).deleteById(anyLong());

        this.userService.delete(1L);

        verify(this.userRepository, times(1)).deleteById(anyLong());
    }

}