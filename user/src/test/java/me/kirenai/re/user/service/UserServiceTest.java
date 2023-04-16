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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

//    @Test
//    @DisplayName("Should find a list of users")
//    void shouldFindUsersTest() {
//        List<UserResponse> usersResponse = UserMocks.getUserResponseList();
//
//        Pageable pageableMock = mock(Pageable.class);
//        when(this.userRepository.findAllBy(any(Pageable.class)))
//                .thenReturn(UserMocks.getFluxUsers());
//        when(this.userMapper.mapOutUserToUserResponse(any()))
//                .thenReturn(usersResponse.get(0), usersResponse.get(1), usersResponse.get(2));
//
//        Flux<UserResponse> response = this.userService.findAll(pageableMock);
//
//        StepVerifier.create(response.log())
//                .expectNext(usersResponse.get(0))
//                .expectNext(usersResponse.get(1))
//                .expectNext(usersResponse.get(2))
//                .verifyComplete();
//
//        verify(this.userRepository, times(1)).findAllBy(any());
//        verify(this.userMapper, times(3)).mapOutUserToUserResponse(any());
//    }

    @Test
    @DisplayName("Should find a user")
    void shouldFindUserTest() {
        UserResponse userResponse = UserMocks.getUserResponse();

        when(this.userRepository.findById(anyLong())).thenReturn(Mono.just(UserMocks.getUser()));
        when(this.userMapper.mapOutUserToUserResponse(any())).thenReturn(userResponse);

        Mono<UserResponse> response = userService.findOne(1L);

        StepVerifier.create(response.log())
                .expectNext(userResponse)
                .verifyComplete();

        verify(this.userRepository, times(1)).findById(anyLong());
        verify(this.userMapper, timeout(1)).mapOutUserToUserResponse(any());
    }

    @Test
    @DisplayName("Should create an user")
    void shouldCreateUser() {
        UserResponse userResponse = UserMocks.getUserResponse();

        when(this.userMapper.mapInUserRequestToUser(any())).thenReturn(UserMocks.getUser());
        when(this.userRepository.save(any())).thenReturn(Mono.just(UserMocks.getUser()));
        when(this.roleManager.createRoleUser(any())).thenReturn(Mono.empty());
        when(this.userMapper.mapOutUserToUserResponse(any())).thenReturn(userResponse);

        Mono<UserResponse> response = this.userService.create(new UserRequest());

        StepVerifier.create(response.log())
                .expectNext(userResponse)
                .verifyComplete();

        verify(this.userMapper, times(1)).mapInUserRequestToUser(any());
        verify(this.roleManager, times(1)).createRoleUser(any());
        verify(this.userMapper, times(1)).mapOutUserToUserResponse(any());
    }

    @Test
    @DisplayName("Should update an user")
    void shouldUpdateUserTest() {
        UserResponse userResponse = UserMocks.getUserResponse();

        when(this.userRepository.findById(anyLong())).thenReturn(Mono.just(UserMocks.getUser()));
        when(this.userRepository.save(any())).thenReturn(Mono.just(UserMocks.getUser()));
        when(this.userMapper.mapOutUserToUserResponse(any())).thenReturn(userResponse);

        Mono<UserResponse> response = this.userService.update(1L, new UserRequest());

        StepVerifier.create(response.log())
                .expectNext(userResponse)
                .verifyComplete();

        verify(this.userRepository, times(1)).findById(anyLong());
        verify(this.userMapper, times(1)).mapOutUserToUserResponse(any());
    }

    @Test
    @DisplayName("Should delete an user")
    void shouldDeleteUserTest() {
        Mono<Void> response = this.userService.delete(1L);

        StepVerifier.create(response.log())
                .expectComplete();
    }

}