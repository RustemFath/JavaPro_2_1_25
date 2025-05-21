package ru.mystudy.error.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import ru.mystudy.dto.ErrorResponse;
import ru.mystudy.exceptions.IntegrationException;

import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    private final ObjectMapper mapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        return statusCode.is4xxClientError() || statusCode.is5xxServerError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
            ErrorResponse errorResponse = mapper.readValue(response.getBody(), ErrorResponse.class);
            throw new IntegrationException(errorResponse.toString(), statusCode.toString());
        }
        ResponseErrorHandler.super.handleError(url, method, response);
    }
}
