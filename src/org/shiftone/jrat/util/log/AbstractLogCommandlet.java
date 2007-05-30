package org.shiftone.jrat.util.log;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.util.log.target.LogTarget;
import org.shiftone.jrat.util.log.target.TandemTarget;
import org.shiftone.jrat.util.log.target.WriterLogTarget;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * this is a base class for Commandlets that redirects the log output produced by
 * the command's thread to the command's output stream, and then reverst the logger back.
 *
 * @author Jeff Drost
 */
public abstract class AbstractLogCommandlet implements Commandlet {

    private static final Logger LOG = Logger.getLogger(AbstractLogCommandlet.class);

    protected abstract void execute() throws Exception;

    public final void execute(OutputStream output) throws Exception {

        LogTarget previous = LoggerFactory.getLogTarget();

        Writer outputWriter = new OutputStreamWriter(output);
        WriterLogTarget writer = new WriterLogTarget(outputWriter);
        TandemTarget tandem = new TandemTarget(previous, writer);

        try {

            LoggerFactory.setLogTarget(tandem);
            execute();

        } catch (Throwable e) {

            LOG.error("Commandlet failed", e);

        } finally {

            LoggerFactory.setLogTarget(previous);

        }
    }

    public final String getContentType() {
        return Commandlet.ContentType.PLAIN;
    }

}
