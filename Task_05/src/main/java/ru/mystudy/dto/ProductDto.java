package ru.mystudy.dto;

import ru.mystudy.enums.ProductType;

import java.math.BigDecimal;

public record ProductDto(Long id, String account, BigDecimal balance, ProductType type, Long userId) {
}
