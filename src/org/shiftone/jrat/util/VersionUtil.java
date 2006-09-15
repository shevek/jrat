package org.shiftone.jrat.util;



import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.log.Logger;

import java.util.Properties;


public class VersionUtil {

    private static final Logger LOG         = Logger.getLogger(VersionUtil.class);
    private static VersionUtil  versionUtil = null;
    private String              version;
    private String              buildOn;
    private String              builtBy;

    private VersionUtil() {

        try
        {
            Properties properties = ResourceUtil.getResourceAsProperties("jrat-version.properties");

            version = properties.getProperty("version");
            buildOn = properties.getProperty("built_on");
            builtBy = properties.getProperty("built_by");
        }
        catch (Exception e)
        {
            version = buildOn = builtBy = "?";
        }
    }


    private static synchronized VersionUtil getVersionUtil() {

        if (versionUtil == null)
        {
            versionUtil = new VersionUtil();
        }

        return versionUtil;
    }


    public static String getVersion() {
        return getVersionUtil().version;
    }


    public static String getBuiltBy() {
        return getVersionUtil().builtBy;
    }


    public static String getBuiltOn() {
        return getVersionUtil().buildOn;
    }
}
