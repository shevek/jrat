package org.shiftone.jrat.core.web.http;

import org.shiftone.jrat.core.spi.WebAction;
import org.shiftone.jrat.core.spi.WebActionFactory;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.VersionUtil;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class WebActionHandler implements Handler {

    private static final Logger LOG = Logger.getLogger(WebActionHandler.class);
    private static final String DYNAMIC_PREFIX = "/jrat/"; // reserved
    private long dynamicUriCount = 0;
    private Map actionFactories = new HashMap();

    public void add(String uri, WebActionFactory actionFactory) {
        if (uri.startsWith(DYNAMIC_PREFIX)) {
            throw new IllegalArgumentException();
        }
        actionFactories.put(uri, new FactoryHolder(actionFactory, uri));
    }

    public void add(WebActionFactory actionFactory) {
        String uri = DYNAMIC_PREFIX + (dynamicUriCount++);
        actionFactories.put(uri, new FactoryHolder(actionFactory, uri));
    }

    public void handle(Request request, Response response) throws Exception {

        String requestUri = request.getRequestUri();


        LOG.info(requestUri);
        LOG.info(actionFactories.keySet());
        FactoryHolder factoryHolder = (FactoryHolder) actionFactories.get(requestUri);

        if (factoryHolder != null) {
            handle(request, response, factoryHolder.factory);
        } else {
            showListing(request, response);
        }
    }

    public void handle(Request request, Response response, WebActionFactory actionFactory) throws Exception {

        WebAction action = actionFactory.createAction();
        Class actionClass = action.getClass();

        Parameters parameters = request.getParameters();
        Iterator keys = parameters.getKeys().iterator();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            PropertyDescriptor descriptor = new PropertyDescriptor(key, actionClass);
            PropertyEditor propertyEditor = PropertyEditorManager.findEditor(descriptor.getPropertyType());
            propertyEditor.setValue(action);

            propertyEditor.setAsText(parameters.getValue(key));
            descriptor.getWriteMethod().invoke(action, new Object[]{propertyEditor.getValue()});
        }
        action.execute(response);
    }


    public void showListing(Request request, Response response) throws Exception {

        PrintWriter out = new PrintWriter(response.getWriter());
        SortedSet ordered = new TreeSet(actionFactories.values());

        out.write("<html>");
        out.write("<h3>JRat ");
        out.write(VersionUtil.getVersion());
        out.write("</h3>");

        out.write("<ul>");
        for (Iterator i = ordered.iterator(); i.hasNext();) {
            FactoryHolder factoryHolder = (FactoryHolder) i.next();
            out.write("<li><a href='");
            out.write(factoryHolder.uri);
            out.write("'>");
            out.write(factoryHolder.title);
            out.write("</a></li>");
        }
        out.write("</ul>");
        out.write("</html>");
    }

    private class FactoryHolder implements Comparable {
        private final WebActionFactory factory;
        private final String title;
        private final String uri;

        public FactoryHolder(WebActionFactory factory, String uri) {
            this.factory = factory;
            this.title = factory.getTitle();
            this.uri = uri;
        }

        public int compareTo(Object o) {
            FactoryHolder other = (FactoryHolder) o;
            return title.compareTo(other.title);
        }
    }


}
