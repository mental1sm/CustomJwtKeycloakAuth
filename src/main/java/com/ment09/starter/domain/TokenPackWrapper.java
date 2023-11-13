package com.ment09.starter.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * Упакованный набор access и refresh токена, включающий в себя само значение токена, а также время истечения
 */
@Getter
@Builder
public class TokenPackWrapper {
    private final TokenPack access;
    private final TokenPack refresh;
}
