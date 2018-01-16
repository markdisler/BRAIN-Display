import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InformationScreen extends JFrame {	

	private String[] listData;

	public InformationScreen(ArrayList<String> data) {
		super();

		this.listData = data.toArray(new String[data.size()]);
		
		//Set frame attributes
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 500, 300);
		this.setTitle("Lesson Results");
		
		JPanel view = new JPanel(new BorderLayout());
		this.add(view);
		
		// MAKE list view 
		final JList <String> list = new JList <String>(this.listData);
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
					
				}
			}
		});
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setBorder(BorderFactory.createEmptyBorder());
		listScroller.setPreferredSize(new Dimension(200, 80));  /// 1500
		view.add(listScroller, BorderLayout.CENTER);
	}
}
