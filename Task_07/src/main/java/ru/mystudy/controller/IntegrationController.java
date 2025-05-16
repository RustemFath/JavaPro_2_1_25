package ru.mystudy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.mystudy.dto.ExecutorPaymentRequest;
import ru.mystudy.dto.PaymentResponse;
import ru.mystudy.dto.ProductResponse;
import ru.mystudy.service.ExecutorProductsService;
import ru.mystudy.service.ExecutorPaymentsService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/integration/v1")
public class IntegrationController {
    private final ExecutorPaymentsService executorPaymentsService;
    private final ExecutorProductsService executorProductsService;

    @GetMapping("/product-by-user-id")
    public ProductResponse findProductsByUserId(@RequestParam("id") Long userId) {
        log.info("get, method: findProductsByUserId, userId - {}", userId);
        return executorProductsService.findProductsByUserId(userId);
    }

    @PostMapping("/payment/execute")
    public PaymentResponse executePayment(@RequestBody ExecutorPaymentRequest executorPaymentRequest) {
        log.info("post, method: executePayment, executorPaymentRequest - {}", executorPaymentRequest);
        return executorPaymentsService.executePayment(executorPaymentRequest);
    }

}
