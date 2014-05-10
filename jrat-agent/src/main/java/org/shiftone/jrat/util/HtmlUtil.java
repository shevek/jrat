package org.shiftone.jrat.util;


import java.util.Collection;
import java.util.Iterator;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class HtmlUtil {

    public static String toHtml(Collection collection) {

        StringBuffer sb = new StringBuffer();

        sb.append("<ul>");

        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            sb.append("<li>");
            sb.append(iterator.next());
            sb.append("</li>");
        }

        sb.append("</ul>");

        return sb.toString();
    }
}
