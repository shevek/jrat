package org.shiftone.jrat.http;

import org.shiftone.jrat.util.log.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @Author Jeff Drost
 */
public class Response {


    private static final Logger LOG = Logger.getLogger(Response.class);

    private final Writer writer;
    private final ResponseWriter responseWriter = new ResponseWriter();
    private boolean committed = false;
    private Status status = Status.OK;
    private String contentType;


    public Response(OutputStream outputStream) {        
        this.writer = new OutputStreamWriter(outputStream);
    }

    public Writer getWriter() {
        return responseWriter;
    }


    public void setStatus(Status status) {
        this.status = status;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void flush() throws IOException {
        commit();
        writer.flush();
    }

    private void writeHeaders() throws IOException {

        String statusLine = "HTTP/1.1 " + status.getCode() + " " + status.getMessage() + "\n";
        LOG.info(statusLine);

        writer.write(statusLine);
        writer.write("Content-Type: " + contentType + "\n");
        writer.write("Cache-Control: no-store, no-cache, must-revalidate\n");    // normal
        writer.write("Cache-Control: post-check=0, pre-check=0");                // IE
        writer.write("Pragma: no-cache\n");                                      // good luck
        writer.write("Expires: Sat, 6 May 1995 12:00:00 GMT\n");                 // more lock

        writer.write("\n");


    }

    private void commit() throws IOException {

        if (!committed) {
            writeHeaders();
            committed = true;
        }
    }

    private class ResponseWriter extends Writer {

        public void write(char cbuf[], int off, int len) throws IOException {
            commit();
            writer.write(cbuf, off, len);
        }

        public void flush() throws IOException {
            commit();
            writer.flush();
        }

        public void close() throws IOException {
            commit();
            writer.close();
        }

    }

}
