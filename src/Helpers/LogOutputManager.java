import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 
 * @author Mark
 * The purpose of this class is to output errors to a text file to keep for records.
 */

public class LogOutputManager {

	private static LogOutputManager instance = null;
	private PrintWriter out;
	private Calendar currentDate = Calendar.getInstance();
	private SimpleDateFormat formal = new SimpleDateFormat("MMM dd, yyyy | HH:mm:ss");

	protected LogOutputManager () { /* No Body Necessary */	}

	public static LogOutputManager shared () {
		if(instance == null)  instance = new LogOutputManager();
		return instance;
	}

	/**
	 * This method takes a string and outputs it to the file.
	 * The file is a list of all of the errors.
	 * It also is necessary to get the time and date to list when the error occurred. 
	 * @param s = string value for the log message
	 */
	private void outputLog (String s) {
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(CoreConstants.LOG_OUTPUT_PATH, true)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String now = formal.format(currentDate.getTime());
		out.println("["+ now +"] " + s );
		out.close();
	} 
	
	public ArrayList<String> getLogEntries() {
		ArrayList <String> logList = new ArrayList <String> ();

		//create reader to read the data
		BufferedReader reader = null;
		try {
			//attempt to load the file
			reader = new BufferedReader(new FileReader(new File(CoreConstants.LOG_OUTPUT_PATH)));
		} catch (FileNotFoundException e1) {}

		//attempt to load file into the settings array
		try {
			String line;
			while((line = reader.readLine()) != null) {
				logList.add(line);
			}
		} catch (IOException e1) {
		} catch (ArrayIndexOutOfBoundsException e){
		}
		return logList;
	}
	
	public void logDebug(String s) {
		this.outputLog("[DEBUG] " + s);
	}
	
	public void logAction(String s) {
		this.outputLog("[ACTION] " + s);
	}
	
	public void logError(String s) {
		this.outputLog("[ERROR] " + s);
	}
}
