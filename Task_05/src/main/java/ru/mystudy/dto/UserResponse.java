package ru.mystudy.dto;

import java.util.List;

public record UserResponse(List<UserDto> users) {
}
