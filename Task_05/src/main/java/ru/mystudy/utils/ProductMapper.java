package ru.mystudy.utils;

import ru.mystudy.dto.ProductCreateRequest;
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

    public static Product toProduct(ProductCreateRequest productCreateRequest) {
        Product product = new Product();
        product.setAccount(productCreateRequest.account());
        product.setBalance(productCreateRequest.balance());
        product.setType(productCreateRequest.type());
        return product;
    }
}
