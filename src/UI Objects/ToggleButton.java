import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ToggleButton extends JButton {
	
	private String onTitle, offTitle;
	private boolean toggledOn;
		
	public ToggleButton(String onTitle, String offTitle, boolean on) {
		super (on ? onTitle : offTitle);
		
		this.onTitle = onTitle;
		this.offTitle = offTitle;
		this.toggledOn = on;
		
		this.configureFromToggle();
		this.setContentAreaFilled(false);
		this.setFont(new Font(CoreConstants.FONT_TYPE, Font.PLAIN, 16));	
		
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				toggle();
			}
		});
	}
	
	public void toggle() {
		this.toggledOn = !this.toggledOn;
		this.configureFromToggle();
	}	
	
	private void configureFromToggle() {
		if (this.toggledOn) {
			this.setText(this.onTitle);
			this.setForeground(Color.RED);  //new Color(235, 147, 147)
		} else {
			this.setText(this.offTitle);
			this.setForeground(new Color(0, 88, 255));
		}
	}
	
	public boolean isToggledOn() {
		return this.toggledOn;
	}
}
