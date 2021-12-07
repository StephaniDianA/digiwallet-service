package com.stephani.digiwallet.exception;

public class DataNotFound extends RuntimeException{

    public DataNotFound(String errorMessage) {
        super(errorMessage);
    }
}
