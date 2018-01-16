import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {

	private Color background = Color.white;

	public void setColor(Color c) {
		this.background = c;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(background);
		g2d.fillRect(0, 0, getWidth(), getHeight() );
	}
}
