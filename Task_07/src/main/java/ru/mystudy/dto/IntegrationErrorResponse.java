package ru.mystudy.dto;

public record IntegrationErrorResponse(String message, String integrationStatusCode, String integrationMessage) {
}
