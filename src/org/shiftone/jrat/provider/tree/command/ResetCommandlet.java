package org.shiftone.jrat.provider.tree.command;

import org.shiftone.jrat.core.spi.Commandlet;
import org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory;
import org.shiftone.jrat.util.log.AbstractLogCommandlet;
import org.shiftone.jrat.util.log.Logger;

/**
 * @author Jeff Drost
 */
public class ResetCommandlet extends AbstractLogCommandlet implements Commandlet {
	private static final Logger LOG = Logger.getLogger(ResetCommandlet.class);
	private final TreeMethodHandlerFactory treeMethodHandlerFactory;

	public ResetCommandlet(TreeMethodHandlerFactory treeMethodHandlerFactory) {
		this.treeMethodHandlerFactory = treeMethodHandlerFactory;
	}

	public void execute() {
		treeMethodHandlerFactory.reset();
		LOG.info("reset complete");
	}

	public String getTitle() {
		return "Reset Tree Statistics";
	}
}
