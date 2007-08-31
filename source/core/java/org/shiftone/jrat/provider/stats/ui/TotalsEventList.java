package org.shiftone.jrat.provider.stats.ui;


import ca.odell.glazedlists.AbstractEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.MethodKeyAccumulator;
import org.shiftone.jrat.util.log.Logger;

import java.util.Collection;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TotalsEventList extends AbstractEventList implements ListEventListener {

    private static final Logger LOG = Logger.getLogger(TotalsEventList.class);
    private EventList eventList;
    private MethodKey methodKey = null;       // todo
    private MethodKeyAccumulator totals = new MethodKeyAccumulator(methodKey);

    public TotalsEventList(EventList eventList) {

        this.eventList = eventList;

        eventList.addListEventListener(this);

        super.readWriteLock = eventList.getReadWriteLock();

        recalculate();
    }


    public void listChanged(ListEvent listEvent) {
        recalculate();
    }


    public void recalculate() {

        updates.beginEvent();

        MethodKeyAccumulator newTotals = new MethodKeyAccumulator(methodKey);

        for (int i = 0; i < eventList.size(); i++) {
            MethodKeyAccumulator accumulator = (MethodKeyAccumulator) eventList.get(i);

            newTotals.combine(accumulator);
        }

        this.totals = newTotals;

        updates.addUpdate(0);
        updates.commitEvent();
    }


    public Object[] toArray(Object[] a) {
        return new Object[]{totals};
    }


    public boolean containsAll(Collection c) {
        return false;
    }


    public boolean removeAll(Collection c) {
        return false;
    }


    public boolean retainAll(Collection c) {
        return false;
    }


    public Object get(int i) {
        return totals;
    }


    public int size() {
        return 1;
    }
}
