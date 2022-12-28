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
            case EMPTY_REQUEST -> new InputIsEmptyException(httpStatus, msg);
            case UPLOAD_FAILED -> new UploadImageException(httpStatus, msg);
            case INVALID_PASSWORD -> new PasswordValidateException(httpStatus, msg);
            case BOOKING_EXPIRED -> new BookingExpiredException(httpStatus, msg);

            default -> new RuntimeException(msg);
        };
    }

    @Setter
    @Getter
    public static class PasswordValidateException extends RuntimeException {
        private final HttpStatus statusCode;
        public PasswordValidateException(HttpStatus statusCode, String msg) {
          super(msg);
            this.statusCode = statusCode;
        }
     }

    @Setter
    @Getter
    public static class BookingExpiredException extends RuntimeException {
        private final HttpStatus statusCode;
        public BookingExpiredException(HttpStatus statusCode, String msg) {
            super(msg);
            this.statusCode = statusCode;
        }
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

    @Setter
    @Getter
    public static class InputIsEmptyException extends RuntimeException {
        private final HttpStatus statusCode;
        public InputIsEmptyException(HttpStatus statusCode, String msg) {
            super(msg);
            this.statusCode = statusCode;
        }
    }

    @Setter
    @Getter
    public static class UploadImageException extends RuntimeException {
        private final HttpStatus statusCode;
        public UploadImageException(HttpStatus statusCode, String msg) {
            super(msg);
            this.statusCode = statusCode;
        }
    }


    FlyketException() {}
}
