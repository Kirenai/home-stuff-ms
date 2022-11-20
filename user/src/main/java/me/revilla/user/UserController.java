package me.revilla.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v0/user")
public record UserController(UserServiceImp userService) {

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

}
