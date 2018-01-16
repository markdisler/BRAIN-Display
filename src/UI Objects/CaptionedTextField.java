import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CaptionedTextField extends JPanel {
	
	private JTextField field;
	private Border originalBorder;
	
	public CaptionedTextField(String caption, int cols) {

		if (cols < 1) {
			cols = 10;
		}
		
		// CONFIGURE panel
		this.setLayout(new FlowLayout(FlowLayout.LEFT));		

		// CREATE caption
		JLabel lbl = new JLabel(caption);

		// CREATE UI Box
		this.field = new JTextField(cols);
		this.originalBorder = this.field.getBorder();

		// SET fonts
		Font f = new Font(CoreConstants.FONT_TYPE, Font.PLAIN, 14);
		lbl.setFont(f);
		this.field.setFont(f);
		
		// ADD components to screen
		this.add(lbl);
		this.add(this.field);
	}
	
	public String getText() {
		return this.field.getText();
	}
	
	public void setText(String text) {
		this.field.setText(text);
	}
	
	public void setActive(boolean active) {
		this.field.setEnabled(active);
	}
	
	public void setHighlighted(boolean highlighted) {
		if (highlighted) {
			Border line = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED); 
			this.field.setBorder(line);
		} else {
			this.field.setBorder(this.originalBorder);
		}
	}
	
}
