import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class HeaderLabel extends JLabel {

	private boolean isBottom;
	private boolean isRowEnd;
	private GridBagConstraints constraint;
		
	public HeaderLabel (String t, int x, int y, int wx, boolean isBottom, boolean isRowEnd) {
		super(t);
		this.setOpaque(true);
		this.constraint = createConstraint(x,y,wx);
		this.isBottom = isBottom;
		this.isRowEnd = isRowEnd;
		
		Theme themeReference = ThemeParser.shared().getCurrentTheme();
		this.updateTheme(themeReference);
	}
	
	public void updateTheme(Theme themeReference) {
		this.setBackground(themeReference.headingBG);
		this.setForeground(themeReference.headingText);
		this.setFont(new Font (CoreConstants.FONT_TYPE, Font.PLAIN, 48));
		
		int xBorder = 1, yBorder = 1, xEndBorder = 1, yBottomBorder = 1;
		if (this.constraint.gridx == 0) xBorder++;
		if (this.constraint.gridy == 0)	yBorder++;
		if (this.isRowEnd) xEndBorder++;
		if (this.isBottom) yBottomBorder++;
		this.setBorder(BorderFactory.createMatteBorder(yBorder,xBorder,yBottomBorder,xEndBorder,themeReference.outline));
	}
	
	public GridBagConstraints createConstraint(int x, int y, int wx) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,0);
		c.gridx = x;
		c.gridy = y;
		c.weightx = wx;
		return c;
	}
	
	public GridBagConstraints getConstraint () {
		return this.constraint;
	}
	
}
