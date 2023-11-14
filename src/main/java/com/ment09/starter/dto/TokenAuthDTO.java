package com.ment09.starter.dto;

/**
 * Интерфейс, который требуется для авторизации и аутентификации.
 * Обязателен к реализации для корректной работы стартеры
*/
public interface TokenAuthDTO {
    String getUsername();
    String getPassword();
}
