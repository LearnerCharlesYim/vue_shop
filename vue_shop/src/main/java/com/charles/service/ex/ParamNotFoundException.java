package com.charles.service.ex;

public class ParamNotFoundException extends ServiceException{
    public ParamNotFoundException() {
    }

    public ParamNotFoundException(String message) {
        super(message);
    }

    public ParamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamNotFoundException(Throwable cause) {
        super(cause);
    }

    public ParamNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
