import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ThemeSectionLabel extends JPanel {

	public JLabel label;
	public JLabel colorPreview;
	
	public ThemeSectionLabel(String s) {
		super();

		this.setLayout(new BorderLayout());
		
		this.label = new JLabel(s);
		this.label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.add(this.label, BorderLayout.CENTER);
		
		this.colorPreview = new JLabel("               ");
		this.colorPreview.setBackground(Color.black);
		this.colorPreview.setOpaque(true);
		this.colorPreview.setBorder(BorderFactory.createMatteBorder(2,2,0,2,Color.black));
		this.add(this.colorPreview, BorderLayout.EAST);
		
		this.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.black));
	}
	
	public void setColor(Color c) {
		this.colorPreview.setBackground(c);
	}
	
	public Color getColor() {
		return colorPreview.getBackground();
	}
}
