package org.shiftone.jrat.benchmark;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Benchmark {

    String title();

    long iterations() default 10000;

}
