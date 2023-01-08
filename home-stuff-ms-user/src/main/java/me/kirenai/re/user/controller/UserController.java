package me.kirenai.re.user.controller;

import lombok.RequiredArgsConstructor;
import me.kirenai.re.user.entity.User;
import me.kirenai.re.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v0/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

}
