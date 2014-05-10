package org.shiftone.jrat.core.web.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Response {

    private static final Logger LOG = Logger.getLogger(Response.class);
    private final OutputStream outputStream;
    private final Writer writer;
    private final ResponseWriter responseWriter = new ResponseWriter();
    private final ResponseOutputStream responseOutputStream = new ResponseOutputStream();
    private boolean committed = false;
    private Status status = Status.OK;
    private String contentType;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.writer = new OutputStreamWriter(outputStream);

    }

    public Writer getWriter() {
        return responseWriter;
    }

    public OutputStream getOutputStream() {
        return responseOutputStream;
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

        String statusLine = "HTTP/1.1 " + status.getCode() + " " + status.getMessage();
        LOG.info(statusLine);

        writer.write(statusLine + "\n");
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
            writeHeaders();
            committed = true;
        }
    }

    private class ResponseOutputStream extends OutputStream {

        @Override
        public void write(int b) throws IOException {
            commit();
            outputStream.write(b);
        }

        @Override
        public void write(byte b[]) throws IOException {
            commit();
            outputStream.write(b);
        }

        @Override
        public void write(byte b[], int off, int len) throws IOException {
            commit();
            outputStream.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            commit();
            outputStream.flush();
        }

        @Override
        public void close() throws IOException {
            commit();
            outputStream.close();
        }
    }

    private class ResponseWriter extends Writer {

        @Override
        public void write(char cbuf[], int off, int len) throws IOException {
            commit();
            writer.write(cbuf, off, len);
        }

        @Override
        public void flush() throws IOException {
            commit();
            writer.flush();
        }

        @Override
        public void close() throws IOException {
            commit();
            writer.close();
        }

        @Override
        public void write(int c) throws IOException {
            commit();
            super.write(c);
        }

        @Override
        public void write(char cbuf[]) throws IOException {
            commit();
            super.write(cbuf);
        }

        @Override
        public void write(String str) throws IOException {
            commit();
            super.write(str);
        }

        @Override
        public void write(String str, int off, int len) throws IOException {
            commit();
            super.write(str, off, len);
        }
    }

}
