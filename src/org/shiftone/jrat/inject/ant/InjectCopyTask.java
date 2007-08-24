package org.shiftone.jrat.inject.ant;


import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;
import org.shiftone.jrat.core.criteria.IncludeExcludeMethodCriteria;
import org.shiftone.jrat.core.criteria.MatcherMethodCriteria;
import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.inject.process.ArchiveFileProcessor;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * Apache Ant Task that extends the common Copy task.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class InjectCopyTask extends Copy {

    private static final Logger LOG = Logger.getLogger(InjectCopyTask.class);
    private Injector injector;
    private IncludeExcludeMethodCriteria methodCriteria;

    public InjectCopyTask() {

        injector = new Injector();
        methodCriteria = new IncludeExcludeMethodCriteria();

        injector.setMethodCriteria(methodCriteria);
    }


    public MatcherMethodCriteria createInclude() {

        MatcherMethodCriteria newCriteria = new MatcherMethodCriteria();

        methodCriteria.addPositive(newCriteria);

        return newCriteria;
    }


    public MatcherMethodCriteria createExclude() {

        MatcherMethodCriteria newCriteria = new MatcherMethodCriteria();

        methodCriteria.addNegative(newCriteria);

        return newCriteria;
    }


    private void copyOrInjectFile(String fromFile, String toFile, FilterSetCollection executionFilters)
            throws Exception {

        String extention = IOUtil.getExtention(fromFile);

        if ((extention != null)
                && ("class".equalsIgnoreCase(extention) || ArchiveFileProcessor.isArchiveExtention(extention))) {
            injector.inject(fromFile, toFile);
        } else {
            if (fromFile.equals(toFile)) {
                log("Skipping self-copy of " + fromFile, verbosity);
            } else {

                //log("Copying " + fromFile, Project.MSG_VERBOSE);
                getFileUtils().copyFile(     //
                        fromFile,                //
                        toFile,                  //
                        executionFilters,        // token filtering
                        getFilterChains(),       // token filtering
                        forceOverwrite,          //
                        preserveLastModified,    //
                        getEncoding(),           //
                        getProject());
            }
        }
    }


    private String getString(Object to) {

        // http://sourceforge.net/tracker/index.php?func=detail&aid=1011493&group_id=41841&atid=431431
        if (to instanceof String) {
            return (String) to;
        } else if (to instanceof String[]) {
            return ((String[]) to)[0];
        } else {
            throw new RuntimeException("Error - please report a bug : " + to.getClass().getName());
        }
    }


    /**
     * Actually does the file (and possibly empty directory) copies. This is a
     * good method for subclasses to override.
     */
    protected void doFileOperations() {

        Iterator sourceFiles;
        Map orderedCopyMap = new TreeMap(fileCopyMap);

        if (orderedCopyMap.size() > 0) {
            log("Copying " + orderedCopyMap.size() + " file" + ((orderedCopyMap.size() == 1)
                    ? ""
                    : "s") + " to " + destDir.getAbsolutePath(), verbosity);

            sourceFiles = orderedCopyMap.keySet().iterator();

            // injector.setForceOverwrite(forceOverwrite);
            // injector.setPreserveLastModified(preserveLastModified);
            while (sourceFiles.hasNext()) {
                String fromFile = (String) sourceFiles.next();
                Object to = orderedCopyMap.get(fromFile);
                String toFile;

                // http://sourceforge.net/tracker/index.php?func=detail&aid=1011493&group_id=41841&atid=431431
                toFile = getString(to);

                //log(fromFile, verbosity);
                try {
                    FilterSetCollection executionFilters = new FilterSetCollection();

                    if (filtering) {
                        executionFilters.addFilterSet(getProject().getGlobalFilterSet());
                    }

                    for (Enumeration filterEnum = getFilterSets().elements(); filterEnum.hasMoreElements();) {
                        executionFilters.addFilterSet((FilterSet) filterEnum.nextElement());
                    }

                    copyOrInjectFile(fromFile, toFile, executionFilters);
                }
                catch (Exception e) {
                    LOG.error("InjectCopyTask error", e);

                    throw new BuildException("Error instramenting " + fromFile, e, getLocation());
                }
            }
        }

        if (includeEmpty) {
            copyEmptyDirectories();
        }
    }


    private void copyEmptyDirectories() {

        Enumeration e = dirCopyMap.elements();
        int count = 0;

        while (e.hasMoreElements()) {
            File d = new File(getString(e.nextElement()));

            if (!d.exists()) {
                if (!d.mkdirs()) {
                    log("Unable to create directory " + d.getAbsolutePath(), Project.MSG_ERR);
                } else {
                    count++;
                }
            }
        }

        if (count > 0) {
            log("Copied " + count + " empty director" + ((count == 1)
                    ? "y"
                    : "ies") + " to " + destDir.getAbsolutePath(), verbosity);
        }
    }


    public void execute() throws BuildException {

        try {
            super.execute();
        }
        catch (BuildException e) {
            LOG.error("copy failed", e);

            throw e;
        }
        catch (Throwable e) {
            LOG.error("copy failed", e);

            throw new BuildException(e);
        }
    }
}
