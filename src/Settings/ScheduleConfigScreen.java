import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ScheduleConfigScreen extends JPanel {

	private Viewer mainViewerReference;
	private JLabel currentFileLbl;
	
	private JLabel statusLbl;

	public ScheduleConfigScreen(Viewer mainViewer) {
		super();

		// SET reference to main viewer
		this.mainViewerReference = mainViewer;

		// SET layout
		this.setLayout(new BorderLayout());

		// SET color
		Color bg = new Color(215, 219, 230);
		this.setBackground(bg);

		// Create inner panel and set border
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(bg);
		mainPanel.setBorder(new EmptyBorder(14,14,14,14));
		this.add(mainPanel, BorderLayout.CENTER);
		
		// CONSTRUCT status label
		this.statusLbl = new JLabel();
		this.statusLbl.setOpaque(true);
		this.statusLbl.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		this.statusLbl.setFont(new Font (CoreConstants.FONT_TYPE, Font.PLAIN, 12));
		this.statusLbl.setBorder(new EmptyBorder(4, 4, 4, 0));
		this.updateStatus("Awaiting action.");
		this.add(this.statusLbl, BorderLayout.SOUTH);
		
		// CONSTRUCT left side view
		JPanel leftView = new JPanel(new BorderLayout());
		leftView.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);

		// MAKE label for Lessons panel title
		JLabel lessonsLbl = new JLabel("Primary Lessons");
		lessonsLbl.setBorder(new EmptyBorder(16, 10, 4, 0)); // T L B R
		lessonsLbl.setFont(new Font (CoreConstants.FONT_TYPE, Font.PLAIN, 16));
		leftView.add(lessonsLbl, BorderLayout.NORTH);

		// MAKE label for selected primary lesson file
		this.currentFileLbl = new JLabel();
		this.currentFileLbl.setText("Active File: " + Settings.shared().getPrimaryLessonFileName());
		this.currentFileLbl.setBorder(new EmptyBorder(0, 10, 4, 0)); 
		leftView.add(currentFileLbl, BorderLayout.SOUTH);

		// MAKE list view for all of the lesson data files (array has padding built in)
		String[] paddedFiles = CoreFunctions.getFiles(CoreConstants.SCHEDULES_PATH, true);
		final JList <String> list = new JList <String> (paddedFiles);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setSelectionBackground(CoreConstants.ACCENT_COLOR);
		list.setSelectionForeground(Color.BLACK);
		list.setFont(new Font(CoreConstants.FONT_TYPE, Font.PLAIN, 14));
		list.setVisibleRowCount(-1);
		list.setFixedCellHeight(30);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					String fileName = list.getSelectedValue().toString().trim();
					setChosenScheduleDataFile(CoreConstants.SCHEDULES_PATH, fileName);
				}
			}
		});
		list.setSelectedValue(CoreConstants.LIST_PADDING_STRING + Settings.shared().getPrimaryLessonFileName(), true);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setBorder(BorderFactory.createEmptyBorder());
		listScroller.setPreferredSize(new Dimension(200, 80));  /// 1500
		leftView.add(listScroller, BorderLayout.CENTER);

		// ADD leftView to the main view
		mainPanel.add(leftView, BorderLayout.WEST);

		// CONSTRUCT center view
		JPanel centerContainer = new JPanel(new BorderLayout());
		mainPanel.add(centerContainer, BorderLayout.CENTER);

		GridBagLayout gblView = new GridBagLayout();
		gblView.columnWidths = new int[]{0,0,0};
		gblView.rowHeights = new int[]{0, 0, 0, 0};
		gblView.columnWeights = new double[]{0.0, 0.0, 0.0};
		gblView.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};

		JPanel centerView = new JPanel();
		centerView.setBackground(bg);
		centerView.setLayout(gblView);
		centerContainer.add(centerView, BorderLayout.CENTER);

		// Middle Separator
		JPanel separator = new JPanel();
		separator.setBackground(bg);
		separator.setPreferredSize(new Dimension(14, 0));
		centerContainer.add(separator, BorderLayout.WEST);

		// Construct Secondary Schedule View
		String[] files = CoreFunctions.getFiles(CoreConstants.SCHEDULES_PATH, false);
		String primary = Settings.shared().getPrimaryLessonFileName();
		ArrayList<String> filesList = new ArrayList<String>();
		for (String f : files) {
			if (!f.equals(primary)) {
				filesList.add(f);
			}
		}
		files = filesList.toArray(new String[filesList.size()]);
		
		boolean hasSecondaryFile = Settings.shared().hasSecondaryLessonFile();

		ListSelector secondarySchedPicker = new ListSelector("Secondary:", files);
		DatePicker startDatePicker = new DatePicker("Start Date:");
		DatePicker endDatePicker = new DatePicker("End Date:");
		secondarySchedPicker.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		startDatePicker.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		endDatePicker.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		secondarySchedPicker.setActive(!hasSecondaryFile);
		startDatePicker.setActive(!hasSecondaryFile);
		endDatePicker.setActive(!hasSecondaryFile);

		if (hasSecondaryFile) {
			secondarySchedPicker.setSelectedListItem(Settings.shared().getSecondaryLessonFileName());
			startDatePicker.setNumberDate(Settings.shared().getSecondaryStart());
			endDatePicker.setNumberDate(Settings.shared().getSecondaryEnd());
		}

		ToggleButton activate = new ToggleButton("Deactivate", "Activate", hasSecondaryFile);		
		activate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {

				String secondaryFile, secondaryStart, secondaryEnd;
				if (!activate.isToggledOn()) {
					secondaryFile = secondarySchedPicker.getSelectedListItem();
					secondaryStart = startDatePicker.getNumberDate();
					secondaryEnd = endDatePicker.getNumberDate();
				} else {
					secondaryFile = CoreConstants.UNSPECIFIED_SETTING;
					secondaryStart = CoreConstants.UNSPECIFIED_SETTING;
					secondaryEnd = CoreConstants.UNSPECIFIED_SETTING;
				}
				Settings.shared().updateSettingValueForKey(CoreConstants.SECONDARY_FILE_SETTINGS_KEY, secondaryFile);
				Settings.shared().updateSettingValueForKey(CoreConstants.SECONDARY_START_SETTINGS_KEY, secondaryStart);
				Settings.shared().updateSettingValueForKey(CoreConstants.SECONDARY_END_SETTINGS_KEY, secondaryEnd);

				secondarySchedPicker.setActive(activate.isToggledOn());
				startDatePicker.setActive(activate.isToggledOn());
				endDatePicker.setActive(activate.isToggledOn());
				
				LessonFileParser.shared().pullLessonDataFromFile();
				mainViewerReference.presentDataOnViewer();
			}
		});		
		
		SettingsSection secondarySchedView = new SettingsSection("Lesson Schedule Deviation", activate);
		secondarySchedView.addItemToContentView(secondarySchedPicker);
		secondarySchedView.addItemToContentView(startDatePicker);
		secondarySchedView.addItemToContentView(endDatePicker);
		secondarySchedView.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		centerView.add(secondarySchedView, CoreFunctions.createConstraint(0, 0, 1, 0, 0));  // inset 14

		// Construct Add Lesson View
		SingleLessonSection addSection = new SingleLessonSection("Add Lesson", "Add");
		addSection.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		centerView.add(addSection, CoreFunctions.createConstraint(0, 1, 1, 0, 14)); 
		addSection.getActionButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				String g = addSection.getGroupString();
				String p = addSection.getPeriodString();
				String d = addSection.getDateString();
				
				if (g.equals("")) {
					addSection.setGroupHighlighted(true);
					updateStatus("Lesson(s) failed to add.  Fields were left blank.");
				} 
				if (p.equals("")) {
					addSection.setPeriodHighlighted(true);
					updateStatus("Lesson(s) failed to add.  Fields were left blank.");
				} 
				if (!g.equals("") && !p.equals("")) {
					addSection.setGroupHighlighted(false);
					addSection.setPeriodHighlighted(false);
					addLessonToCurrentFile(g, p, d);
					updateStatus("Lesson(s) successfully added.");
				}
			}
		});		
		
		// Construct Remove Lesson View
		SingleLessonSection removeSection = new SingleLessonSection("Remove Lesson", "Remove");
		removeSection.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		centerView.add(removeSection, CoreFunctions.createConstraint(0, 2, 1, 0, 14)); 
		removeSection.getActionButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				String g = removeSection.getGroupString();
				String p = removeSection.getPeriodString();
				String d = removeSection.getDateString();
				
				if (g.equals("")) {
					removeSection.setGroupHighlighted(true);
					updateStatus("Lesson(s) failed to remove.  Fields were left blank.");
				} 
				if (p.equals("")) {
					removeSection.setPeriodHighlighted(true);
					updateStatus("Lesson(s) failed to remove.  Fields were left blank.");
				} 
				
				if (!g.equals("") && !p.equals("")) {
					removeSection.setGroupHighlighted(false);
					removeSection.setPeriodHighlighted(false);
					removeLessonFromCurrentFile(g, p, d);
					updateStatus("Lesson(s) successfully removed.");
				}
			}
		});	
		
		// Construct View Lessons View
		LessonRangeSection viewLessonsSection = new LessonRangeSection("View Lessons", "View");
		viewLessonsSection.setBackground(CoreConstants.SETTINGS_SECTION_COLOR);
		centerView.add(viewLessonsSection, CoreFunctions.createConstraint(0, 3, 1, 0, 14)); 
		viewLessonsSection.getActionButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				viewLessonsInCurrentFile(viewLessonsSection.getGroupString(), viewLessonsSection.getPeriodString(), viewLessonsSection.getStartDateString(), viewLessonsSection.getEndDateString());
			}
		});	
		
	}

	// Change settings text file
	public void setChosenScheduleDataFile(String folderPath, String fileName) {
		System.out.println(folderPath+fileName);
		File dataFile = new File(folderPath + fileName);
		if (dataFile.exists() && !dataFile.isDirectory()) {
			Settings.shared().updateSettingValueForKey(CoreConstants.CURRENT_FILE_SETTINGS_KEY, fileName);
			this.currentFileLbl.setText("Active File: " + Settings.shared().getPrimaryLessonFileName());
			LessonFileParser.shared().pullLessonDataFromFile();
			this.mainViewerReference.presentDataOnViewer();
		}
	}
	
	// Add lesson to current file
	public void addLessonToCurrentFile(String group, String period, String date) {
		ArrayList<String> lessons = LessonFileParser.shared().getLinesFromTextFile(CoreConstants.SCHEDULES_PATH + Settings.shared().getPrimaryLessonFileName());
		String[] periods = period.split(",");
		for (String p : periods) {
			lessons.add(date + "," + group + "," + p);
		}
		CoreFunctions.exportToTextFile(CoreConstants.SCHEDULES_PATH + Settings.shared().getPrimaryLessonFileName(), lessons);
	}
	
	// Remove lesson from current file
	public void removeLessonFromCurrentFile(String group, String period, String date) {
		ArrayList<String> lessons = LessonFileParser.shared().getLinesFromTextFile(CoreConstants.SCHEDULES_PATH + Settings.shared().getPrimaryLessonFileName());
		String[] periods = period.split(",");
		for (String p : periods) {
			lessons.remove(date + "," + group + "," + p);
		}
		CoreFunctions.exportToTextFile(CoreConstants.SCHEDULES_PATH + Settings.shared().getPrimaryLessonFileName(), lessons);
	}
	
	// View lessons in current file
	public void viewLessonsInCurrentFile(String group, String period, String startDate, String endDate) {
		
		String[] periods = period.split(",");
		
		ArrayList<Lesson> lessons = LessonFileParser.shared().getLessonSchedule();
		ArrayList<String> results = new ArrayList<String>();
				
		for (Lesson lsn : lessons) {
			boolean groupMatch = (lsn.getGroup().equalsIgnoreCase(group) || group.equals(""));
			boolean dateMatch = DateManager.dateInRange(lsn.getDate(), startDate, endDate);
			boolean periodMatch = false;
			
			for (String p : periods) {
				if (p.equals(lsn.getPeriod())) {
					periodMatch = true;
					break;
				}
			}
			
			if (groupMatch && dateMatch && periodMatch) {
				results.add("\t\t" + lsn.getGroup() + "\t\t\t\t" + lsn.getDate() + "\t\t\t\t" + lsn.getPeriod());
			}
		}
				
		InformationScreen infoScreen = new InformationScreen(results);
		infoScreen.setVisible(true);
	}
	
	// Update status label
	private void updateStatus(String status) {
		this.statusLbl.setText("[ ! ] " + status);
	}

}
