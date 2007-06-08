package org.shiftone.jrat.http;

import java.io.File;
import java.io.Writer;
import java.io.FileInputStream;

/**
 * @Author Jeff Drost
 */
public class FsBrowseHandler implements Handler {

    private static final String ROOT = new File("").getAbsolutePath();

    public void handle(Request request, Response response) throws Exception {

        response.setContentType(ContentType.TEXT_HTML);
        Writer out = response.getWriter();
        String uri = request.getRequestUri();
        File file = new File(ROOT + uri);

       
        if (file.isDirectory()) {
            
            out.write("<ul>");
            File[] children = file.listFiles();

            for (int i = 0; i < children.length; i++) {
                File child = children[i];
                if (child.isDirectory()) {
                    out.write("<li><a href='" + child.getName() + "/'>" + child.getName() + "/</a>");
                } else {
                    out.write("<li><a href='" + child.getName() + "'>" + child.getName() + "</a>");
                }
            }

            out.write("</ul>");

        } else {

            out.write(file.getAbsolutePath());

        }


    }
}
