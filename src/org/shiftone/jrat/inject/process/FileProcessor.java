package org.shiftone.jrat.inject.process;



import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;

import java.io.File;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.6 $
 */
public interface FileProcessor {
    public void process(Transformer transformer, InjectorOptions options, File source, File target);
}
