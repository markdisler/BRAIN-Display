import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class PeriodButton extends JButton {
	
	private int periodNumber;
	private boolean active;
	
	public PeriodButton(int periodNumber) {
		super("" + periodNumber);
		
		this.periodNumber = periodNumber;
		
		this.setBorderPainted(false);
		this.setOpaque(true);
		this.setFont(new Font(CoreConstants.FONT_TYPE, Font.PLAIN, 18));
		this.setBackground(new Color(235, 147, 147));
		this.setPreferredSize(new Dimension(66, 66));
	}
	
	public void setActive(boolean active) {
		if (active) {
			this.setBackground(new Color(155, 240, 158));
		} else {
			this.setBackground(new Color(235, 147, 147));
		}
		this.active = active;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public int getPeriod() {
		return this.periodNumber;
	}
}
