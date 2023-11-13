package com.ment09.starter.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * Упакованный токен, включающий в себя само значение токена, а также время истечения
*/
@Getter
@Builder
public class TokenPack {
    private final String token;
    private final Integer lifeSpan;
}
