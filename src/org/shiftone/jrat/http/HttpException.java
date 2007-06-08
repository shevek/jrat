package org.shiftone.jrat.http;

/**
 * @Author Jeff Drost
 */
public class HttpException extends RuntimeException {
    private final Status status;

    public HttpException(Status status) {
        this.status = status;
    }
 
    public Status getStatus() {
        return status;
    }
}
