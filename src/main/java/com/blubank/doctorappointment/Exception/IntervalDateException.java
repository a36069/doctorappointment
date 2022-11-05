package com.blubank.doctorappointment.Exception;

public class IntervalDateException extends RuntimeException{
    public IntervalDateException() {
    }

    public IntervalDateException(String message) {
        super(message);
    }

    public IntervalDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntervalDateException(Throwable cause) {
        super(cause);
    }

    public IntervalDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
