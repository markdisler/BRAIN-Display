
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ViewerMenuBar extends JMenu {

	private JButton settingsBtn;
	private JButton quitBtn;
	
	public ViewerMenuBar(String name) {
		super(name);
	
		this.setBackground(new Color(215, 219, 230));
		
		// Create menu panel
		JPanel p = new JPanel(null);
		p.setBackground(new Color(215, 219, 230));
		p.setPreferredSize(new Dimension(300, 400));
		add(p);
		
		String title = "<html><B>BRAIN</B> Display 2017</html>";
		JLabel titleLbl1 = new JLabel(title);
		titleLbl1.setFont(new Font (CoreConstants.FONT_TYPE, Font.PLAIN, 24));
		titleLbl1.setBounds(15, 40, 300, 50);
		p.add(titleLbl1);
		
		this.settingsBtn = new JButton("  Settings");
		this.settingsBtn.setOpaque(true);
		this.settingsBtn.setFocusPainted(false);
		this.settingsBtn.setBackground(Color.WHITE);
		this.settingsBtn.setHorizontalAlignment(SwingConstants.LEFT);
		this.settingsBtn.setFont(new Font (CoreConstants.FONT_TYPE, Font.PLAIN, 24));
		this.settingsBtn.setBounds(0, 90, 300, 50);
		this.settingsBtn.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
		p.add(this.settingsBtn);
		
		this.quitBtn = new JButton("  Quit");
		this.quitBtn.setOpaque(true);
		this.quitBtn.setFocusPainted(false);
		this.quitBtn.setBackground(Color.WHITE);
		this.quitBtn.setHorizontalAlignment(SwingConstants.LEFT);
		this.quitBtn.setFont(new Font (CoreConstants.FONT_TYPE, Font.PLAIN, 24));
		this.quitBtn.setBounds(0, 140, 300, 50);
		this.quitBtn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		p.add(this.quitBtn);
		
		String[] names = {"Mark Disler", "Dorothy Drysielski", "Eric Furman", "Anthony Santanastaso"};
		
		for (int i = 0; i < names.length; i++) {
			String first = names[i].split(" ")[0];
			String last = names[i].split(" ")[1];
			String nameString = "<html><B>" + first + "</B> "+ last+ "</html>";
			
			JLabel nameLbl = new JLabel(nameString);
			nameLbl.setFont(new Font (CoreConstants.FONT_TYPE, Font.PLAIN, 18));
			nameLbl.setBounds(15, 280 + (25 * i), 300, 25);
			p.add(nameLbl);
		}
		
		settingsBtn.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        settingsBtn.setBackground(CoreConstants.ACCENT_COLOR);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        settingsBtn.setBackground(Color.WHITE);
		    }
		});
		
		quitBtn.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        quitBtn.setBackground(CoreConstants.ACCENT_COLOR);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        quitBtn.setBackground(Color.WHITE);
		    }
		});
	}
	
	public JButton getSettingsButton() {
		return this.settingsBtn;
	}
	
	public JButton getQuitButton() {
		return this.quitBtn;
	}
	
	
}