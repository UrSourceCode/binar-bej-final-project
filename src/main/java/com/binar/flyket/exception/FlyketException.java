package com.binar.flyket.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FlyketException {


    public static RuntimeException throwException(ExceptionType exceptionType, HttpStatus httpStatus, String msg) {
        return switch (exceptionType) {
            case NOT_FOUND -> new EntityNotFoundException(httpStatus, msg);
            case INVALID_EMAIL -> new EmailValidateException(httpStatus, msg);
            case DUPLICATE_ENTITY -> new DuplicateEntityException(httpStatus, msg);
            default -> new RuntimeException(msg);
        };
    }


    @Setter
    @Getter
    public static class EmailValidateException extends RuntimeException {
        private final HttpStatus statusCode;
        public EmailValidateException(HttpStatus statusCode, String msg) {
            super(msg);
            this.statusCode = statusCode;
        }
    }


    @Setter
    @Getter
    public static class DuplicateEntityException extends RuntimeException {
        private final HttpStatus statusCode;

        public DuplicateEntityException(HttpStatus statusCode, String msg) {
            super(msg);
            this.statusCode = statusCode;
        }
    }

    @Setter
    @Getter
    public static class EntityNotFoundException extends RuntimeException {
        private final HttpStatus statusCode;
        public EntityNotFoundException(HttpStatus statusCode, String msg) {
            super(msg);
            this.statusCode = statusCode;
        }
    }


    FlyketException() {}
}
