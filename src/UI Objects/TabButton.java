import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabButton extends JButton {
	
	private boolean toggled;

	public TabButton(String text) {
		super(" " + text + CoreConstants.LIST_PADDING_STRING);
		
		this.setHorizontalAlignment(SwingConstants.LEFT);
		this.setPreferredSize(new Dimension(220, 30));  
		this.setFont(new Font(CoreConstants.FONT_TYPE, Font.PLAIN, 16));
		this.setOpaque(true);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
		
		Border line = new LineBorder(Color.LIGHT_GRAY);
		Border margin = new EmptyBorder(0, 0, 0, 0);  //// T L B R
		Border compound = new CompoundBorder(line, margin);
		this.setBorder(compound);
	}
	
	public void setToggle(boolean on) {
		this.toggled = on;
		if (on) {
			this.setBackground(Color.WHITE);
		} else {
			this.setBackground(new Color(229, 229, 229));
		}
	}
	
	public boolean isToggled() {
		return this.toggled;
	}
}
