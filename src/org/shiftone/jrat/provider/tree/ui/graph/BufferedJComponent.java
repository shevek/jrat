package org.shiftone.jrat.provider.tree.ui.graph;


import org.shiftone.jrat.util.log.Logger;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;


public abstract class BufferedJComponent extends JComponent {

    private static final Logger LOG = Logger.getLogger(BufferedJComponent.class);
    private int bufferWidth;
    private int bufferHeight;
    private Image bufferImage;
    private Graphics2D bufferGraphics;

    public void dataChanged() {
        destroyBuffer();
    }


    public void paint(Graphics g) {
        if ((bufferWidth != getSize().width) || (bufferHeight != getSize().height) || (bufferImage == null)
                || (bufferGraphics == null)) {
            createBuffer();
            paintBuffer(bufferGraphics);
        }

        if (bufferGraphics != null) {
            g.drawImage(bufferImage, 0, 0, this);
        }
    }


    private void destroyBuffer() {

        if (bufferGraphics != null) {
            bufferGraphics.dispose();

            bufferGraphics = null;
        }

        if (bufferImage != null) {
            bufferImage.flush();

            bufferImage = null;
        }
    }


    private void createBuffer() {

        LOG.debug("resetBuffer");

        bufferWidth = getSize().width;
        bufferHeight = getSize().height;

        destroyBuffer();

        bufferImage = createImage(bufferWidth, bufferHeight);
        bufferGraphics = (Graphics2D) bufferImage.getGraphics();
    }


    protected abstract void paintBuffer(Graphics2D g);
}
