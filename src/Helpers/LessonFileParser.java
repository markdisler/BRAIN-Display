import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * @author Mark
 * This class reads in the data file of lessons that are scheduled and organizes it into a system that 
 * the application can read.  It is necessary to create a BufferReader object in order to get the data from
 * the specified path.  It begins by importing the data from the file into an arraylist and further breaking it
 * into a two dimensional array.  It further takes that new array and puts it back into a different arraylist but
 * this time of Lesson objects.
 */
public class LessonFileParser {

	private ArrayList<Lesson> scheduledLessons;

	private static LessonFileParser instance = null;

	public static LessonFileParser shared () {
		if(instance == null)  instance = new LessonFileParser();
		return instance;
	}

	protected LessonFileParser () {
		pullLessonDataFromFile();
	}

	/**
	 * It parses the data from the file into an array list.
	 * From that array list, it is further parsed into an array.
	 * From that array it is organized cleanly into an array list of Lesson objects.
	 */
	public void pullLessonDataFromFile () {

		this.scheduledLessons = new ArrayList<Lesson>();

		ArrayList<Lesson> primaryLessons = lessonsFromFile(Settings.shared().getPrimaryLessonFileName(), true);
		this.scheduledLessons.addAll(primaryLessons);

		if (Settings.shared().hasSecondaryLessonFile()) {
			ArrayList<Lesson> secondaryLessons = lessonsFromFile(Settings.shared().getSecondaryLessonFileName(), false);
			this.scheduledLessons.addAll(secondaryLessons);
		}

		Collections.sort(this.scheduledLessons);		

		for (Lesson l : this.scheduledLessons) {
			System.out.println(l);
		}
	}

	private ArrayList<Lesson> lessonsFromFile(String fileName, boolean isPrimary) {
		
		String file = CoreConstants.SCHEDULES_PATH + fileName;
		ArrayList<String> lines = this.getLinesFromTextFile(file);
		
		if (lines.size() == 0) {
			return new ArrayList<Lesson>();
		}

		String[] linesArray = lines.toArray(new String[lines.size()]);
		String[][] data = new String [lines.size()][3];
	
		int counter = 0;
		for (int i = 0; i < linesArray.length; i++) {
			StringTokenizer st = new StringTokenizer(linesArray[counter], ",");
			int j = 0;
			while (st.hasMoreElements()) {
				String token = (String) st.nextElement();
				data[i] [j] = token;
				j++;
			}
			counter++;
		}

		boolean hasSecondFile = Settings.shared().hasSecondaryLessonFile();
		ArrayList<Lesson> lessons = new ArrayList<Lesson>();
		for (int a = 0; a < data.length; a++) {	
			String d = DateManager.getManager().reformatDate(data[a][0]);

			if ((isPrimary && !hasSecondFile) ||
					(isPrimary && hasSecondFile && !DateManager.dateInRange(d, Settings.shared().getSecondaryStart(), Settings.shared().getSecondaryEnd())) ||
					(!isPrimary && DateManager.dateInRange(d, Settings.shared().getSecondaryStart(), Settings.shared().getSecondaryEnd()))) {
				String g = data[a][1];
				String p = data[a][2];
				g = shortenInstrumentName(g);
				Lesson tempLesson = new Lesson(d, g, p);
				lessons.add(tempLesson);
			}
		}
		return lessons;

	}

	public ArrayList<String> getLinesFromTextFile(String path) {

		ArrayList<String> lines = new ArrayList<String>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String str = null;
			while((str = in.readLine()) != null){
				if (!str.startsWith("//")) {
					lines.add(str);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			LogOutputManager.shared().logError("Lesson schedule data file not found.");
		} catch (IOException e) {
			LogOutputManager.shared().logError("Unable to read lesson schedule.");
		}
		return lines;
	}

	public String shortenInstrumentName(String s) {
				
		if (s.contains("Trombone")) {
			s = s.replace("Trombone", "Trbn");
		} 
		if (s.contains("Trumpet")) {
			s = s.replace("Trumpet", "Trpt");
		} 
		if (s.contains("Saxophone")) {
			s = s.replace("Saxophone", "Sax");
		} 
		if (s.contains("Percussion")) {
			s = s.replace("Percussion", "Perc.");
		} 
		if (s.contains("Bass Clarinet")) {
			s = s.replace("Bass Clarinet", "B. Cl.");
		}
		if (s.contains("Clarinet")) {
			s = s.replace("Clarinet", "Clar");
		} 
		if (s.contains("French")) {
			s = s.replace("French", "F.");
		} 
		if (s.contains("Bassoon")) {
			s = s.replace("Bassoon", "Bsn.");
		}
		if (s.contains("Baritone")) {
			s = s.replace("Baritone", "Bari");
		}
		if (s.contains("Euphonium")) {
			s = s.replace("Euphonium", "Euph");
		}
		return s;
	}

	/**
	 * This method returns the array list of lessons needed in the Viewer.
	 * @return scheduledLessons
	 */
	public ArrayList<Lesson> getLessonSchedule () {
		return this.scheduledLessons;
	}
	
	/**
	 * This method will provide all the lessons that occur on the given set of dates.
	 * @param lessons A set of lessons.
	 * @param dates A set of dates (represented as strings) that will serve as the filter for the lessons.
	 * @return A filtered subset of lessons that occur on one of the dates passed to this method.
	 */
	public static ArrayList<Lesson> getLessonsFilteredForDates(ArrayList<Lesson> lessons, String[] dates) {
		ArrayList<Lesson> filtered = new ArrayList<Lesson>();
		for (Lesson lsn : lessons) {
			String d = lsn.getDate();
			for (String date : dates) {
				if (d.equals(date)) {
					filtered.add(lsn);
					break;
				}
			}
		}
		return filtered;
	}
}
