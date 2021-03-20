package com.lahiru.corebank.exception;

public class DataAccessException extends Exception{

    private String errorCode;

    public DataAccessException(String s, String errorCode) {
        super(s);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
