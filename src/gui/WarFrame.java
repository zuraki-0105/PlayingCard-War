package gui;

import javax.swing.JFrame;

public class WarFrame extends JFrame{
	private ScreenController sc = new ScreenController();
	public WarFrame() {
		setInitialize();
		Set.loadImages();
		
		HomePane homePane = new HomePane(sc);
		GamePane gamePane = new GamePane(sc);
		SelectPlayersPane selectPane = new SelectPlayersPane(sc, gamePane);
		
		sc.addPanel(homePane, "HOME");
		sc.addPanel(gamePane, "GAME");
		sc.addPanel(selectPane, "SELECT");
		sc.addPanel(new SettingPane(sc), "SET");
		
		super.add(sc.getPanel());
		
		super.setVisible(true);
	}
	
	public void setInitialize() {
		super.setTitle("トランプ");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setSize(800, 700);
		super.setResizable(false);
	}
	
}
