
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class ApplicationLogScreen extends JPanel {

	private JTextArea debugPane;
	private JTextArea errorPane;
	private JTextArea actionPane;	
	/**
	 * ApplicationLogScreen()
	 * 
	 * Creates the interface to view the log file within the application
	 */
	public ApplicationLogScreen() {
		super();
		
		// SET layout
		this.setLayout(new BorderLayout());
				
		// SET color
		Color bg = new Color(215, 219, 230);
		this.setBackground(bg);
						
		// SET border
		this.setBorder(new EmptyBorder(14,14,14,14));
				
		//Create the tab pane
		JTabbedPane tabbedPane = new JTabbedPane();
		this.add(tabbedPane, BorderLayout.CENTER);

		//Create textareas for each log category
		this.debugPane = new JTextArea();
		this.errorPane = new JTextArea();
		this.actionPane = new JTextArea();
		
		//Create the tabs and add the text areas to them
		JPanel tab1 = new JPanel();
		JPanel tab2 = new JPanel();
		JPanel tab3 = new JPanel();
		tab1.setLayout(new BorderLayout());
		tab2.setLayout(new BorderLayout());
		tab3.setLayout(new BorderLayout());
		tab1.add(new JScrollPane(this.debugPane), BorderLayout.CENTER);
		tab2.add(new JScrollPane(this.errorPane), BorderLayout.CENTER);
		tab3.add(new JScrollPane(this.actionPane), BorderLayout.CENTER);
		tabbedPane.add("Debug Log", tab1);
		tabbedPane.add("Error Log", tab2);
		tabbedPane.add("Action Log", tab3);
		
		//Sort the log file into the appropriate category tabs
		ArrayList <String> logList = LogOutputManager.shared().getLogEntries();
		for (String entry : logList) {
			if (entry.contains("[ERROR]")) {
				this.errorPane.append("\n" + entry);
			} else if (entry.contains("[ACTION]")) {
				this.actionPane.append("\n" + entry);
			} else if (entry.contains("[DEBUG]")) {
				this.debugPane.append("\n" + entry);
			}
		}
	
	}
}
