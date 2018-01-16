import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SingleLessonSection extends SettingsSection {

	private CaptionedTextField groupField;
	private CaptionedTextField periodField;
	private DatePicker datePicker;
	
	public SingleLessonSection(String title, String buttonTitle) {
		super(title, buttonTitle);
		
		this.groupField = new CaptionedTextField("Group:", 12);
		this.groupField.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		this.addItemToContentView(this.groupField);
				
		this.periodField = new CaptionedTextField("Period:", 2);
		this.periodField.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		this.addItemToContentView(this.periodField);
		
		this.datePicker = new DatePicker("Date:");
		this.datePicker.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		this.addItemToContentView(this.datePicker);
	}
	
	public String getGroupString() {
		return this.groupField.getText();
	}
	
	public String getPeriodString() {
		return this.periodField.getText();
	}
	
	public String getDateString() {
		return this.datePicker.getNumberDate();
	}
	
	public void setGroupHighlighted(boolean highlighted) {
		this.groupField.setHighlighted(highlighted);	
	}
	
	public void setPeriodHighlighted(boolean highlighted) {
		this.periodField.setHighlighted(highlighted);
	}

}
