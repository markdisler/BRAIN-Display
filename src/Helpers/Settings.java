import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Settings {
	
	Map<String, String> settingsDict = new HashMap<String, String>();

	private static Settings instance = null;

	protected Settings () { 
		this.loadSettingsFromFileAndParse();
	}

	public static Settings shared () {
		if(instance == null)  instance = new Settings();
		return instance;
	}

	public void loadSettingsFromFileAndParse() {
	
		/*
		 * First and foremost, this application is setup with several settings:
		 *  >Periods to Include 
		 *  >Data file (String)
		 *  >Secondary file (String)
		 *  >Secondary start (String)
		 *  >Secondary end (String)
		 *  >Current theme name (String)
		 */
		
		//Create settings array
		ArrayList<String> settings = new ArrayList<String>();

		//create reader to read the data
		BufferedReader reader = null;
		try {
			//attempt to load the file
			reader = new BufferedReader(new FileReader(new File(CoreConstants.SETTINGS_DATA_PATH)));
		} catch (FileNotFoundException e1) {
			//if file is not found
			LogOutputManager.shared().logError("Settings file missing or not setup correctly.");
		}

		//attempt to load file into the settings array
		try {
			String line;
			while((line = reader.readLine()) != null) {
				settings.add(line);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e){
			LogOutputManager.shared().logError("Settings file is corrupted and is either missing data or containing an excessive amount.");
		}
		
			
		for (int i = 0; i < settings.size(); i++) {
			String line = settings.get(i);
			int eqlSignPos = line.indexOf("=");
			String key = line.substring(0, eqlSignPos).trim();
			String val = line.substring(eqlSignPos + 1).trim();
			this.settingsDict.put(key, val);
		}
		
//		System.out.println("{");
//		for (Map.Entry<String, String> entry : this.settingsDict.entrySet()) {
//		    System.out.println("\t" + entry.getKey() + ":" + entry.getValue());
//		}
//		System.out.println("}");	
	}
	
	public void updateSettingValueForKey(String key, String value) {
		this.settingsDict.put(key, value);
		
		String[] updatedSettings = new String[this.settingsDict.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : this.settingsDict.entrySet()) {
			String line = entry.getKey() + " = " + entry.getValue();
			updatedSettings[i] = line;
			i++;
		}
		CoreFunctions.writeSettingsToSettingsFile(updatedSettings);
	}
	
	//
	//  Resets
	//
	
	public void deactivateSecondarySchedule() {
		if (this.hasSecondaryLessonFile()) {
			String newValue = CoreConstants.UNSPECIFIED_SETTING;
			Settings.shared().updateSettingValueForKey(CoreConstants.SECONDARY_FILE_SETTINGS_KEY, newValue);
			Settings.shared().updateSettingValueForKey(CoreConstants.SECONDARY_START_SETTINGS_KEY, newValue);
			Settings.shared().updateSettingValueForKey(CoreConstants.SECONDARY_END_SETTINGS_KEY, newValue);
		}
	}
	
	//
	//	Helpers
	//
	
	public boolean shouldSecondaryDeactivate() {
		if (this.hasSecondaryLessonFile()) {
			int comparison = DateManager.compareDate(this.getSecondaryEnd(), DateManager.getManager().getToday());
			return (comparison < 0);
		}
		return false;
	}
	
	public boolean ensureRequiredKeysExist(String[] keys) {
		boolean noChanges = true;
		for (String key : keys) {
			if (!this.settingsHasKey(key)) {
				Settings.shared().updateSettingValueForKey(key, FailsafeController.getDefaultValueForSettingsKey(key));
				noChanges = false;
			}
		}
		return noChanges;
	}
	
	private boolean settingsHasKey(String key) {
		return this.settingsDict.containsKey(key);
	}
	
	//
	//	Getters 	
	//

	public boolean shouldDynamicallyDisplayPeriods() {
		if (this.settingsHasKey(CoreConstants.DYNAMIC_PERIOD_KEY)) {
			return this.settingsDict.get(CoreConstants.DYNAMIC_PERIOD_KEY).equals("true");
		}
		return true;
	}
	public String[] getPeriods() {
		return this.settingsDict.get(CoreConstants.PERIOD_SETTINGS_KEY).split(",");
	}
	public String getPrimaryLessonFileName() {
		return this.settingsDict.get(CoreConstants.CURRENT_FILE_SETTINGS_KEY);
	}
	public String getSecondaryLessonFileName() {
		return this.settingsDict.get(CoreConstants.SECONDARY_FILE_SETTINGS_KEY);
	}
	public String getSecondaryStart() {
		String start = this.settingsDict.get(CoreConstants.SECONDARY_START_SETTINGS_KEY);
		if (this.hasSecondaryLessonFile()) {
			return DateManager.getManager().reformatDate(start);
		}
		return "";
	}
	public String getSecondaryEnd() {
		String end = this.settingsDict.get(CoreConstants.SECONDARY_END_SETTINGS_KEY);
		if (this.hasSecondaryLessonFile()) {
			return DateManager.getManager().reformatDate(end);
		}
		return "";
	}
	public String getCurrentTheme() {
		return this.settingsDict.get(CoreConstants.THEME_SETTINGS_KEY);
	}
	public boolean isTopBarLight() {
		if (this.settingsHasKey(CoreConstants.TOP_BAR_LIGHT_KEY)) {
			return this.settingsDict.get(CoreConstants.TOP_BAR_LIGHT_KEY).equals("true");
		}
		return true;
	}
	public boolean hasSecondaryLessonFile() {
		return !(this.getSecondaryLessonFileName().equals(CoreConstants.UNSPECIFIED_SETTING));
	}

}
