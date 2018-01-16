/**
 * 
 * @author Mark
 * The purpose of this object is to hold all of the information necessary for a lesson.
 * A lesson has a date, group and period in which it is scheduled.
 * 
 * This object has NO default constructor.
 */
public class Lesson implements Comparable<Lesson>{

	private String date;
	private String group;
	private String period;

	/**
	 * The constructor of this object takes three parameters:
	 * @param d = the date of the lesson
	 * @param g = the group or instrument that will be in this lesson
	 * @param p = the period this lesson will be held
	 * 
	 * There is no default constructor because a lesson MUST have these three pieces of information.
	 */
	public Lesson (String d, String g, String p) {
		this.date = d;
		this.group = g;
		this.period = p;
	}

	/**
	 * This method returns the period.
	 * @return period
	 */
	public String getPeriod () {
		return this.period;
	}

	/**
	 * This method returns the group.
	 * @return group
	 */
	public String getGroup () {
		return this.group;
	}

	/**
	 * This method returns the date.
	 * @return date
	 */
	public String getDate() {
		return this.date;
	}

	public String toString() {
		return "Date: " + this.date + " | Group: " + this.group + " | Period: " + this.period;
	}

	@Override
	public int compareTo(Lesson other) {
		return DateManager.compareDate(this.date, other.date);
	}
}
