package ru.mystudy.dto;

import java.util.List;

public record ProductResponse(List<ProductDto> products, Long userId) {
}
