package ru.mystudy.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mystudy.entity.User;
import ru.mystudy.repository.UserRepository;
import ru.mystudy.utils.ProductMapper;
import ru.mystudy.dto.ProductCreateRequest;
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
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    public ProductDto findByProductId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Parameter id is null");
        }
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return productMapper.toDto(product);
    }

    public ProductResponse findByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Parameter userId is null");
        }
        userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        List<Product> products = productRepository.findByUserId(userId);
        return new ProductResponse(
                products.stream()
                        .map(productMapper::toDto)
                        .collect(Collectors.toList()),
                userId);
    }

    public ProductDto createProduct(ProductCreateRequest productCreateRequest) {
        User user = userRepository.findById(productCreateRequest.userId()).orElseThrow(EntityNotFoundException::new);
        Product newProduct = productMapper.toProduct(productCreateRequest);
        newProduct.setUser(user);
        Product product = productRepository.save(newProduct);
        return productMapper.toDto(product);
    }
}
