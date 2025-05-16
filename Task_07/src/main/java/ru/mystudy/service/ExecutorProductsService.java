package ru.mystudy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mystudy.dto.ProductResponse;

@Slf4j
@Service
public class ExecutorProductsService {
    private final RestTemplate productsExecutorClient;
    private final String getByUserIdUri;

    public ExecutorProductsService(@Qualifier("productsExecutorClient") RestTemplate productsExecutorClient,
                                   @Value("${integration.clients.products-client.get-by-user-id-uri}")
                                   String getByUserIdUri) {
        this.productsExecutorClient = productsExecutorClient;
        this.getByUserIdUri = getByUserIdUri;
    }

    public ProductResponse findProductsByUserId(Long userId) {
        log.info("findProductsByUserId, userId = {}", userId);
        return productsExecutorClient.getForEntity(getByUserIdUri + userId, ProductResponse.class).getBody();
    }
}
