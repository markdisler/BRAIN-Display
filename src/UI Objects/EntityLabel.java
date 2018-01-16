import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class EntityLabel extends JLabel {

	private GridBagConstraints constraint;
	private int period;
	private boolean isBottom;
	private boolean isRowEnd;

	public EntityLabel (String t, int x, int y, int wx, int wy, boolean isBottom, boolean isRowEnd) {
		super(t);		
		this.setOpaque(true);	
		this.constraint = createConstraint(x,y,wx,wy);
		this.isBottom = isBottom;
		this.isRowEnd = isRowEnd;

		Theme themeReference = ThemeParser.shared().getCurrentTheme();
		this.updateTheme(themeReference);
	}

	public void updateTheme(Theme themeReference) {
		this.setFont(new Font (CoreConstants.FONT_TYPE, Font.PLAIN, 52));

		if (this.constraint.gridx == 0) {
			this.setBackground(themeReference.headingBG);
			this.setForeground(themeReference.headingText);
		} else {
			if (this.constraint.gridy % 2 == 0) {
				this.setBackground(themeReference.contentBG);
				this.setForeground(themeReference.contentText);
			} else {
				this.setBackground(themeReference.alternatingBG);
				this.setForeground(themeReference.alternatingText);
			}
		}
		int xBorder = 1, yBorder = 1, xEndBorder = 1, yBottomBorder = 1;
		if (this.constraint.gridx == 0) xBorder++;
		if (this.constraint.gridy == 0)	yBorder++;
		if (this.isRowEnd) xEndBorder++;
		if (this.isBottom) yBottomBorder++;
		this.setBorder(BorderFactory.createMatteBorder(yBorder,xBorder,yBottomBorder,xEndBorder,themeReference.outline));
	}

	public GridBagConstraints createConstraint(int x, int y, int wx, int wy) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,0);
		c.gridx = x;
		c.gridy = y;
		c.weightx = wx;
		c.weighty = wy;
		return c;
	}

	public void setPeriod (int p) {
		this.period = p;
	}

	public int getPeriod () {
		return this.period;
	}

	public GridBagConstraints getConstraint () {
		return this.constraint;
	}

}
