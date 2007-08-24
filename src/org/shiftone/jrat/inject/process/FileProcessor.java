package org.shiftone.jrat.inject.process;


import org.shiftone.jrat.inject.InjectorOptions;
import org.shiftone.jrat.inject.bytecode.Transformer;

import java.io.File;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public interface FileProcessor {
    public void process(Transformer transformer, InjectorOptions options, File source, File target);
}
