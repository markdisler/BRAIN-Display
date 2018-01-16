import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SimpleColorPicker extends JColorChooser {

	private JLabel previewLabel;
	public SimpleColorPicker(Color c, JLabel previewLbl) {
		super(c);

		this.previewLabel = previewLbl;
		this.setPreviewPanel(new JPanel());

		this.getSelectionModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ColorSelectionModel model = (ColorSelectionModel) e.getSource();
				previewLabel.setBackground(model.getSelectedColor());
			}
		});

		AbstractColorChooserPanel[] panels = this.getChooserPanels();
		for (AbstractColorChooserPanel accp : panels) {
			if (accp.getDisplayName().equals("RGB")) {
				this.setChooserPanels(new AbstractColorChooserPanel[]{accp});
			}
		}
	}
	
	public void setColor(Color c) {
		super.setColor(c);
		this.previewLabel.setBackground(c);
	}

}

class PreviewPanel extends JComponent {
	Color currentColor;
	public PreviewPanel(JColorChooser chooser) {
		this.currentColor = chooser.getColor();
		this.setPreferredSize(new Dimension(50, 50));
	}
	public void paint(Graphics g) {
		g.setColor(currentColor);
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
}