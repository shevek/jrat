package org.shiftone.jrat.core.output;


/**
 * @author Jeff Drost
 */
public class FileInfo {

    private String requestedName;
    private String actualName;
    private long creationTime;

    public FileInfo(String requestedName, String actualName, long creationTime) {

        this.requestedName = requestedName;
        this.actualName = actualName;
        this.creationTime = creationTime;
    }


    public String getRequestedName() {
        return requestedName;
    }


    public String getActualName() {
        return actualName;
    }


    public long getCreationTime() {
        return creationTime;
    }
}
