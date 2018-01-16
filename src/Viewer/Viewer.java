import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

import com.sun.glass.events.KeyEvent;

public class Viewer extends JFrame {

	private Timer timer; 
	public JPanel masterView;
	private int todayDayOfWeek;

	private String[] periods;

	public JPanel view;
	public HeaderLabel periodColumn;
	public HeaderLabel day1;
	public HeaderLabel day2;
	public HeaderLabel day3;
	public HeaderLabel day4;

	public ArrayList<EntityLabel> periodsList = new ArrayList <EntityLabel> ();
	public ArrayList<ArrayList<EntityLabel>> viewerLists = new ArrayList<ArrayList<EntityLabel>>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Viewer frame = new Viewer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Viewer() throws BadLocationException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {	

		// Set up frame properties
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setBounds(100, 100, 818, 477);
		this.setUndecorated(true);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//System.setProperty("apple.laf.useScreenMenuBar", "true");

		// Make sure all dependencies exist and create them otherwise
		FailsafeController.checkForDependencies();

		// Create top bar
		MenuBar menuBar = new MenuBar();
		setJMenuBar(menuBar);
		ViewerMenuBar vmb = new ViewerMenuBar("BRAIN Display");
		menuBar.add(vmb);
		
		if (!Settings.shared().isTopBarLight()) {
			menuBar.setColor(new Color(46, 44, 59));
			menuBar.setForeground(Color.WHITE);
			vmb.setOpaque(false);
		}
		//vmb.setIcon(new ImageIcon("hamburger.png"));
		//menuBar.setPreferredSize(new Dimension(menuBar.getPreferredSize().width, 50));

		// Set bar actions
		final Viewer v = this;
		vmb.getSettingsButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath();
				vmb.getSettingsButton().setBackground(Color.WHITE);

				SettingsScreen settings = new SettingsScreen(v);
				settings.setVisible(true);
			}
		});

		vmb.getQuitButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});

		// Create the content pane (masterView) that all components will be added to
		this.masterView = new JPanel();
		this.masterView.setLayout(new BorderLayout(0,0));
		this.setContentPane(this.masterView);

		// Set some visual properties on the content pane
		Theme currentTheme = ThemeParser.shared().getCurrentTheme();
		this.masterView.setBackground(currentTheme.generalBG);
		this.masterView.setBorder(new EmptyBorder(12,12,12,12));
		
		// Create the display's table
		this.view = new JPanel();
		this.view.setBackground(currentTheme.generalBG);
		this.masterView.add(this.view, BorderLayout.CENTER);		

		GridBagLayout gblView = new GridBagLayout();
		gblView.columnWidths = new int[]{0,0,0};
		gblView.rowHeights = new int[]{0, 0};
		gblView.columnWeights = new double[]{0.0, 0.0, 1.0};
		gblView.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		this.view.setLayout(gblView);

		// Construct the UI
		this.presentDataOnViewer();

		// Start core timer
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				updateLessonSchedule();
			}
		}, 0, 1*6*1000);

		System.out.println("[!]Application startup \t\t [COMPLETE]");		
	}

	public void presentDataOnViewer() {

		// Clear the screen
		this.viewerLists.clear();
		this.view.removeAll();
		this.view.repaint();

		// Build the lists that will store the labels
		int numLists = 4;
		for (int i = 0; i < numLists; i++) {
			this.viewerLists.add(new ArrayList<EntityLabel>());
		}

		//Get the full lesson schedule
		ArrayList<Lesson> allLessons = LessonFileParser.shared().getLessonSchedule();

		//Configure date system/manager
		DateManager.getManager().constructDateSystem(allLessons);

		//Get the filtered lesson schedule for the next four days
		ArrayList<Lesson> lessons = LessonFileParser.getLessonsFilteredForDates(allLessons, DateManager.getManager().getScheduleDays());

		//Get the new dates
		String today = DateManager.getManager().getDay1Formal();
		String tomorrow = DateManager.getManager().getDay2Formal();
		String afterTomorrow = DateManager.getManager().getDay3Formal();
		String twoDaysFromNow = DateManager.getManager().getDay4Formal();

		//Determine the new day of the week
		this.todayDayOfWeek = DateManager.getManager().getDay();

		//Create the header and side UI
		this.periodColumn = new HeaderLabel("Period", 0, 0, 2, false, false);
		this.day1 = new HeaderLabel("MM dd, yyyy", 1, 0, 10, false, false);
		this.day2 = new HeaderLabel("MM dd, yyyy", 2, 0, 10, false, false);
		this.day3 = new HeaderLabel("MM dd, yyyy", 3, 0, 10, false, false);
		this.day4 = new HeaderLabel("MM dd, yyyy", 4, 0, 10, false, true);

		this.periodColumn.setHorizontalAlignment(SwingConstants.CENTER);

		this.view.add(this.periodColumn, this.periodColumn.getConstraint());
		this.view.add(this.day1, this.day1.getConstraint());
		this.view.add(this.day2, this.day2.getConstraint());
		this.view.add(this.day3, this.day3.getConstraint());
		this.view.add(this.day4, this.day4.getConstraint());

		//Update the dates in the header for the UI
		this.day1.setText(" " + today);
		this.day2.setText(" " + tomorrow);
		this.day3.setText(" " + afterTomorrow);
		this.day4.setText(" " + twoDaysFromNow);

		//Reset all lessons on viewer to blank
		for (int i = 0; i < this.viewerLists.size(); i++) {
			CoreFunctions.resetLabelsForLabelList(this.viewerLists.get(i), true);
		}

		//Get the list of periods (as strings) that will be displayed on the left side
		if (Settings.shared().shouldDynamicallyDisplayPeriods()) {
			this.periods = CoreFunctions.getPeriodsFromLessons(lessons);
			System.out.println("[Status] Periods dynamically selected.");
		} else {
			this.periods = Settings.shared().getPeriods();
			System.out.println("[Status] Periods chosen from Settings file.");
		}
		
		//Generate a list of labels based on the array of periods (strings)
		this.periodsList = CoreFunctions.getLabelListForArray(this.periods, 0, 2, false);
		for (int i = 0; i < this.periodsList.size(); i++)  {
			this.view.add(this.periodsList.get(i), this.periodsList.get(i).getConstraint());
		}

		//Create EntityLabels and set their respective period properties and add them to the viewerLists
		for (int i = 0; i < this.viewerLists.size(); i++) {
			for (int j = 0; j < this.periodsList.size(); j++) {
				boolean isAtBottom = (j == this.periodsList.size() - 1);
				boolean isAtRight = (i == this.viewerLists.size() - 1);
				EntityLabel lbl = new EntityLabel(" ", i + 1, j + 1, 5, 5, isAtBottom, isAtRight);
				lbl.setPeriod(Integer.parseInt(this.periods[j]));
				this.viewerLists.get(i).add(lbl);
			}
		}
		
		// Add all labels in the viewerLists to the screen
		for (int i = 0; i < this.viewerLists.size(); i++) {
			for (int j = 0; j < this.viewerLists.get(i).size(); j++) {
				EntityLabel entLbl = this.viewerLists.get(i).get(j);
				this.view.add(entLbl, entLbl.getConstraint());
			}
		}

		int a [] = {0, 0, 0, 0};
		for (int q = 0; q < lessons.size(); q++) {
			Lesson focused = lessons.get(q);
			String group = focused.getGroup();
			String date = focused.getDate();
			String period = focused.getPeriod();

			int widArrayIdx = -1;
			if (date.equals(DateManager.getManager().getScheduleDay1())) {
				widArrayIdx = 0;
			} else if (date.equals(DateManager.getManager().getScheduleDay2())) {
				widArrayIdx = 1;
			} else if (date.equals(DateManager.getManager().getScheduleDay3())) {
				widArrayIdx = 2;
			} else if (date.equals(DateManager.getManager().getScheduleDay4())) {
				widArrayIdx = 3;
			}

			if (widArrayIdx >= 0) {
				ArrayList<EntityLabel> activeList = this.viewerLists.get(widArrayIdx);
				for (int r = 0; r < activeList.size(); r++) {
					EntityLabel tempLabel = activeList.get(r);
					String tag = "" + tempLabel.getPeriod();
					if (period.equals(tag)){
						tempLabel.setText(" " + group);
						if (tempLabel.getPreferredSize().width > a[3]) {
							a[widArrayIdx] = tempLabel.getPreferredSize().width;
						}
					}
				}
			}
		}
		
		int wid = Math.min(a[0], Math.min(a[1], Math.min(a[2], a[3])));
		for (int i = 0; i < this.viewerLists.size(); i++) {
			for (int j = 0; j < this.viewerLists.get(i).size(); j++) {
				this.viewerLists.get(i).get(j).setPreferredSize(new Dimension(wid, 0));
			}
		}
	}	

	public void updateLessonSchedule() {

		// Find what the day of the week was the last time the viewer updated
		int checkDayOfWeek = DateManager.getManager().getDay();

		// If that is not the same as what the day of the week is now, update viewer
		if (checkDayOfWeek != this.todayDayOfWeek) {
			this.presentDataOnViewer();
			LogOutputManager.shared().logAction("Viewer updated.");
			System.out.println("[Status] Viewer Updated.");

			boolean shouldDeactivate = Settings.shared().shouldSecondaryDeactivate();
			if (shouldDeactivate) {
				Settings.shared().deactivateSecondarySchedule();
				LogOutputManager.shared().logAction("Secondary schedule was deactivated.");
				System.out.println("[Status] Secondary schedule was deactivated.");
			}
		}

		// Simulate the 'shift' key press to prevent the BRAIN from showing screensaver or going to sleep
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void updateTheme(Theme currentTheme) {
		LogOutputManager.shared().logAction("Theme updated.");
		this.masterView.setBackground(currentTheme.generalBG);
		this.view.setBackground(currentTheme.generalBG);
		this.periodColumn.updateTheme(currentTheme);
		this.day1.updateTheme(currentTheme);
		this.day2.updateTheme(currentTheme);
		this.day3.updateTheme(currentTheme);
		this.day4.updateTheme(currentTheme);

		for (EntityLabel entityLabel : this.periodsList) {
			entityLabel.updateTheme(currentTheme);
		}

		for (int i = 0; i < this.viewerLists.size(); i++) {
			for (int j = 0; j < this.viewerLists.get(i).size(); j++) {
				EntityLabel entLbl = this.viewerLists.get(i).get(j);
				entLbl.updateTheme(currentTheme);
			}
		}
	}
}
