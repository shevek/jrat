package org.shiftone.jrat.core.spi;

import java.io.OutputStream;

/**
 * @author Jeff Drost
 */
public interface Commandlet {

    public interface ContentType {
        public static final String HTML = "text/html";
        public static final String XML = "text/xml";
        public static final String PLAIN = "text/plain";
    }

    public void execute(OutputStream output) throws Exception;

    public String getContentType();

    public String getTitle();

}
