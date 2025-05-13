package ru.mystudy.utils;

import ru.mystudy.dto.ProductDto;
import ru.mystudy.dto.UserCreateRequest;
import ru.mystudy.dto.UserDto;
import ru.mystudy.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserMapper {
    public static UserDto toDto(User user) {
        List<ProductDto> productsDto = Optional.of(user.getProducts())
                .orElseGet(Collections::emptySet)
                .stream()
                .map(ProductMapper::toDto)
                .toList();
        return new UserDto(user.getId(), user.getUsername(), productsDto);
    }

    public static User toUser(UserCreateRequest userCreateRequest) {
        return new User(userCreateRequest.username(), null);
    }
}
