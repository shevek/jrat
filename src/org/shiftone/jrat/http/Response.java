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
    private final OutputStream internalStream;
    private final OutputStream outputStream;
    private final Writer writer;
    private boolean committed = false;
    private int status = 200;
    private String contentType;


    public Response(OutputStream outputStream) {
        this.internalStream = outputStream;
        this.outputStream = new ResponseOutputStream();
        this.writer = new OutputStreamWriter(outputStream);
    }


    public Writer getWriter() {
        return writer;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void flush() throws IOException {
        writer.flush();
    }

    private void writeHeaders() throws IOException {
        LOG.info("writeHeaders");
        writer.write("HTTP/1.1 " + status + " OK\n");
        writer.write("Content-Type: " + contentType + "\n");
        writer.write("Cache-Control: no-store, no-cache, must-revalidate\n");    // normal
        writer.write("Cache-Control: post-check=0, pre-check=0");                // IE
        writer.write("Pragma: no-cache\n");                                      // good luck
        writer.write("Expires: Sat, 6 May 1995 12:00:00 GMT\n");                 // more lock
        writer.write("\n");
        writer.flush();
    }

    private void commit() throws IOException {
        if (!committed) {
            committed = true;
            writeHeaders();
        }
    }

    private class ResponseOutputStream extends OutputStream {

        public void write(byte b[]) throws IOException {
            commit();
            outputStream.write(b);
        }

        public void write(byte b[], int off, int len) throws IOException {
            commit();
            outputStream.write(b, off, len);
        }

        public void flush() throws IOException {
            commit();
            outputStream.flush();
        }

        public void close() throws IOException {
            commit();
            outputStream.close();
        }

        public void write(int b) throws IOException {
            commit();
            outputStream.write(b);
        }

    }
}
