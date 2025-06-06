package com.pastebin.pastebin.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ErrorList {
    SERVER_ERROR(1, HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(2, HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(3, HttpStatus.BAD_REQUEST),
    BAD_REQUEST(4, HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(5, HttpStatus.NOT_FOUND),
    USER_ALREADY_EXIST(6, HttpStatus.CONFLICT),
    USER_NOT_CREATED(7, HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_UPDATED(8, HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_DATE_FORMAT(9, HttpStatus.BAD_REQUEST),
    BOARD_NOT_FOUND(10, HttpStatus.NOT_FOUND),
    BOARD_EXPIRED(11, HttpStatus.BAD_REQUEST),
    ;

    private final int errorCode;
    private final HttpStatus httpStatusCode;
}
