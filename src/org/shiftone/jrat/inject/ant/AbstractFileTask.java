package org.shiftone.jrat.inject.ant;


import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;


/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public abstract class AbstractFileTask extends Task {

    protected Vector filesets = new Vector();
    protected File file = null;

    /**
     * called by Ant
     */
    public void addFileset(FileSet set) {
        filesets.addElement(set);
    }


    /**
     * called by Ant
     */
    public void setFile(File file) {
        this.file = file;
    }


    protected abstract void validateFile(File file) throws BuildException;


    protected abstract void processFile(File file) throws BuildException;


    private void processFiles(Set fileNames) throws BuildException {

        Iterator iterator = fileNames.iterator();

        while (iterator.hasNext()) {
            processFile((File) iterator.next());
        }
    }


    /**
     * called by Ant
     */
    public void execute() throws BuildException {

        Set files = new TreeSet();
        String fileName = null;
        File fileToAdd = null;

        try {

            // single file
            if (this.file != null) {
                validateFile(this.file);
                files.add(this.file);
            }

            // FileSet(s)
            for (int i = 0; i < filesets.size(); i++) {
                FileSet fs = (FileSet) filesets.elementAt(i);
                DirectoryScanner ds = fs.getDirectoryScanner(getProject());
                File fromDir = fs.getDir(getProject());
                String[] srcFiles = ds.getIncludedFiles();

                for (int x = 0; x < srcFiles.length; x++) {
                    fileName = fromDir + File.separator + srcFiles[x];
                    fileToAdd = new File(fileName);

                    validateFile(fileToAdd);
                    files.add(fileToAdd);
                }
            }

            processFiles(files);
        }
        catch (Throwable e) {
            e.printStackTrace();

            throw new BuildException("file task failed", e);
        }
    }
}
