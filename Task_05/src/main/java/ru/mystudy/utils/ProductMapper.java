package ru.mystudy.utils;

import org.springframework.stereotype.Service;
import ru.mystudy.dto.ProductCreateRequest;
import ru.mystudy.dto.ProductDto;
import ru.mystudy.entity.Product;

@Service
public class ProductMapper {
    public ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getAccount(),
                product.getBalance(),
                product.getType(),
                product.getUser().getId());
    }

    public Product toProduct(ProductCreateRequest productCreateRequest) {
        Product product = new Product();
        product.setAccount(productCreateRequest.account());
        product.setBalance(productCreateRequest.balance());
        product.setType(productCreateRequest.type());
        return product;
    }
}
