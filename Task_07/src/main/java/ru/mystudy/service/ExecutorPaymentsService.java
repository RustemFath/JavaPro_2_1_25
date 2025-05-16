package ru.mystudy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.mystudy.dto.ExecutorPaymentRequest;
import ru.mystudy.dto.PaymentResponse;
import ru.mystudy.exceptions.IntegrationException;

@Slf4j
@Service
public class ExecutorPaymentsService {

    private final RestTemplate paymentsExecutorClient;
    private final String executeUri;
    private final CheckPaymentsService checkPaymentsService;

    public ExecutorPaymentsService(@Qualifier("paymentsExecutorClient") RestTemplate paymentsExecutorClient,
                                   @Value("${integration.clients.payments-client.execute-uri}")
                                   String executeUri,
                                   CheckPaymentsService checkPaymentsService) {
        this.paymentsExecutorClient = paymentsExecutorClient;
        this.executeUri = executeUri;
        this.checkPaymentsService = checkPaymentsService;
    }

    public PaymentResponse executePayment(ExecutorPaymentRequest executorPaymentRequest) {
        log.info("executePayment, executorPaymentRequest = {}", executorPaymentRequest);
        checkPaymentsService.validatePayment(executorPaymentRequest);
        try {
            return paymentsExecutorClient.getForEntity(
                    executeUri + "?userId=" + executorPaymentRequest.productDto().userId() +
                            "&productId=" + executorPaymentRequest.productDto().id(),
                    PaymentResponse.class).getBody();
        }
        catch (ResourceAccessException exception) {
            throw new IntegrationException(exception.toString(), "500");
        }
    }

}
