package org.shiftone.jrat.test.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Draws a pie chart over and over for 10 seconds.  Reports on how many redraws were achieved.
 * <p>
 * On my PC (SuSE Linux 8.2, JDK 1.4, 256mb RAM, 2.66ghz Pentium) I get 90-95 charts per second.
 *
 */
public class ChartTiming1 implements ActionListener {

    /** A flag that indicates when time is up. */
    private boolean finished;

    /**
     * Creates a new application.
     */
    public ChartTiming1() {
        // nothing to do
    }

    /**
     * Runs the timing.
     */
    public void run() {
        this.finished = false;

        // create a dataset...
        final DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("One", new Double(10.3));
        data.setValue("Two", new Double(8.5));
        data.setValue("Three", new Double(3.9));
        data.setValue("Four", new Double(3.9));
        data.setValue("Five", new Double(3.9));
        data.setValue("Six", new Double(3.9));

        // create a pie chart...
        final boolean withLegend = true;
        final JFreeChart chart = ChartFactory.createPieChart(
            "Testing",
            data,
            withLegend,
            true,
            false
        );

        final BufferedImage image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g2 = image.createGraphics();
        final Rectangle2D chartArea = new Rectangle2D.Double(0, 0, 400, 300);

        // set up the timer...

        for (int i = 0 ; i < 1000 ; i ++ ) {

            chart.draw(g2, chartArea, null, null);
	        if (i % 10 ==0) {
		        System.out.println(i);
	        }
        }
        System.out.println("DONE");

    }

    /**
     * Receives notification of action events (in this case, from the Timer).
     *
     * @param event  the event.
     */
    public void actionPerformed(final ActionEvent event) {
        this.finished = true;
    }

    /**
     * Starting point for the application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final ChartTiming1 app = new ChartTiming1();
        app.run();

    }

}