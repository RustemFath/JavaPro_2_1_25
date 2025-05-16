package ru.mystudy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.mystudy.error.handler.RestTemplateResponseErrorHandler;

@Configuration
@EnableConfigurationProperties(ClientsProperties.class)
@RequiredArgsConstructor
public class AppConfig {
    private final ClientsProperties clientsProperties;

    @Bean
    public RestTemplate productsExecutorClient(RestTemplateResponseErrorHandler errorHandler) {
        RestTemplateProperties clientProperties = clientsProperties.getProductsClient();
        return new RestTemplateBuilder()
                .rootUri(clientProperties.url())
                .readTimeout(clientProperties.readTimeout())
                .connectTimeout(clientProperties.connectTimeout())
                .errorHandler(errorHandler)
                .build();
    }

    @Bean
    public RestTemplate paymentsExecutorClient(RestTemplateResponseErrorHandler errorHandler) {
        RestTemplateProperties clientProperties = clientsProperties.getPaymentsClient();
        return new RestTemplateBuilder()
                .rootUri(clientProperties.url())
                .readTimeout(clientProperties.readTimeout())
                .connectTimeout(clientProperties.connectTimeout())
                .build();
    }
}
