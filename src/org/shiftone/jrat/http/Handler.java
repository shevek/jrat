package org.shiftone.jrat.http;

/**
 * This is basicly a lightweight Servlet.
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface Handler {

    public interface ContentType {
        public static final String TEXT_HTML = "text/html";
        public static final String TEXT_XML = "text/xml";
        public static final String TEXT_PLAIN = "text/plain";
        public static final String OCTET_STREAM = "application/octet-stream";
    }

    public void handle(Request request, Response response) throws Exception;

}
