package ru.mystudy.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mystudy.dto.ProductDto;
import ru.mystudy.dto.UserCreateRequest;
import ru.mystudy.dto.UserDto;
import ru.mystudy.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final ProductMapper productMapper;

    public UserDto toDto(User user) {
        List<ProductDto> productsDto = Optional.of(user.getProducts())
                .orElseGet(Collections::emptySet)
                .stream()
                .map(productMapper::toDto)
                .toList();
        return new UserDto(user.getId(), user.getUsername(), productsDto);
    }

    public User toUser(UserCreateRequest userCreateRequest) {
        return new User(userCreateRequest.username(), null);
    }
}
