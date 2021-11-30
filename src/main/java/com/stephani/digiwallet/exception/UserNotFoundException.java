package com.stephani.digiwallet.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;
    private String code;

    public UserNotFoundException(String message) {
        super();
        this.message = message;
        this.code = String.valueOf(HttpStatus.SC_NOT_FOUND);
    }

    public UserNotFoundException(String message, String code) {
        super();
        this.message = message;
        this.code = code;
    }
}
