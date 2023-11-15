package com.ment09.starter.util.exceptions;

public class UserIsAlreadyExistsException extends Throwable {

    public UserIsAlreadyExistsException(Object msg) {super(msg.toString());}

}
