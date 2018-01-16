import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FailsafeController {

	public static void checkForDependencies() {
		File settingsFile = new File(CoreConstants.SETTINGS_DATA_PATH);
		File themesFile = new File(CoreConstants.THEMES_PATH);
		File errorFile = new File(CoreConstants.LOG_OUTPUT_PATH);
		File scheduleFileFolder = new File(CoreConstants.SCHEDULES_PATH);

		if (settingsFile.exists() && !settingsFile.isDirectory()) {
			System.out.println("[!]Settings file located \t [YES]");
			
			String[] requiredKeys = {
				CoreConstants.PERIOD_SETTINGS_KEY,
				CoreConstants.CURRENT_FILE_SETTINGS_KEY,
				CoreConstants.SECONDARY_FILE_SETTINGS_KEY,
				CoreConstants.SECONDARY_START_SETTINGS_KEY,
				CoreConstants.SECONDARY_END_SETTINGS_KEY,
				CoreConstants.THEME_SETTINGS_KEY
			};
			
			boolean changes = Settings.shared().ensureRequiredKeysExist(requiredKeys);
			if (changes) {
				System.out.println("[!]Settings has all keys \t [YES]");
			} else {
				System.out.println("[!]Settings created missing keys \t [YES]");
			}
			
		} else {
			String [] lines = {
					"period_array = 2,3,5,6,7,8,9",
					"current_file = data.txt",
					"secondary_file = [n/a]",
					"secondary_start = [n/a]",
					"secondary_end = [n/a]",
					"theme = Default"
			};
			CoreFunctions.exportToTextFile(CoreConstants.SETTINGS_DATA_PATH, lines);
		}

		if (themesFile.exists() && !themesFile.isDirectory()) {
			System.out.println("[!]Themes file located \t\t [YES]");
		} else {
			String [] lines = {
					"\"Default\"-(80,80,80)-(255,255,255)-(240,240,240)-(0,0,0)-(215,215,215)-(0,0,0)-(0,0,0)-(255,255,255)",
					"\"Default2\"-(220,220,220)-(0,0,0)-(255,255,255)-(0,0,0)-(255,255,255)-(0,0,0)-(0,0,0)-(255,255,255)",
					"\"Dark\"-(46,44,59)-(255,255,255)-(46,44,59)-(255,255,255)-(46,44,59)-(255,255,255)-(26,25,39)-(26,25,39)"
			};
			CoreFunctions.exportToTextFile(CoreConstants.THEMES_PATH, lines);
		}

		if (errorFile.exists() && !errorFile.isDirectory()) {
			System.out.println("[!]Log file located \t\t [YES]");
		} else {
			String [] lines = {
					"[Action]File structure created."
			};
			CoreFunctions.exportToTextFile(CoreConstants.LOG_OUTPUT_PATH, lines);
		}

		if (scheduleFileFolder.exists() && scheduleFileFolder.isDirectory()) {
			System.out.println("[!]Schedule data located \t [YES]");
		} else {
			File scheduleFolder = new File(CoreConstants.SCHEDULES_PATH);
			try {
				if(scheduleFolder.mkdir()) { 
					String [] lines = {
							"3/14,Sample,2"
					};
					CoreFunctions.exportToTextFile(CoreConstants.SCHEDULES_PATH + "data.txt", lines);
				}
			} catch(Exception e){
				e.printStackTrace();
			} 
		}
	}
	
	public static String getDefaultValueForSettingsKey(String key) {
		Map<String, String> defaultSettings = new HashMap<String, String>();
		defaultSettings.put(CoreConstants.PERIOD_SETTINGS_KEY, "2,3,5,6,7,8,9");
		defaultSettings.put(CoreConstants.CURRENT_FILE_SETTINGS_KEY, "data.txt");
		defaultSettings.put(CoreConstants.SECONDARY_FILE_SETTINGS_KEY, CoreConstants.UNSPECIFIED_SETTING);
		defaultSettings.put(CoreConstants.SECONDARY_START_SETTINGS_KEY, CoreConstants.UNSPECIFIED_SETTING);
		defaultSettings.put(CoreConstants.SECONDARY_END_SETTINGS_KEY, CoreConstants.UNSPECIFIED_SETTING);
		defaultSettings.put(CoreConstants.THEME_SETTINGS_KEY, "Default");
		return defaultSettings.get(key);
	}
}
