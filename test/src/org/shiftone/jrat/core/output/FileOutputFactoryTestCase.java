package org.shiftone.jrat.core.output;

import junit.framework.TestCase;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * @author Jeff Drost
 *
 */
public class FileOutputFactoryTestCase extends TestCase {
	private static final Logger LOG = Logger.getLogger(FileOutputFactoryTestCase.class);

	public void testWriter() throws Exception {

		FileOutputRegistry registry = new FileOutputRegistry();
		FileOutputFactory factory = new FileOutputFactory(registry, 0, false);

		Writer writer = factory.createWriter(new File("FileOutputFactoryTestCase.Writer"));

		for (int i = 0; i < 100; i++) {
			writer.write("test " + i + "\n");
		}

		registry.shutdown();
	}

	public void testPrintWriter() throws Exception {

		FileOutputRegistry registry = new FileOutputRegistry();
		FileOutputFactory factory = new FileOutputFactory(registry, 0, false);

		PrintWriter printWriter = factory.createPrintWriter(new File("FileOutputFactoryTestCase.PrintWriter"));

		for (int i = 0; i < 100; i++) {
			printWriter.println("test " + i);
		}

		registry.shutdown();
	}

	public void testOutputStream() throws Exception {

		FileOutputRegistry registry = new FileOutputRegistry();
		FileOutputFactory factory = new FileOutputFactory(registry, 0, false);

		OutputStream outputStream = factory.createOutputStream(new File("FileOutputFactoryTestCase.OutputStream"));

		for (int i = 0; i < 9999; i++) {
			outputStream.write(i);
		}

		registry.shutdown();
	}
}
