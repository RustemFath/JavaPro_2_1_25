package ru.mystudy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "integration.clients")
@Getter
@Setter
public class ClientsProperties {
    private RestTemplateProperties productsClient;
    private RestTemplateProperties paymentsClient;
}
