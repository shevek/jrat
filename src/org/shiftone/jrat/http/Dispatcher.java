package org.shiftone.jrat.http;

import org.shiftone.jrat.util.log.Logger;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Iterator;
import java.io.Writer;

/**
 * @Author Jeff Drost
 */
public class Dispatcher implements Handler {

    private static final Logger LOG = Logger.getLogger(Dispatcher.class);
    private String title;
    private SortedMap contexts = new TreeMap(); /* <String, Handler> */

    public Dispatcher(String title) {
        this.title = title;
    }

    public void addRoute(String path, Handler handler) {
        contexts.put(path, handler);        
    }

    public void handle(Request request, Response response) throws Exception {

        LOG.info("handle");

        String path = request.getRequestUri();
        if (path.length() > 0 && path.charAt(0) == '/') {
            path = path.substring(1);
        }

        int firstSlash = path.indexOf("/");
        String firstPart = (firstSlash == -1)
                ? path
                : path.substring(0, firstSlash);

        LOG.info("firstPart = " + firstPart);
        Handler handler = (Handler)contexts.get(firstPart);

        if (handler != null) {

            // trim the firstPart off the URI
            request.setRequestUri( path.substring(firstPart.length()));
            handler.handle(request, response);
            //return;

        } else {

            listHandlers(request, response);

        }

    }

    private void listHandlers(Request request, Response response) throws Exception {

        LOG.info("listHandlers");
        response.setContentType(ContentType.TEXT_HTML);

        Iterator iterator = contexts.entrySet().iterator();

        Writer out = response.getWriter();

        out.write("<h2>" + title + "</h2>");
        out.write("<table border='1'>");

        while (iterator.hasNext()) {

            Map.Entry entry = (Map.Entry)iterator.next();
            out.write("<tr>");

            out.write("<td><a href='" + entry.getKey() + "/'>" + entry.getKey()  + "</a></td>");
            out.write("<td>" + entry.getValue() + "</td>");

            out.write("</tr>");

        }

        out.write("</table>");
    }

}
