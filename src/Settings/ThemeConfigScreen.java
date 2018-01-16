import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ThemeConfigScreen extends JPanel {

	private Viewer mainViewerReference;
	
	private JLabel currentThemeLbl;
	private JTextField nameField;
	private final JList <String> themeList;
	private DefaultListModel <String> themeListModel;
	private SimpleColorPicker picker1, picker2, picker3, picker4, picker5, picker6, picker7, picker8;

	public ThemeConfigScreen(Viewer mainViewer) {
		super();
		
		// SET reference to main viewer
		this.mainViewerReference = mainViewer;
		
		// SET layout
		this.setLayout(new BorderLayout());

		// SET color
		Color bg = new Color(215, 219, 230);
		this.setBackground(bg);
		
		// DEFINE view color
		Color viewColor = Color.WHITE;
		
		// SET border
		this.setBorder(new EmptyBorder(14,14,14,14));
		
		JPanel selectThemeList = new JPanel(new BorderLayout());
		selectThemeList.setBackground(viewColor);

		this.themeListModel = new DefaultListModel<String>();
		this.populateThemeList();

		this.themeList = new JList<String>(this.themeListModel);
		this.themeList.setSelectionBackground(CoreConstants.ACCENT_COLOR);
		this.themeList.setSelectionForeground(Color.BLACK);
		this.themeList.setFont(new Font(CoreConstants.FONT_TYPE, Font.PLAIN, 14));
		this.themeList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.themeList.setVisibleRowCount(-1);
		this.themeList.setFixedCellHeight(30);
		this.themeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					String themeName = themeList.getSelectedValue().toString();
					setChosenTheme(themeName);
					loadThemeIntoThemeDesigner();
				}
			}
		});
		this.themeList.setSelectedValue(Settings.shared().getCurrentTheme(), true);
		JScrollPane themeListScroller = new JScrollPane(themeList);
		themeListScroller.setBorder(BorderFactory.createEmptyBorder());
		themeListScroller.setPreferredSize(new Dimension(200, 80));

		this.currentThemeLbl = new JLabel();
		this.currentThemeLbl.setText("Active: " + ThemeParser.shared().getCurrentTheme().name);
		this.currentThemeLbl.setBorder(new EmptyBorder(0, 10, 4, 0)); 
		
		JButton addThemeBtn = new JButton("New +");
		addThemeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				createNewTheme();
			}
		});
		selectThemeList.add(themeListScroller, BorderLayout.CENTER);
		selectThemeList.add(addThemeBtn, BorderLayout.NORTH);
		selectThemeList.add(this.currentThemeLbl, BorderLayout.SOUTH);

		
		JPanel centerContainer = new JPanel(new BorderLayout());
		
		// Theme Editor Panel
		JScrollPane editorPanel = createInputPanel(viewColor);
		centerContainer.add(editorPanel, BorderLayout.CENTER);
		
		// Middle Separator
		JPanel separator = new JPanel();
		separator.setBackground(bg);
		separator.setPreferredSize(new Dimension(14, 0));
		centerContainer.add(separator, BorderLayout.WEST);
		
		this.add(selectThemeList, BorderLayout.WEST);
		this.add(centerContainer, BorderLayout.CENTER);
				
		this.loadThemeIntoThemeDesigner();
	}

	private void loadThemeIntoThemeDesigner() {
		Theme theme = ThemeParser.shared().getCurrentTheme();
		this.nameField.setText(theme.name);
		this.picker1.setColor(theme.headingBG);
		this.picker2.setColor(theme.headingText);
		this.picker3.setColor(theme.contentBG);
		this.picker4.setColor(theme.contentText);
		this.picker5.setColor(theme.alternatingBG);
		this.picker6.setColor(theme.alternatingText);
		this.picker7.setColor(theme.outline);
		this.picker8.setColor(theme.generalBG);
	}

	private JScrollPane createInputPanel(Color viewColor) {
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(viewColor);
		
		GridBagLayout gbl_view = new GridBagLayout();
		gbl_view.columnWidths = new int[]{0,0,0};
		gbl_view.rowHeights = new int[]{0, 0};
		gbl_view.columnWeights = new double[]{0.0, 0.0, 1.0};
		mainPanel.setLayout(gbl_view);

		//Create the theme creator

		JPanel actionBar = new JPanel();
		actionBar.setBackground(viewColor);
		actionBar.setLayout(new BorderLayout());

		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				saveCurrentTheme();
			}
		});

		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				confirmDelete();
			}
		});

		actionBar.add(saveBtn, BorderLayout.WEST);
		actionBar.add(deleteBtn, BorderLayout.EAST);

		JLabel nameLabel = this.makeTitleLabel("Theme Name");
		this.nameField = new JTextField(20);
		this.nameField.setBounds(0, 0, 200, 30);

		ThemeSectionLabel headingBackgroundLbl = new ThemeSectionLabel("Heading Background");	
		this.picker1 = new SimpleColorPicker(Color.white, headingBackgroundLbl.colorPreview);

		ThemeSectionLabel headingTextColorLbl = new ThemeSectionLabel("Heading Text Color");
		this.picker2 = new SimpleColorPicker(Color.white, headingTextColorLbl.colorPreview);

		ThemeSectionLabel contentBackgroundLbl = new ThemeSectionLabel("Content Background");
		this.picker3 = new SimpleColorPicker(Color.white, contentBackgroundLbl.colorPreview);

		ThemeSectionLabel contentColorLbl = new ThemeSectionLabel("Content Text Color");
		this.picker4 = new SimpleColorPicker(Color.white, contentColorLbl.colorPreview);

		ThemeSectionLabel alternatingBGLbl = new ThemeSectionLabel("Alternating Content Background");
		this.picker5 = new SimpleColorPicker(Color.white, alternatingBGLbl.colorPreview);

		ThemeSectionLabel alternatingTextColorLbl = new ThemeSectionLabel("Alternating Content Text Color");
		this.picker6 = new SimpleColorPicker(Color.white, alternatingTextColorLbl.colorPreview);

		ThemeSectionLabel outlineColorLbl = new ThemeSectionLabel("Outline Color");
		this.picker7 = new SimpleColorPicker(Color.white, outlineColorLbl.colorPreview);

		ThemeSectionLabel generalBackgroundLbl = new ThemeSectionLabel("General Background");
		this.picker8 = new SimpleColorPicker(Color.white, generalBackgroundLbl.colorPreview);
		
		//Add the components to the panel
		mainPanel.add(actionBar, this.createConstraint(0, 0));
		mainPanel.add(nameLabel, this.createConstraint(0, 1));
		mainPanel.add(nameField, this.createConstraint(0, 2));
		mainPanel.add(headingBackgroundLbl, this.createConstraint(0, 3));
		mainPanel.add(this.picker1, this.createConstraint(0, 4));
		mainPanel.add(headingTextColorLbl, this.createConstraint(0,5));
		mainPanel.add(this.picker2, this.createConstraint(0, 6));
		mainPanel.add(contentBackgroundLbl, this.createConstraint(0,7));
		mainPanel.add(this.picker3, this.createConstraint(0,8));
		mainPanel.add(contentColorLbl, this.createConstraint(0,9));
		mainPanel.add(this.picker4, this.createConstraint(0,10));
		mainPanel.add(alternatingBGLbl, this.createConstraint(0,11));
		mainPanel.add(this.picker5, this.createConstraint(0,12));
		mainPanel.add(alternatingTextColorLbl, this.createConstraint(0,13));
		mainPanel.add(this.picker6, this.createConstraint(0,14));
		mainPanel.add(outlineColorLbl, this.createConstraint(0,15));
		mainPanel.add(this.picker7, this.createConstraint(0,16));
		mainPanel.add(generalBackgroundLbl, this.createConstraint(0,17));
		mainPanel.add(this.picker8, this.createConstraint(0,18));
		
		JScrollPane scroller = new JScrollPane(mainPanel);
		scroller.setBorder(BorderFactory.createEmptyBorder());
		return scroller;
	}

	private JLabel makeTitleLabel(String s) {
		JLabel label = new JLabel(s);
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));	
		label.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.black));
		return label;
	}

	private GridBagConstraints createConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,25,0,0);
		c.gridx = x;
		c.gridy = y;
		c.weightx = 0;
		return c;
	}

	//Change theme file
	public void setChosenTheme(String themeName) {
		Settings.shared().updateSettingValueForKey(CoreConstants.THEME_SETTINGS_KEY, themeName);

		//Update Stored Settings
		Settings.shared().loadSettingsFromFileAndParse();
		this.currentThemeLbl.setText("Active: " + ThemeParser.shared().getCurrentTheme().name);
		this.mainViewerReference.updateTheme(ThemeParser.shared().getCurrentTheme());
	}

	//Create, Delete, Modify Theme File

	private void createNewTheme() {
		int counter = 0;
		String rootThemeName = "New Theme";
		String newThemeName = rootThemeName;

		while (this.isUsedName(newThemeName)) {
			counter++;
			newThemeName = rootThemeName + " " + counter;
		}

		Theme newTheme = new Theme (newThemeName, new Color (220,220,220), new Color (0,0,0), new Color (255,255,255), new Color (0,0,0), new Color (215,215,215), new Color (0,0,0), new Color (120,120,120), new Color (0,0,0));
		ArrayList <Theme> allThemes = ThemeParser.shared().getThemes();
		allThemes.add(newTheme);

		CoreFunctions.writeThemeListToThemeFile(allThemes);
		ThemeParser.shared().loadThemesFileAndParse();
		this.populateThemeList();

		Theme newestAdditionToList = ThemeParser.shared().getThemes().get(ThemeParser.shared().getThemes().size()-1);
		this.setChosenTheme(newestAdditionToList.name);
		this.themeList.setSelectedValue(Settings.shared().getCurrentTheme(), true);
		this.loadThemeIntoThemeDesigner();
	}

	private boolean isUsedName(String n) {
		for (String s : ThemeParser.shared().getThemesArray()) {
			if (n.equals(s)) {
				return true;
			}
		}
		return false;
	}

	private boolean isUsedByOtherTheme(String n) {
		Theme currentTheme = ThemeParser.shared().getCurrentTheme();
		for (Theme t : ThemeParser.shared().getThemes()) {
			if (t != currentTheme && t.name.equals(n)) {
				return true;
			}
		}
		return false;
	}

	private void populateThemeList() {
		if (this.themeListModel != null) {
			this.themeListModel.removeAllElements();
			for (String s : ThemeParser.shared().getThemesArray()) {
				this.themeListModel.addElement(s);
			}
		}
	}

	private void saveCurrentTheme() {
		String themeName = this.nameField.getText();
		Color headingBG = picker1.getColor();
		Color headingText = picker2.getColor();
		Color contentBG = picker3.getColor();
		Color contentText = picker4.getColor();
		Color alternatingBG = picker5.getColor();
		Color alternatingText = picker6.getColor();
		Color outline = picker7.getColor();
		Color generalBG = picker8.getColor();

		boolean canBeUsed = !this.isUsedByOtherTheme(themeName);
		if (!canBeUsed) {
			int dialogResult = JOptionPane.showConfirmDialog(this, "Please pick a unique theme name.", "Save Error", JOptionPane.PLAIN_MESSAGE);
			if (dialogResult == 0) {}
		} else {
			Theme currentTheme = ThemeParser.shared().getCurrentTheme();
			Theme replacementTheme = new Theme(themeName, headingBG, headingText, contentBG, contentText, alternatingBG, alternatingText, outline, generalBG);
			ArrayList <Theme> allThemes = ThemeParser.shared().getThemes();
			int indexOfCurrentTheme = allThemes.indexOf(currentTheme);
			allThemes.set(indexOfCurrentTheme, replacementTheme);

			CoreFunctions.writeThemeListToThemeFile(allThemes);
			ThemeParser.shared().loadThemesFileAndParse();
			this.loadThemeIntoThemeDesigner();
			this.mainViewerReference.updateTheme(ThemeParser.shared().getCurrentTheme());
		}
	}

	private void confirmDelete() {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(this, "Confirm theme delete?", "Action Confirmation", dialogButton, JOptionPane.PLAIN_MESSAGE);
		if (dialogResult == 0) {
			ArrayList <Theme> allThemes = ThemeParser.shared().getThemes();
			if (allThemes.size() > 1) {
				Theme currentTheme = ThemeParser.shared().getCurrentTheme();
				allThemes.remove(currentTheme);
				CoreFunctions.writeThemeListToThemeFile(allThemes);
				ThemeParser.shared().loadThemesFileAndParse();
				this.populateThemeList();

				Theme firstInList = ThemeParser.shared().getThemes().get(0);
				this.setChosenTheme(firstInList.name);
				this.themeList.setSelectedValue(Settings.shared().getCurrentTheme(), true);
				this.loadThemeIntoThemeDesigner();
			}
		} 
	}
}
