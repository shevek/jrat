package org.shiftone.jrat.simulate.stmt;


import org.shiftone.jrat.core.InternalHandler;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.util.log.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Class SimMethodCall
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class MethodCallStatement extends ListStatement {

	private static final Logger LOG = Logger.getLogger(MethodCallStatement.class);
	private static Object[] ARGS = new Object[]{};
	private MethodKey methodKey = null;
	private String klass = null;
	private String method = null;
	private String signature = null;
	private List children = new ArrayList();


	private MethodKey getMethodKey() {

		if (methodKey == null) {
			methodKey = new MethodKey(klass, method, signature);

			LOG.info("new MethodKey " + methodKey);
		}

		return methodKey;
	}


	public void execute(InternalHandler internalHandler) {

		MethodHandler handler;

		long start;


		start = System.currentTimeMillis();
		handler = internalHandler.getMethodHandler(getMethodKey());

		try {
			handler.onMethodStart(this);
			Thread.sleep(20);
			super.execute(internalHandler);
			handler.onMethodFinish(null, System.currentTimeMillis() - start, null);

		}
		catch (Throwable e) {
			handler.onMethodFinish(null, System.currentTimeMillis() - start, e);
		}

	}


	/**
	 * method
	 *
	 * @param string .
	 */
	public void setClass(String string) {
		klass = string;
	}


	/**
	 * method
	 *
	 * @param string .
	 */
	public void setMethod(String string) {
		method = string;
	}


	/**
	 * method
	 *
	 * @param string .
	 */
	public void setSignature(String string) {
		signature = string;
	}
}
