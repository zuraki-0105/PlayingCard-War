package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PreparationPane extends JPanel{
	Image background;
	public PreparationPane(Image background) {
		this.background = background;
		setVisible(true);
		setLayout(new GridBagLayout());
		setSize(Set.FRAME_W, Set.FRAME_H);
		setOpaque(false);
		
		
		
		JLabel temp = new JLabel("準備中m(__)m...");
		Set.setSentence(45, Color.white, temp);
		temp.setBounds(230, 300, 400, 50);
		add(temp);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(background != null) {
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
}
