import javax.swing.SwingUtilities;

import gui.WarFrame;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new WarFrame();
			}
		});
//		new WarGame();
	}
}
