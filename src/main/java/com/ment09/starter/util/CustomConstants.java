package com.ment09.starter.util;

import lombok.Getter;

/**
 * Константы стартера
*/
public class CustomConstants {
    /**
     * Константы для токенов
    */
    @Getter
    public enum TokenConstants {
        ACCESS_TOKEN("ACCESS_TOKEN"),
        REFRESH_TOKEN("REFRESH_TOKEN");

        private final String value;

        TokenConstants(String value) {
            this.value = value;
        }
    }
}