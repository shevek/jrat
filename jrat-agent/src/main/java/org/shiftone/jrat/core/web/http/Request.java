package org.shiftone.jrat.core.web.http;

import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.io.IOUtil;

import java.io.*;
import java.util.*;

/**
 * This object us mutable.
 * I want to be able to modify a request and then redispatch it.  I could have done that by
 * wrapping the request in another implementation of a request, but that's a bit too complex for
 * such a simple http server.  The handler owns the request.  If it wants to change the data and
 * redispatch it, then it can, without any need to wrap it.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Request {

    private static final Logger LOG = Logger.getLogger(Request.class);
    private static final boolean DEBUG = false;
    private String method;
    private String requestUri;
    private String httpVersion;
    private String queryString;
    private String posted;

    private Map headers = new HashMap();

    public Request(InputStream inputStream) throws Exception {

        if (DEBUG) {
            LOG.info("debugging request...");
            byte[] data = IOUtil.readAndClose(inputStream);
            File file = new File("http-" + System.currentTimeMillis() + ".txt");
            FileOutputStream dump = new FileOutputStream(file);
            IOUtil.copy(new ByteArrayInputStream(data), dump);
            dump.flush();
            dump.close();
            LOG.info("wrote " + file.getAbsolutePath());
            inputStream = new ByteArrayInputStream(data);
        }


        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();

        parseRequestLine(line);

        // this code is not expecially robust        
        while (((line = reader.readLine()) != null) && (line.length() > 0)) {
            parseHeaderField(line);
        }

        if (method.equals("POST")) {
            posted = reader.readLine();
        }

    }


    private void parseRequestLine(String line) {

        LOG.info(line);
        int a = line.indexOf(' ');
        int b = line.lastIndexOf(' ');

        method = line.substring(0, a);
        httpVersion = line.substring(b + 1);
        requestUri = line.substring(a + 1, b);

        // query string
        int q = requestUri.indexOf('?');
        if (q != -1) {
            queryString = requestUri.substring(q + 1); // get just the query string
            requestUri = requestUri.substring(0, q);   // remove the query string and ?
        }
    }

    public Parameters getParameters() {
        return (posted != null)
                ? Parameters.fromQueryString(posted)
                : Parameters.fromQueryString(queryString);
    }

    private void parseHeaderField(String line) {
        int a = line.indexOf(':');
        String key = line.substring(0, a);
        String value = line.substring(a + 2);  // remove ": "        
        headers.put(key, value);
    }

    public String getMethod() {
        return method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getQueryString() {
        return queryString;
    }

    public Map getHeaders() {
        return headers;
    }

}
