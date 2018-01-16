import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class ThemeParser {

	//"Theme Name",(heading bg),(heading text color),(content bg),(content color),(outline color),(general background)
	private String [] masterThemes;
	private ArrayList <Theme> masterThemeList;

	private static ThemeParser instance = null;

	protected ThemeParser() { 
		loadThemesFileAndParse();
	}

	public static ThemeParser shared() {
		if(instance == null)  instance = new ThemeParser();
		return instance;
	}

	public void loadThemesFileAndParse() {
		this.loadFile();
		this.parseThemes();
	}

	private void loadFile() {
		//Create settings arraylist
		ArrayList <String> themeDataList = new ArrayList <String> ();

		//create reader to read the data
		BufferedReader reader = null;
		try {
			//attempt to load the file
			reader = new BufferedReader(new FileReader(new File(CoreConstants.THEMES_PATH)));
		} catch (FileNotFoundException e1) {
			//if file is not found
			LogOutputManager.shared().logError("Themes file missing or not set up correctly.");
		}

		//attempt to load file into the settings array
		try {
			String line;
			while((line = reader.readLine()) != null) {
				themeDataList.add(line);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e){
			LogOutputManager.shared().logError("Themes file is corrupted and is either missing data or containing an excessive amount.");
		}

		this.masterThemes = new String[themeDataList.size()];
		this.masterThemes = themeDataList.toArray(this.masterThemes);
	}
	
	private void parseThemes() {
		ArrayList <Theme> themesList = new ArrayList <Theme>();
		for (int a = 0; a < this.masterThemes.length; a++) {
			String currentTheme = this.masterThemes[a];
			StringTokenizer tokenizer = new StringTokenizer(currentTheme,"-");
			String [] themeComponents = new String [tokenizer.countTokens()];
			int counter = 0;
			while (tokenizer.hasMoreTokens()){
				String token = tokenizer.nextToken();
				themeComponents[counter] = token;
				counter++;
			}
			
			String name = themeComponents[0];
			name = name.substring(1, name.length() - 1);
			Color headingBG = CoreFunctions.createColorFromRGBString(themeComponents[1]);
			Color headingText = CoreFunctions.createColorFromRGBString(themeComponents[2]);
			Color contentBG = CoreFunctions.createColorFromRGBString(themeComponents[3]);
			Color contentText = CoreFunctions.createColorFromRGBString(themeComponents[4]);
			Color alternatingBG = CoreFunctions.createColorFromRGBString(themeComponents[5]);
			Color alternatingText = CoreFunctions.createColorFromRGBString(themeComponents[6]);
			Color outline = CoreFunctions.createColorFromRGBString(themeComponents[7]);
			Color generalBG = CoreFunctions.createColorFromRGBString(themeComponents[8]);
			
			Theme tempTheme = new Theme(name, headingBG, headingText, contentBG, contentText, alternatingBG, alternatingText, outline, generalBG);
			themesList.add(tempTheme);
		}
		this.masterThemeList = themesList;
	}
	
	public ArrayList<Theme> getThemes() {
		return this.masterThemeList;
	}
	
	public String[] getThemesArray () {
		ArrayList <Theme> themes = ThemeParser.shared().getThemes();
		String [] themeTitles = new String [themes.size()];
		for (int i = 0; i < themes.size(); i++) {
			Theme t = themes.get(i);
			themeTitles[i] = t.getName();
		}
		return themeTitles;
	}
	
	public Theme getCurrentTheme() {
		for (Theme theme : this.masterThemeList) {
			String name = theme.getName();
			if (name.equals(Settings.shared().getCurrentTheme())) {
				return theme;
			}
		}
		return new Theme ("CoreDefaultTheme", new Color (220,220,220), new Color (0,0,0), new Color (255,255,255), new Color (0,0,0), new Color (215,215,215), new Color (0,0,0), new Color (0,0,0), new Color (255,255,255));
	}
		
}
