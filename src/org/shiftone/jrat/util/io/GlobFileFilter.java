package org.shiftone.jrat.util.io;


import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.regex.GlobMatcher;

import java.io.File;


/**
 * Class GlobFileFilter
 *
 * @author Jeff Drost
 *
 */
public class GlobFileFilter extends javax.swing.filechooser.FileFilter implements java.io.FileFilter {

    private static final Logger LOG          = Logger.getLogger(GlobFileFilter.class);
    private GlobMatcher[]       globMatchers = null;
    private String              description  = null;

    public GlobFileFilter(String[] globPatterns, String description) {

        StringBuffer sb = new StringBuffer(" (");

        this.globMatchers = new GlobMatcher[globPatterns.length];

        for (int i = 0; i < globPatterns.length; i++)
        {
            globMatchers[i] = new GlobMatcher(globPatterns[i]);

            if (i != 0)
            {
                sb.append(", ");
            }

            sb.append(globPatterns[i]);
        }

        sb.append(")");

        this.description = description + sb.toString();
    }


    /**
     * Method accept
     */
    public boolean accept(File f) {

        if (f.isDirectory())
        {
            return true;
        }

        for (int i = 0; i < globMatchers.length; i++)
        {
            if (globMatchers[i].isMatch(f.getName()))
            {
                return true;
            }
        }

        return false;
    }


    /**
     * Method getDescription
     */
    public String getDescription() {
        return description;
    }
}
