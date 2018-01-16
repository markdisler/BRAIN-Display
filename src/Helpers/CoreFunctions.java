import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingConstants;
public class CoreFunctions {

//
//	Viewer Helpers
//
	
	public static String[] getPeriodsFromLessons(ArrayList<Lesson> lessons) {
		Set<String> periodSet = new HashSet<String>();
		
		for (Lesson lsn : lessons) {
			periodSet.add(lsn.getPeriod());
		}
		
		ArrayList<String> lessonPeriods = new ArrayList<String>(periodSet);
		String[] periods = new String[lessonPeriods.size()];
		for (int i = 0; i < lessonPeriods.size(); i++) {
			periods[i] = lessonPeriods.get(i);
		}
		return periods;
	}
	
	public static ArrayList <EntityLabel> getLabelListForArray(String [] a, int col, int sep, boolean isRowEnd) {
		ArrayList <EntityLabel> list = new ArrayList <EntityLabel> ();
		for (int i = 0; i < a.length; i++) {
			String s = a[i];
			boolean isLast = i == a.length - 1 ? true : false;
			EntityLabel label = new EntityLabel (s, col, i+1, sep, sep, isLast, isRowEnd);
			label.setOpaque(true);
			Theme currentTheme = ThemeParser.shared().getCurrentTheme();
			label.setBackground(currentTheme.headingBG);
			label.setForeground(currentTheme.headingText);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			list.add(label);
		}
		return list;
	}
	
	public static GridBagConstraints createConstraint(int x, int y, int wx, int wy, int topInset) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(topInset,0,0,0);
		c.gridx = x;
		c.gridy = y;
		c.weightx = wx;
		c.weighty = wy;
		return c;
	}

	public static void resetLabelsForLabelList (ArrayList <EntityLabel> labelList, boolean resetColor) {
		for (EntityLabel label : labelList) {
			label.setText(" ");
		}
	}
	
//
//	File Interactions
//
	public static void exportToTextFile(String path, String[] lines) {
		try {
			PrintWriter writer = new PrintWriter(path);
			for (String s : lines) {
				writer.println(s);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void exportToTextFile(String path, ArrayList<String> lines) {
		try {
			PrintWriter writer = new PrintWriter(path);
			for (String s : lines) {
				writer.println(s);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeLessonsToFile(String lessonFile, ArrayList<String> lessons) {
		CoreFunctions.exportToTextFile(lessonFile, lessons);
	}
	
	public static void writeSettingsToSettingsFile(String [] settings) {
		CoreFunctions.exportToTextFile(CoreConstants.SETTINGS_DATA_PATH, settings);
	}
	
	public static void writeThemeListToThemeFile(ArrayList<Theme>themes) {
		ArrayList <String> themeEntries = new ArrayList <String> ();
		for (Theme theme : themes) {
			themeEntries.add(theme.getEntryNotation());
		}
		try {
			PrintWriter writer = new PrintWriter (CoreConstants.THEMES_PATH);
			for (String s : themeEntries) {
				writer.println(s);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

	public static String[] getFiles (String folderPath, boolean applyVisualPadding) {
		ArrayList <String> fileNamesList = new ArrayList <String> ();
		File folder = new File(folderPath);
		File [] listOfFiles = folder.listFiles();
		String pref = (applyVisualPadding) ? CoreConstants.LIST_PADDING_STRING : "";

		for (File file : listOfFiles) {
			if (file.isFile()) {
				fileNamesList.add(pref + file.getName());
			}
		}
		return fileNamesList.toArray(new String[fileNamesList.size()]);
	}

//
//	Colors
//
	public static Color createColorFromRGBString(String color) {
		if (color.length() < 7) return Color.black;
		String withoutParen = color.substring(1, color.length()-1);
		int posOfFirstComma = withoutParen.indexOf(",");
		int posOfSecondComma = withoutParen.indexOf(",", posOfFirstComma+1);
		String rStr = withoutParen.substring(0, posOfFirstComma);
		String gStr = withoutParen.substring(posOfFirstComma+1, posOfSecondComma);
		String bStr = withoutParen.substring(posOfSecondComma+1);
		int r = Integer.parseInt(rStr);
		int g = Integer.parseInt(gStr);
		int b = Integer.parseInt(bStr);
		Color theColor = new Color(r,g,b);
		return theColor;
	}
	
	/**
	 * darkenColor(Color c)
	 * @param c = Original color that we wish to darken
	 * @return Darker color
	 */
	public static Color darkenColor(Color c) {
		int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
		int darkenValue = 40;
		r = Math.max(r - darkenValue, 0);
		g = Math.max(g - darkenValue, 0);
		b = Math.max(b - darkenValue, 0);
		return new Color (r, g, b);	
	}
}
