import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DatePicker extends JPanel {
	
	private JComboBox<String> monthBox;
	private JComboBox<String> dayBox;
	//private JComboBox<String> yearBox;
	
	public DatePicker(String caption) {
		
		// CONFIGURE panel
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// CREATE options
		//Calendar currentDate = Calendar.getInstance();
		//SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		//String todayYearStr = yearFormat.format(currentDate.getTime());
		//int year = Integer.parseInt(todayYearStr);
		
		String [] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		String [] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", 
						"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
//		String [] years = new String[5];
//		for (int i = 0; i < 5; i++) {
//			years[i] = "" + (year + i);
//		}
		
		// CREATE caption
		JLabel lbl = new JLabel(caption);

		// CREATE UI Boxes
		this.monthBox = new JComboBox<String>(months);
		this.dayBox = new JComboBox<String>(days);
		//this.yearBox = new JComboBox<String>(years);
		
		// SET font
		Font f = new Font(CoreConstants.FONT_TYPE, Font.PLAIN, 14);
		lbl.setFont(f);
		this.monthBox.setFont(f);
		this.dayBox.setFont(f);
		//this.yearBox.setFont(f);
		
		// ADD components to screen
		this.add(lbl);
		this.add(this.monthBox);
		this.add(this.dayBox);
		//this.add(this.yearBox);
	}
	
	public void setActive(boolean active) {
		this.monthBox.setEnabled(active);
		this.dayBox.setEnabled(active);
		//this.yearBox.setEnabled(active);
	}
	
	public String getNumberDate() {
		String month = "" + (this.monthBox.getSelectedIndex() + 1);
		String day = this.dayBox.getSelectedItem().toString();
//		if (withYear) {
//			String year = this.yearBox.getSelectedItem().toString();
//			return month + "/" + day + "/" + year;
//		}
		
		String fullDate = month + "/" + day;
		return DateManager.getManager().reformatDate(fullDate);
	}
	
	public void setNumberDate(String date) {
		// Make sure date has proper format
		date = DateManager.getManager().reformatDate(date);	
		
		// Separate date components for month and date
		int m = Integer.parseInt(date.substring(0, date.indexOf("/")));
		String d = date.substring(date.indexOf("/") + 1);
		
		// Set the selection of both combo boxes
		this.monthBox.setSelectedIndex(m - 1);
		this.dayBox.setSelectedItem(d);
	}
}
