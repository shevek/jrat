package org.shiftone.jrat.util.collection;



import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.Assert;

import java.util.LinkedList;


/**
 * Simple wrapper around a LinkedList.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.3 $
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
