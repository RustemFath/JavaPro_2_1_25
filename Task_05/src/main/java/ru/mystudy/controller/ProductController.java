package ru.mystudy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.mystudy.dto.ProductCreateRequest;
import ru.mystudy.dto.ProductDto;
import ru.mystudy.dto.ProductResponse;
import ru.mystudy.service.ProductService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/product/v1")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/by-user-id")
    public ProductResponse findByUserId(@RequestParam("id") Long userId) {
        log.info("get, method: findByUserId, userId - {}", userId);
        return productService.findByUserId(userId);
    }

    @GetMapping("/by-product-id")
    public ProductDto findByProductId(@RequestParam("id") Long id) {
        log.info("get, method: findByProductId, id - {}", id);
        return productService.findByProductId(id);
    }

    @PostMapping("/create")
    public ProductDto createProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        log.info("post, method: createProduct, request - {}", productCreateRequest);
        return productService.createProduct(productCreateRequest);
    }
}
