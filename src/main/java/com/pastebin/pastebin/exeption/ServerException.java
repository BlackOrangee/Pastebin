package com.pastebin.pastebin.exeption;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {

    private final ErrorList error;

    public ServerException(String message, ErrorList errorList) {
        super(message);
        error = errorList;
    }

    public ServerException(String message, Throwable cause, ErrorList errorList) {
        super(message, cause);
        error = errorList;
    }
}
