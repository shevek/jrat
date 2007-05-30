package org.shiftone.jrat.event;

import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.core.output.FileOutputFactory;
import org.shiftone.jrat.core.output.FileOutputRegistry;
import org.shiftone.jrat.core.shutdown.ShutdownRegistry;
import org.shiftone.jrat.core.shutdown.ShutdownListener;
import junit.framework.TestCase;

/**
 * @author Jeff Drost
 *
 */
public class ShutdownRegistryTestCase extends TestCase {
	private static final Logger LOG = Logger.getLogger(ShutdownRegistryTestCase.class);

	public void testOne() {

		ShutdownRegistry shutdownRegistry = new ShutdownRegistry();
		FileOutputRegistry fileOutputRegistry = new FileOutputRegistry();
		FileOutputFactory fileOutputFactory = new FileOutputFactory(fileOutputRegistry , 0, false);

		shutdownRegistry.registerShutdownListener(fileOutputRegistry);
		for (int i = 0 ; i < 1000 ; i ++) {
			shutdownRegistry.registerShutdownListener(new TestShutdownListener(i));
		}
	}

	private class TestShutdownListener implements ShutdownListener {
		 private int i;

		public TestShutdownListener(int i) {
			this.i = i;
		}

		public void shutdown() {
			LOG.info("shutdown " + i);
			try {
			Thread.sleep(100);
			} catch (Exception e) {
				LOG.error("sleep failed",e);
			}
		}

		public String toString() {
			return "TestShutdownListener#" + i;
		}
	}
}
