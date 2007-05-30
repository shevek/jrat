package org.shiftone.jrat.util.io;


import java.io.File;


/**
 * @author Jeff Drost
 */
public class Dir extends File {

    public Dir(String pathname) {
        super(pathname);
    }


    public Dir(String parent, String child) {
        super(parent, child);
    }


    public Dir(File parent, String child) {
        super(parent, child);
    }


    public File createChild(String childName) {
        return new File(this, childName);
    }


    public Dir createChildDir(String childName) {
        return new Dir(createChild(childName).getAbsolutePath());
    }


    public void make() {
        IOUtil.mkdir(this);
    }
}
