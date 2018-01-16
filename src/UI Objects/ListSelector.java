import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ListSelector extends JPanel{
	
	private JComboBox<String> box;
	
	public ListSelector(String caption, String[] list) {

		// CONFIGURE panel
		this.setLayout(new FlowLayout(FlowLayout.LEFT));		

		// CREATE caption
		JLabel lbl = new JLabel(caption);

		// CREATE UI Box
		this.box = new JComboBox<String>(list);

		// SET fonts
		Font f = new Font(CoreConstants.FONT_TYPE, Font.PLAIN, 14);
		lbl.setFont(f);
		this.box.setFont(f);
		
		// ADD components to screen
		this.add(lbl);
		this.add(this.box);
	}
	
	public void setActive(boolean active) {
		this.box.setEnabled(active);
	}
	
	public String getSelectedListItem() {
		return this.box.getSelectedItem().toString();
	}
	
	public void setSelectedListItem(String item) {
		this.box.setSelectedItem(item);
	}
}
