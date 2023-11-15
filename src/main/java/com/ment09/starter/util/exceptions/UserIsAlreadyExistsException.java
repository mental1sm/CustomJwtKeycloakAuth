package com.ment09.starter.util.exceptions;

import lombok.Getter;

@Getter
public class UserIsAlreadyExistsException extends Throwable {

    Object message;
    public UserIsAlreadyExistsException(Object msg) {
        this.message = msg;
    }


}
