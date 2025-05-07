package ru.mystudy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.mystudy.dto.UserCreateRequest;
import ru.mystudy.dto.UserDto;
import ru.mystudy.dto.UserResponse;
import ru.mystudy.service.UserService;
import ru.mystudy.utils.UserMapper;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public UserDto createUser(@RequestBody UserCreateRequest userCreateRequest) {
        log.info("post, method: createUser, request - {}", userCreateRequest);
        return UserMapper.toDto(userService.createUser(UserMapper.toUser(userCreateRequest)));
    }

    @GetMapping("/users")
    public UserResponse getAllUsers() {
        log.info("get, method: getAllUsers");
        return new UserResponse(userService.findAll().stream()
                .map(UserMapper::toDto)
                .toList());
    }
}
