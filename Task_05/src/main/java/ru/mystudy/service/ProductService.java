package ru.mystudy.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mystudy.ProductMapper;
import ru.mystudy.dto.ProductDto;
import ru.mystudy.dto.ProductResponse;
import ru.mystudy.entity.Product;
import ru.mystudy.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto findByProductId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Parameter id is null");
        }
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return ProductMapper.toDto(product);
    }

    public ProductResponse findByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Parameter userId is null");
        }
        List<Product> products = productRepository.findByUserId(userId);
        return new ProductResponse(
                products.stream()
                        .map(ProductMapper::toDto)
                        .collect(Collectors.toList()),
                userId);
    }
}
