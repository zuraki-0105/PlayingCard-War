package gui;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class ScreenController {
	private JPanel mainPanel;
	private CardLayout cardLayout = new CardLayout();
	
	public ScreenController() {
		mainPanel = new JPanel(cardLayout);
	}
	
	public JPanel getPanel() {
		return mainPanel;
	}
	
	public void addPanel(JPanel pane, String name) {
		mainPanel.add(pane, name);
	}
	
	public void showScreen(String name) {
		cardLayout.show(mainPanel, name);
	}
}
