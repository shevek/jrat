package org.shiftone.jrat.http;

/**
 * @Author Jeff Drost
 */
public class Status {

    public static final Status STATUS_100 = new Status(100, "Continue");
    public static final Status STATUS_101 = new Status(101, "Switching Protocols");
    public static final Status STATUS_200 = new Status(200, "OK");
    public static final Status STATUS_201 = new Status(201, "Created");
    public static final Status STATUS_202 = new Status(202, "Accepted");
    public static final Status STATUS_203 = new Status(203, "Non-Authoritative Information");
    public static final Status STATUS_204 = new Status(204, "No Content");
    public static final Status STATUS_205 = new Status(205, "Reset Content");
    public static final Status STATUS_206 = new Status(206, "Partial Content");
    public static final Status STATUS_300 = new Status(300, "Multiple Choices");
    public static final Status STATUS_301 = new Status(301, "Moved Permanently");
    public static final Status STATUS_302 = new Status(302, "Found");
    public static final Status STATUS_303 = new Status(303, "See Other");
    public static final Status STATUS_304 = new Status(304, "Not Modified");
    public static final Status STATUS_305 = new Status(305, "Use Proxy");
    public static final Status STATUS_307 = new Status(307, "Temporary Redirect");
    public static final Status STATUS_400 = new Status(400, "Bad Request");
    public static final Status STATUS_401 = new Status(401, "Unauthorized");
    public static final Status STATUS_402 = new Status(402, "Payment Required");
    public static final Status STATUS_403 = new Status(403, "Forbidden");
    public static final Status STATUS_404 = new Status(404, "Not Found");
    public static final Status STATUS_405 = new Status(405, "Method Not Allowed");
    public static final Status STATUS_406 = new Status(406, "Not Acceptable");
    public static final Status STATUS_407 = new Status(407, "Proxy Authentication Required");
    public static final Status STATUS_408 = new Status(408, "Request Time-out");
    public static final Status STATUS_409 = new Status(409, "Conflict");
    public static final Status STATUS_410 = new Status(410, "Gone");
    public static final Status STATUS_411 = new Status(411, "Length Required");
    public static final Status STATUS_412 = new Status(412, "Precondition Failed");
    public static final Status STATUS_413 = new Status(413, "Request Entity Too Large");
    public static final Status STATUS_414 = new Status(414, "Request-URI Too Large");
    public static final Status STATUS_415 = new Status(415, "Unsupported Media Type");
    public static final Status STATUS_416 = new Status(416, "Requested range not satisfiable");
    public static final Status STATUS_417 = new Status(417, "Expectation Failed");
    public static final Status STATUS_500 = new Status(500, "Internal Server Error");
    public static final Status STATUS_501 = new Status(501, "Not Implemented");
    public static final Status STATUS_502 = new Status(502, "Bad Gateway");
    public static final Status STATUS_503 = new Status(503, "Service Unavailable");
    public static final Status STATUS_504 = new Status(504, "Gateway Time-out");
    public static final Status STATUS_505 = new Status(505, "HTTP Version not supported");

    public static final Status OK = STATUS_200;

    private final int code;
    private final String message;

    public Status(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
