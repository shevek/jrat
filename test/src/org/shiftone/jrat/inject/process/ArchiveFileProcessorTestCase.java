package org.shiftone.jrat.inject.process;

import junit.framework.TestCase;
import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;

/**
 * @author Jeff Drost
 * @version $Revision: 1.3 $
 */
public class ArchiveFileProcessorTestCase extends TestCase {

	private static final Logger  LOG = Logger.getLogger(ArchiveFileProcessorTestCase.class);


	public void testOne() throws Exception {

	   File inFile = new File("C:/test.zip");
	   File outFile = new File("C:/_test-out.zip");

	   ArchiveFileProcessor processor = new ArchiveFileProcessor();
	   InjectorOptions options = new InjectorOptions();
	   processor.processFile(new Transformer(), options, inFile, outFile);
	}
}
