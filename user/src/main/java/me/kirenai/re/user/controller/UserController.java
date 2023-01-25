package me.kirenai.re.user.controller;

import lombok.RequiredArgsConstructor;
import me.kirenai.re.user.dto.ApiResponse;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v0/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(
            @PageableDefault(size = 5)
            @SortDefault.SortDefaults(value = {
                    @SortDefault(sort = "userId", direction = Sort.Direction.ASC)
            }) Pageable pageable
    ) {
        List<UserResponse> response = this.userService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserResponse response = this.userService.findOne(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> saveUser(@RequestBody UserRequest userRequest) {
        UserResponse response = this.userService.create(userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<UserResponse>builder().response(response).build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long userId,
                                                                @RequestBody UserRequest userRequest) {
        UserResponse response = this.userService.update(userId, userRequest);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder().response(response).build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(@PathVariable Long userId) {
        this.userService.delete(userId);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder().message("Deleted").build());
    }

}