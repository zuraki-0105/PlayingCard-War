package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

public class SelectPlayersPane extends JPanel{
	private Image background;
	private JLabel errLabel;
	private JSpinner playerSpinner;
	private JSpinner cpuSpinner;
	
	public SelectPlayersPane(ScreenController controller, GamePane gamePane) {
		try {
			background = new ImageIcon(getClass().getResource("/others/background2.png")).getImage();
		} catch (Exception e) {
			System.out.println("背景画像の読み込みに失敗しました.");
		}
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createVerticalStrut(140));
		
		JLabel playerLabel = new JLabel("プレイヤー人数:");
		playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.playerSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));
		playerSpinner.setMaximumSize(new Dimension(80, 30));
		playerSpinner.setFont(new Font("", Font.BOLD, 20));
		playerSpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		JLabel cpuLabel = new JLabel("CPU人数:");
		cpuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Set.setSentence(28, Color.white, playerLabel, cpuLabel);
		
		this.cpuSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 3, 1));
		cpuSpinner.setMaximumSize(new Dimension(80, 30));
		cpuSpinner.setFont(new Font("", Font.BOLD, 20));
		cpuSpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		disableTextInput(playerSpinner, cpuSpinner);
		
		errLabel = new JLabel("総人数が1～4になるようにしてください!");
		Set.setSentence(15, Color.red, errLabel);
		errLabel.setAlignmentX(CENTER_ALIGNMENT);
		errLabel.setVisible(false);
		
		JButton button = new JButton("決定");
		Set.setSentence(18, null, button);
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
		button.addActionListener(e -> {
			int sum = getPlayerCount() + getCpuCount();
			if(sum <= 1 || sum > 4) {
				errLabel.setVisible(true);
				Timer timer = new Timer(5000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						errLabel.setVisible(false);
					}
				});
				timer.start();
//				new Timer(5000, event -> {
//					errLabel.setVisible(false);
//				}).start();
			} else {
				errLabel.setVisible(false);
				System.out.println("設定完了");
				gamePane.initializeAll(this);
				controller.showScreen("GAME");
			}
			
		});
		
		add(playerLabel);
		add(Box.createVerticalStrut(30));
		add(playerSpinner);
		add(Box.createVerticalStrut(100));
		add(cpuLabel);
		add(Box.createVerticalStrut(30));
		add(cpuSpinner);
		add(Box.createVerticalStrut(90));
		add(button);
		add(Box.createVerticalStrut(20));
		add(errLabel);
		
		
		add(Box.createVerticalGlue());
		setOpaque(false); // 背景画像が透けるようにする
		
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(background != null) {
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	public int getPlayerCount() {
		return (int)this.playerSpinner.getValue();
	}
	
	public int getCpuCount() {
		return (int)this.cpuSpinner.getValue();
	}
	
	private void disableTextInput(JSpinner... spinners) {
		for(JSpinner spinner : spinners) {
			JComponent editor = spinner.getEditor();
			if (editor instanceof JSpinner.DefaultEditor defaultEditor) {
				JFormattedTextField textField = defaultEditor.getTextField();
				textField.setEditable(false);
			}
//			JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
		}
	}
}
