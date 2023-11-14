package com.ment09.starter.dto;

/**
 * Интерфейс, который требуется для регистрации.
 * Обязателен к реализации для корректной работы стартеры
 */
public interface TokenRegistrationDTO extends TokenAuthDTO {
    String getEmail();
}
