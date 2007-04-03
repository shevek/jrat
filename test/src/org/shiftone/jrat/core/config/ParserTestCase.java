package org.shiftone.jrat.core.config;

import org.shiftone.jrat.util.log.Logger;

import java.io.FileInputStream;


/**
 * @author Jeff Drost
 */
public class ParserTestCase {
	
	private static final Logger LOG = Logger.getLogger(ParserTestCase.class);

	public static void main(String[] args) throws Exception
	{
		ConfigurationParser parser = new ConfigurationParser();
		parser.parse(new FileInputStream("C:/workspace/jrat/trunk/config/jrat.xml"));
	}
}
