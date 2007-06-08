package org.shiftone.jrat.http;

/**
 * @Author Jeff Drost
 */
public interface Handler {

    public void handle(Request request, Response response) throws Exception;

}
