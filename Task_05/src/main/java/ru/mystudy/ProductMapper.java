package ru.mystudy;

import ru.mystudy.dto.ProductDto;
import ru.mystudy.entity.Product;

public class ProductMapper {
    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getAccount(),
                product.getBalance(),
                product.getType(),
                product.getUser().getId());
    }
}
