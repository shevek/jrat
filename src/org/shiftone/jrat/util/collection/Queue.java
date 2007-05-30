package org.shiftone.jrat.util.collection;


import org.shiftone.jrat.util.Assert;
import org.shiftone.jrat.util.log.Logger;

import java.util.LinkedList;


/**
 * Simple wrapper around a LinkedList.
 *
 * @author Jeff Drost
 *
 */
public class Queue {

    private static final Logger LOG        = Logger.getLogger(Queue.class);
    private LinkedList          linkedList = new LinkedList();

    public void enqueue(Object object) {
        Assert.assertNotNull("object", object);
        linkedList.addLast(object);
    }


    public Object dequeue() {

        return (linkedList.size() > 0)
               ? linkedList.removeFirst()
               : null;
    }


    public boolean isEmpty() {
        return (linkedList.size() == 0);
    }


    public int size() {
        return linkedList.size();
    }
}
