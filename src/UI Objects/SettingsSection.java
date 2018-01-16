import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SettingsSection extends JPanel {
	
	protected JLabel titleLabel;
	protected JPanel contentView;
	protected JButton actionButton;
		
	public SettingsSection(String title, JButton customButton) {
		super(new BorderLayout());
		this.constructSectionView(title, customButton);		
	}
	
	public SettingsSection(String title, String buttonTitle) {
		super(new BorderLayout());
		
		JButton btn = new JButton(buttonTitle);
		btn.setContentAreaFilled(false);
		btn.setFont(new Font(CoreConstants.FONT_TYPE, Font.PLAIN, 16));
		btn.setForeground(new Color(0, 88, 255));
		
		
		this.constructSectionView(title, btn);
	}
	
	private void constructSectionView(String title, JButton b) {
		this.titleLabel = new JLabel(title);
		this.titleLabel.setBorder(new EmptyBorder(16, 10, 4, 0)); 
		this.titleLabel.setFont(new Font (CoreConstants.FONT_TYPE, Font.PLAIN, 16));
		this.add(this.titleLabel, BorderLayout.NORTH);
		
		this.contentView = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.contentView.setBorder(new EmptyBorder(0,15,10,0)); 
		this.add(this.contentView, BorderLayout.CENTER);
		
		this.actionButton = b;
		this.actionButton.setOpaque(true);
		this.add(this.actionButton, BorderLayout.SOUTH);
		
		Border line = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.lightGray); 
		Border margin = new EmptyBorder(5,5,5,5);  
		Border compound = new CompoundBorder(line, margin);
		this.actionButton.setBorder(compound);
				
		this.actionButton.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent evt) {
		    	actionButton.setBackground(new Color(245, 245, 245));
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	actionButton.setBackground(Color.WHITE);
		    }
		});
	}
	
	public void setBackground(Color bg) {
		super.setBackground(bg);
		
		if (this.titleLabel != null) {
			this.titleLabel.setBackground(bg);
		}
		if (this.contentView != null) {
			this.contentView.setBackground(bg);
		}
		if (this.actionButton != null) {
			this.actionButton.setBackground(bg);
		}
	}
		
	public void setTitle(String title) {
		this.titleLabel.setText(title);
	}
	
	public void setActionButtonText(String text) {
		this.actionButton.setText(text);
	}
	
	public void addItemToContentView(JComponent c) {
		this.contentView.add(c);
	}
	
	public JButton getActionButton() {
		return this.actionButton;
	}
}