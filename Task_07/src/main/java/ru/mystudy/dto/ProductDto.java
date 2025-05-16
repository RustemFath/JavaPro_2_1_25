package ru.mystudy.dto;

import java.math.BigDecimal;

public record ProductDto(Long id, String account, BigDecimal balance, String type, Long userId) {
}
