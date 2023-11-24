package com.github.mental1sm.starter.dto;

/**
 * Интерфейс, который требуется для авторизации и аутентификации.
 * Обязателен к реализации для корректной работы стартеры
*/
public interface TokenAuthDTO {
    String getUsername();
    String getPassword();
}
