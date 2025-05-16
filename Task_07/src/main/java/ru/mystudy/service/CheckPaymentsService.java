package ru.mystudy.service;

import org.springframework.stereotype.Service;
import ru.mystudy.dto.ExecutorPaymentRequest;
import ru.mystudy.dto.ProductDto;

import java.math.BigDecimal;

@Service
public class CheckPaymentsService {

    public void validatePayment(ExecutorPaymentRequest executorPaymentRequest) {
        ProductDto productDto = executorPaymentRequest.productDto();
        if (productDto == null || productDto.id() == null || productDto.userId() == null ||
                productDto.account() == null || productDto.balance() == null ||
                productDto.type() == null) {
            throw new IllegalArgumentException("Параметры запроса некорректны, request = " + executorPaymentRequest);
        }
        if (productDto.balance().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма баланса недостаточна, request = " + executorPaymentRequest);
        }
    }
}
