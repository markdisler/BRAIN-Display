import java.awt.Color;

public class CoreConstants {
	
	/**
	 * String ROOT_PATH
	 * This constant is determined at runtime.
	 * It will store the location of the program when opened and used to determine the location of
	 * its dependencies.
	 */
	public static final String ROOT_PATH = System.getProperty("user.dir");
	
	/**
	 * String SCHEDULES_PATH
	 * This is the location of the folder containing the schedule file data appended to the root path.
	 */
	public static final String SCHEDULES_PATH = ROOT_PATH + "/ScheduleFiles/";
		
	/**
	 * String SETTINGS_DATA_PATH
	 * String LOG_OUTPUT_PATH
	 * String THEMES_PATH
	 * String SIDEBAR_TEXT_PATH
	 * These are the paths of several text files that store data essential to the operation of this program.
	 * 
	 * The settings file contains settings that can be modified.  This includes periods displayed, current data
	 * file, theme, and whether or not to ignore weekends in the display.
	 * 
	 * The log output is a log of any issues that occurred or some information for tracking/debugging.
	 * 
	 * The theme file contains possible themes for the viewer.  The file has a certain format.
	 * 
	 * The side bar text is a document that will be displayed in the sidebar on the side.
	 */
	public static final String SETTINGS_DATA_PATH = ROOT_PATH + "/settings.txt";
	public static final String LOG_OUTPUT_PATH = ROOT_PATH + "/log.txt";
	public static final String THEMES_PATH = ROOT_PATH + "/themes.txt";
	public static final String SIDEBAR_TEXT_PATH = ROOT_PATH + "/sidebar.txt";
		
	/**
	 * 	String PERIOD_SETTINGS_KEY
	 * 	String CURRENT_FILE_SETTINGS_KEY
	 *	String SECONDARY_FILE_SETTINGS_KEY
	 *	String THEME_SETTINGS_KEY
	 *  These values represent the keys in the settings file that come before the actual settings values.
	 */
	public static final String PERIOD_SETTINGS_KEY = "period_array";
	public static final String CURRENT_FILE_SETTINGS_KEY = "current_file";
	public static final String SECONDARY_FILE_SETTINGS_KEY = "secondary_file";
	public static final String SECONDARY_START_SETTINGS_KEY = "secondary_start";
	public static final String SECONDARY_END_SETTINGS_KEY = "secondary_end";
	public static final String THEME_SETTINGS_KEY = "theme";
	public static final String DYNAMIC_PERIOD_KEY = "dynamic_period";
	public static final String TOP_BAR_LIGHT_KEY = "top_bar_light";
	
	/**
	 * String UNSPECIFIED_SETTING
	 * A string that signifies that no value has been given to this setting.
	 */
	public static final String UNSPECIFIED_SETTING = "[n/a]";
	
	/**
	 * String FONT_TYPE
	 * This is the font name used for the viewer.
	 */
	public static final String FONT_TYPE = "Kalinga";
	public static final String LIST_PADDING_STRING = "     ";
	
	public static final Color SETTINGS_SECTION_COLOR = Color.WHITE;
	public static final Color ACCENT_COLOR = new Color(137, 215, 255);
	
}
