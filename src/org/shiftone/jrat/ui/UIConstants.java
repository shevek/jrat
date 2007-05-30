package org.shiftone.jrat.ui;


import org.shiftone.jrat.util.VersionUtil;
import org.shiftone.jrat.util.io.GlobFileFilter;

import java.util.ResourceBundle;


/**
 * Interface UIConstants
 *
 * @author Jeff Drost
 */
public interface UIConstants {

    UserSettings SETTINGS = UserSettings.INSTANCE;
    String BUNDLE_NAME = "org.shiftone.jrat.ui.app";
    ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
    String UI_TITLE = BUNDLE.getString("jrat.ui.title") + " v" + VersionUtil.getVersion();
    String WEBSITE = BUNDLE.getString("jrat.website");
    String EMAIL = BUNDLE.getString("jrat.email");
    String ABOUT_TITLE = BUNDLE.getString("jrat.ui.about.title");
    String CHOOSE_INJECT_JAR_TITLE = BUNDLE.getString("jrat.ui.choose_inject_jar.title");
    String CHOOSE_INJECT_DIR_TITLE = BUNDLE.getString("jrat.ui.choose_inject_dir.title");
    String MENU_FILE = BUNDLE.getString("jrat.ui.menu.file");
    String MENU_WINDOW = BUNDLE.getString("jrat.ui.menu.window");
    String MENU_INSTRUMENT = BUNDLE.getString("jrat.ui.menu.instrument");
    String MENU_HELP = BUNDLE.getString("jrat.ui.menu.help");
    String MENU_OPEN = BUNDLE.getString("jrat.ui.menu.open");
    String MENU_CLOSE = BUNDLE.getString("jrat.ui.menu.close");
    String MENU_CLOSE_ALL = BUNDLE.getString("jrat.ui.menu.close_all");
    String MENU_SPAWN = BUNDLE.getString("jrat.ui.menu.spawn");
    String MENU_640X480 = BUNDLE.getString("jrat.ui.menu.640x480");
    String MENU_800x600 = BUNDLE.getString("jrat.ui.menu.800x600");
    String MENU_EXIT = BUNDLE.getString("jrat.ui.menu.exit");
    String MENU_ABOUT = BUNDLE.getString("jrat.ui.menu.about");
    String MENU_DOCS = BUNDLE.getString("jrat.ui.menu.docs");
    String MENU_LICENSE = BUNDLE.getString("jrat.ui.menu.license");
    String MENU_INJECT_JAR = BUNDLE.getString("jrat.ui.menu.inject_jar");
    String MENU_INJECT_DIR = BUNDLE.getString("jrat.ui.menu.inject_dir");
    String MENU_TREE_SPAWN_ROOT = BUNDLE.getString("jrat.tree.ui.menu.spawn_root");
    String MENU_TREE_STAT_VIEW = BUNDLE.getString("jrat.tree.ui.menu.stat");
    String MENU_TREE_TOUCHGRAPH = BUNDLE.getString("jrat.tree.ui.menu.touch");
    String YES = BUNDLE.getString("jrat.ui.yes");
    String NO = BUNDLE.getString("jrat.ui.no");
    String ICON = BUNDLE.getString("jrat.ui.resource.icon");
    float FRAME_WIDTH_PCT = 0.8f;
    float FRAME_HEIGHT_PCT = 0.8f;

    //
    GlobFileFilter OUTPUT_FILE_FILTER = new GlobFileFilter(new String[]{"*.xrat", "*.xrat.gz", "*.jrat", "*.jrat.gz"},
            "JRat Output File");
    GlobFileFilter INJECT_FILE_FILTER = new GlobFileFilter(new String[]
            {
                    "*.jar", "*.war", "*.ear", "*.sar", "*.har", "*.zip", "*.class"
            }, "Injectable File");
}
