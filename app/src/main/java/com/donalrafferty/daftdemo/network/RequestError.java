package com.donalrafferty.daftdemo.network;

/**
 * RequestError
 * This is a basic error class
 * Not utilised in this simple app as yet but useful for Error handling in future
 */
public class RequestError extends Throwable {

    public String status;
    public int errorCode;
    public String errorMessage;

    public RequestError(String status, int code, String message) {
        this.status = status;
        this.errorCode = code;
        this.errorMessage = message;
    }

    public RequestError(String message) {
        this.errorMessage = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
