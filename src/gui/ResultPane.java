package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import card.base.Card;
import card.base.Player;

public class ResultPane extends JPanel {
	private JButton backButton;
	
	public ResultPane(List<Player<Card>> players, ActionListener backAction) {
		setLayout(null);
		setBounds(100, 100, 600, 500);
		setBackground(new Color(0, 0, 0, 180));
		setVisible(true);
		
		
		JLabel titleLabel = new JLabel("最終結果");
		Set.setSentence(36, Color.white, titleLabel);
		titleLabel.setBounds(200, 30, 300, 50);
		add(titleLabel);
		
		
		for(int i = 0; i < players.size(); i++) {
			Player<Card> p = players.get(i);
			String text = String.format("%d位--%s : %d 枚", i+1, p.getName(), p.getScoreLog().size());
			JLabel scoreLabel = new JLabel(text);
			scoreLabel.setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.PLAIN, 24));
			scoreLabel.setForeground(Color.WHITE);
			scoreLabel.setBounds(150, 100 + i * 60, 400, 40);
			add(scoreLabel);
		}
		
		
		backButton = new JButton("ホームへ戻る");
		backButton.setBounds(225, 400, 150, 40);
		backButton.addActionListener(backAction);
		add(backButton);
	}
}
