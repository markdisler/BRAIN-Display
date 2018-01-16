import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SettingsScreen extends JFrame {	

	private Viewer mainViewerReference;

	public SettingsScreen(Viewer mainViewer) {
		super("Settings");

		//Set frame attributes
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 1150, 700);
		this.setMinimumSize(new Dimension(1150, 700));

		//Set reference to the main viewer
		this.mainViewerReference = mainViewer;
		this.setLayout(new BorderLayout());

		//Configure Settings Screen
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);

		// Create tab bar
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 0, 0);
		fl.setAlignOnBaseline(true);

		JPanel tabBar = new JPanel();		
		tabBar.setLayout(fl);
		TabButton b1 = new TabButton("Schedule Configuration");
		TabButton b2 = new TabButton("Theme Configuration");
		TabButton b3 = new TabButton("App Logs");
		b1.setToggle(true);
		b2.setToggle(false);
		b3.setToggle(false);
		tabBar.add(b1);
		tabBar.add(b2);
		tabBar.add(b3);
		tabBar.setPreferredSize(new Dimension(0, 30));  
		panel.add(tabBar, BorderLayout.NORTH);

		// Create tabs
		JPanel tab1 = new JPanel(new BorderLayout());
		JPanel tab2 = new JPanel(new BorderLayout());
		JPanel tab3 = new JPanel(new BorderLayout());
		panel.add(tab1, BorderLayout.CENTER);

		// Create the contents of tab 1
		ScheduleConfigScreen scs = new ScheduleConfigScreen(mainViewer);
		tab1.add(scs, BorderLayout.CENTER);

		//Create tab 2 content
		ThemeConfigScreen tcs = new ThemeConfigScreen(mainViewer);
		tab2.add(tcs, BorderLayout.CENTER);

		// Create the contents of tab 3
		tab3.add(new ApplicationLogScreen(), BorderLayout.CENTER);

		// Create actions for tab bar
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if (b1.isToggled()) {
					panel.remove(tab1);
				} else if (b2.isToggled()) {
					panel.remove(tab2);
				} else if (b3.isToggled()) {
					panel.remove(tab3);
				}
				panel.add(tab1, BorderLayout.CENTER);
				panel.revalidate();
				panel.repaint();
				b1.setToggle(true);
				b2.setToggle(false);
				b3.setToggle(false);
			}
		});

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if (b1.isToggled()) {
					panel.remove(tab1);
				} else if (b2.isToggled()) {
					panel.remove(tab2);
				} else if (b3.isToggled()) {
					panel.remove(tab3);
				}
				panel.add(tab2, BorderLayout.CENTER);
				panel.revalidate();
				panel.repaint();
				b1.setToggle(false);
				b2.setToggle(true);
				b3.setToggle(false);
			}
		});

		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if (b1.isToggled()) {
					panel.remove(tab1);
				} else if (b2.isToggled()) {
					panel.remove(tab2);
				} else if (b3.isToggled()) {
					panel.remove(tab3);
				}
				panel.add(tab3, BorderLayout.CENTER);
				panel.revalidate();
				panel.repaint();
				b1.setToggle(false);
				b2.setToggle(false);
				b3.setToggle(true);
			}
		});
	}
}