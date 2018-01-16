import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Mark
 * The purpose of this object is to get all of the dates necessary for this application.
 * It is necessary for this application to have access to four dates at all times in two different formats.
 * 
 * day1, day2, day3, and day4 are relative dates meaning they are not exactly rooted in today's date
 * However, when specified (in the case of getNow() and getToday()) the method will interact with the date that is ACTUALLY today.
 */
public class DateManager {

	private static DateManager instance = null;
	
	private String today;
	private String day1Formal;
	private String day2Formal;
	private String day3Formal;
	private String day4Formal;
	private String scheduleDay1 = "";
	private String scheduleDay2 = "";
	private String scheduleDay3 = "";
	private String scheduleDay4 = "";

	/**
	 * Default constructor of this object
	 * In some cases, it is unnecessary to ask whether weekends are included
	 */
	protected DateManager () {	/* No body necessary*/	}

	public static DateManager getManager() {
		if(instance == null)  instance = new DateManager();
		return instance;
	}

	/**
	 * Get day1, day2, day3, and day4 in the two formats needed by the application and store in instance variables.
	 * 
	 * Formal - the format showed at the top of each column
	 * Informal - used by the code in this application to determine what to display
	 */
	public void constructDateSystem (ArrayList<Lesson> lessonData) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat informal = new SimpleDateFormat("M/d");
		SimpleDateFormat formal = new SimpleDateFormat("EEE, MMM dd");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

		today = reformatDate(informal.format(currentDate.getTime()));
		String todayYear = yearFormat.format(currentDate.getTime());
		Lesson fakeTodayLesson = new Lesson(today, "Helper", "2");

		this.scheduleDay1 = "";
		this.scheduleDay2 = "";
		this.scheduleDay3 = "";
		this.scheduleDay4 = "";
		
		ArrayList <Integer> inflatedMonths = new ArrayList <Integer> ();
		int daysAcquired = 0;

		for (int i = 0; i < lessonData.size(); i++) {
			Lesson currentLesson = lessonData.get(i);
			String lessonDate = currentLesson.getDate();
			if (currentLesson.compareTo(fakeTodayLesson) == 0) {
				if (!this.scheduleDay1.equals(lessonDate) && !this.scheduleDay2.equals(lessonDate) && !this.scheduleDay3.equals(lessonDate) && !this.scheduleDay4.equals(lessonDate)) {
					daysAcquired++;
					this.scheduleDay1 = lessonDate;
					inflatedMonths.add(Integer.parseInt(this.scheduleDay1.substring(0, today.indexOf("/"))));
				}
			} else if (currentLesson.compareTo(fakeTodayLesson) == 1) {
				if (!this.scheduleDay1.equals(lessonDate) && !this.scheduleDay2.equals(lessonDate) && !this.scheduleDay3.equals(lessonDate) && !this.scheduleDay4.equals(lessonDate)) {
					daysAcquired++;
					if (daysAcquired == 1) {
						this.scheduleDay1 = lessonDate;
						inflatedMonths.add(Integer.parseInt(this.scheduleDay1.substring(0, today.indexOf("/"))));
					} else if (daysAcquired == 2) {
						this.scheduleDay2 = lessonDate;
						inflatedMonths.add(Integer.parseInt(this.scheduleDay2.substring(0, today.indexOf("/"))));
					} else if (daysAcquired == 3) {
						this.scheduleDay3 = lessonDate;
						inflatedMonths.add(Integer.parseInt(this.scheduleDay3.substring(0, today.indexOf("/"))));
					} else if (daysAcquired == 4) {
						this.scheduleDay4 = lessonDate;
						inflatedMonths.add(Integer.parseInt(this.scheduleDay4.substring(0, today.indexOf("/"))));
					}
				}
			}
			if (daysAcquired >= 4) {
				i = lessonData.size();
			}
		}

		int currentMonth = Integer.parseInt(today.substring(0, today.indexOf("/")));
		int inflatedTodayMonth = (currentMonth <= 6) ? currentMonth + 12 : currentMonth;
		int todayYearNumber = Integer.parseInt(todayYear);
		int [] years = {0, 0, 0, 0};

		for (int i = 0; i < inflatedMonths.size(); i++) {
			if (inflatedMonths.get(i) <= 6)
				inflatedMonths.set(i, inflatedMonths.get(i) + 12);
				//System.out.println(inflatedMonths.get(i));
				//System.out.println(inflatedTodayMonth);
			if (inflatedMonths.get(i) == inflatedTodayMonth) {
				years[i] = todayYearNumber;	
			} else if (inflatedMonths.get(i) <= 12 && inflatedTodayMonth <= 12) {
				years[i] = todayYearNumber;
			} else if (inflatedMonths.get(i) > 12 && inflatedTodayMonth > 12) {
				years[i] = todayYearNumber;
			} else if (inflatedMonths.get(i) <= 12 && inflatedTodayMonth > 12) {
				years[i] = todayYearNumber - 1;
			} else if (inflatedMonths.get(i) > 12 && inflatedTodayMonth <= 12) {
				years[i] = todayYearNumber + 1;
			} else {
				years[i] = todayYearNumber;
			}
		}		

		DateFormat basicMDYFormat = new SimpleDateFormat("M/d/yyyy", Locale.ENGLISH);

		try {
			
			this.day1Formal = "";
			this.day2Formal = "";
			this.day3Formal = "";
			this.day4Formal = "";
			
			if (daysAcquired >= 1) {
				Date date1 = basicMDYFormat.parse(this.scheduleDay1 + "/" + years[0]);
				this.day1Formal = formal.format(date1);
			}

			if (daysAcquired >= 2) {
				Date date2 = basicMDYFormat.parse(this.scheduleDay2 + "/" + years[1]);
				this.day2Formal = formal.format(date2);
			}

			if (daysAcquired >= 3) {
				Date date3 = basicMDYFormat.parse(this.scheduleDay3 + "/" + years[2]);
				this.day3Formal = formal.format(date3);
			}

			if (daysAcquired >= 4) {
				Date date4 = basicMDYFormat.parse(this.scheduleDay4 + "/" + years[3]);		
				this.day4Formal = formal.format(date4);
			}

		} catch (Exception e) { }

		//Output to persistent log
		LogOutputManager.shared().logDebug(this.scheduleDay1 + ", " + this.scheduleDay2 + ", " + this.scheduleDay3 + ", " + this.scheduleDay4);

	}
	
	/**
	 * Get the legitimate day TODAY in informal format
	 * @return today's date
	 */
	public String getToday () {
		return this.today;
	}

	/**
	 * Get the date today in month day year format
	 * @return the date today in formal format
	 */
	public String getDay1Formal () {
		return this.day1Formal;
	}

	/**
	 * Get the date tomorrow in month day year format
	 * @return the date tomorrow in formal format
	 */
	public String getDay2Formal () {
		return this.day2Formal;
	}

	/**
	 * Get the date two days from today in month day year format
	 * @return the date two days from now in formal format
	 */
	public String getDay3Formal () {
		return this.day3Formal;
	}

	/**
	 * Get the date three days from today in month day year format
	 * @return the date three days from now in formal format
	 */
	public String getDay4Formal () {
		return this.day4Formal;
	}

	/**
	 * Get today's date in number format
	 * @return today's date in informal format
	 */
	public String getScheduleDay1 () {
		return this.scheduleDay1;
	}

	/**
	 * Get the date of tomorrow in number format
	 * @return the date tomorrow in informal format
	 */
	public String getScheduleDay2 () {
		return this.scheduleDay2;
	}

	/**
	 * Get the date two days from now in number format
	 * @return the date two days from today in informal format
	 */
	public String getScheduleDay3 () {
		return this.scheduleDay3;
	}

	/**
	 * Get the date three days from now in number format
	 * @return the date three days from today in informal format
	 */
	public String getScheduleDay4 () {
		return this.scheduleDay4;
	}

	/**
	 * Get an array of ALL of the days (represented in the informal format)
	 * @return An array of days containing scheduleDay1, scheduleDay2, scheduleDay3, and scheduleDay4
	 */
	public String[] getScheduleDays() {
		return new String[]{this.scheduleDay1, this.scheduleDay2, this.scheduleDay3, this.scheduleDay4};
	}

	/**
	 * Get today's date in formal format
	 * This value is actually today's date and is used when checking if the date has changed in the Viewer
	 * The Viewer compares the value returned to the other date stored in this object.
	 * @return today's date
	 */
	public String getNow() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formal = new SimpleDateFormat("MMM dd, yyyy");
		String now = formal.format(currentDate.getTime());
		return now;
	}
	
	public int getDay() {
		Calendar currentDate = Calendar.getInstance();
		int dayOfWeekToday = currentDate.get(Calendar.DAY_OF_WEEK);
		return dayOfWeekToday;
	}

	/**
	 * The purpose of this method is to take any date string in number format and properly format it with
	 * the correct number of digits. Format = MM/dd ALWAYS 02/09
	 * @param s take the original date string that could be improperly formatted
	 * @return the properly formatted date string
	 */
	public String reformatDate (String s) {
		String date = s;
		int indexOfSlash = date.indexOf("/");
		String month = date.substring(0, indexOfSlash);
		String day = date.substring(indexOfSlash+1);

		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}
		return month + "/" + day;
	}
	
	public static int compareDate(String thisDate, String otherDate) {
		int thisDateMonth = Integer.parseInt(thisDate.substring(0, thisDate.indexOf("/")));
		int thisDateDay = Integer.parseInt(thisDate.substring(thisDate.indexOf("/")+1));

		int otherDateMonth = Integer.parseInt(otherDate.substring(0, thisDate.indexOf("/")));
		int otherDateDay = Integer.parseInt(otherDate.substring(thisDate.indexOf("/")+1));

		if (thisDateMonth <= 6) {
			thisDateMonth += 12;
		}

		if (otherDateMonth <= 6) {
			otherDateMonth += 12;
		}

		if (thisDateMonth < otherDateMonth) {
			return -1;
		} else if (thisDateMonth > otherDateMonth) {
			return 1;
		} else {
			if (thisDateDay < otherDateDay) {
				return -1;
			} else if (thisDateDay > otherDateDay) {
				return 1;
			} else {
				return 0;
			}
		}	
	}
	
	public static boolean dateInRange(String givenDate, String startDate, String endDate) {
		int startVsGiven = DateManager.compareDate(startDate, givenDate);
		int givenVsEnd = DateManager.compareDate(givenDate, endDate);
		return (startVsGiven <= 0 && givenVsEnd <= 0);
	}
}
