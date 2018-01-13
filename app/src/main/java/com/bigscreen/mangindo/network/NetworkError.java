package com.bigscreen.mangindo.network;


import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class NetworkError {

    private static final int ERROR_UNKNOWN = 0;
    private static final int ERROR_SERVER = 1;
    private static final int ERROR_CONNECTION = 2;

    public static final String MESSAGE_ERROR = "Something went wrong. Please try again.";
    private static final String MESSAGE_ERROR_SERVER = "Server error. Please try again.";
    private static final String MESSAGE_ERROR_CONNECTION = "Connection error. Please Try again.";

    private Throwable throwable;

    public NetworkError(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getErrorMessage() {
        switch (getErrorType()) {
            case ERROR_SERVER:
                return MESSAGE_ERROR_SERVER;
            case ERROR_CONNECTION:
                return MESSAGE_ERROR_CONNECTION;
            default:
                return throwable.getMessage();
        }
    }

    private int getErrorType() {
        if (throwable instanceof HttpException)
            return ERROR_SERVER;
        else if (throwable instanceof IOException)
            return ERROR_CONNECTION;
        else
            return ERROR_UNKNOWN;
    }
}
