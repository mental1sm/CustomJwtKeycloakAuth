package com.ment09.starter.util.exceptions;

import lombok.Getter;

@Getter
public class InvalidCredentialsException extends Throwable {

    Object message;

    public InvalidCredentialsException(Object message) {
        this.message = message;
    }
}
