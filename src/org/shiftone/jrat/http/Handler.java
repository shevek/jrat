package org.shiftone.jrat.http;

/**
 * @Author Jeff Drost
 */
public interface Handler {

    public interface ContentType {
        public static final String TEXT_HTML = "text/html";
        public static final String TEXT_XML = "text/xml";
        public static final String TEXT_PLAIN = "text/plain";
    }

    public void handle(Request request, Response response) throws Exception;

}
