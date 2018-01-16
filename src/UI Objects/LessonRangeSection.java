
public class LessonRangeSection extends SettingsSection {

	private CaptionedTextField groupField;
	private CaptionedTextField periodField;
	private DatePicker startDatePicker;
	private DatePicker endDatePicker;
	
	public LessonRangeSection(String title, String buttonTitle) {
		super(title, buttonTitle);
		
		this.groupField = new CaptionedTextField("Group:", 12);
		this.groupField.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		this.addItemToContentView(this.groupField);
				
		this.periodField = new CaptionedTextField("Period:", 2);
		this.periodField.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		this.addItemToContentView(this.periodField);
		
		this.startDatePicker = new DatePicker("Start Date:");
		this.startDatePicker.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		this.addItemToContentView(this.startDatePicker);
		
		this.endDatePicker = new DatePicker("End Date:");
		this.endDatePicker.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		this.addItemToContentView(this.endDatePicker);
	}
	
	public String getGroupString() {
		return this.groupField.getText();
	}
	
	public String getPeriodString() {
		return this.periodField.getText();
	}
	
	public String getStartDateString() {
		return this.startDatePicker.getNumberDate();
	}
	public String getEndDateString() {
		return this.endDatePicker.getNumberDate();
	}

}
