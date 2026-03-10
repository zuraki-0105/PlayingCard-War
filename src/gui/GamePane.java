package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import card.base.Card;
import card.base.Player;
import game.WarGameController;

public class GamePane extends JPanel {
	private ScreenController sc;
	private WarGameController warGameController;
	private JLayeredPane layeredPane;

	private Image background;
	private ImageIcon backCard;
	private Map<String, ImageIcon> cardsImage;

	private List<JLabel> winLabels = new ArrayList<JLabel>();
	private List<JLabel> loseLabels = new ArrayList<JLabel>();
	private List<JLabel> drawLabels = new ArrayList<JLabel>();
	private List<JLabel> fieldCards = new ArrayList<JLabel>();
	private List<JLabel> scoreCards = new ArrayList<JLabel>();
	private OutlineLabel turnEndLabel;
	private OutlineLabel resultLabel;

	private int numHuman = -1;
	private int numCPU = -1;

	private int playersFlag = 0;

	public GamePane(ScreenController controller) {
		this.sc = controller;

	}

	public void initializeAll(SelectPlayersPane select) {
		removeAll(); // 初期化
		revalidate(); // 再配置
		repaint(); // 再描画

		this.warGameController = new WarGameController();
		this.numHuman = select.getPlayerCount();
		this.numCPU = select.getCpuCount();
		this.playersFlag = checkPlayers(numHuman, numCPU);
		this.background = Set.background;
		this.backCard = Set.backCard;
		this.cardsImage = Set.cardsImage;
		warGameController.startGame(numHuman, numCPU);

		setLayout(null);

		System.out.printf("人数 - Player:%d, CPU:%d\nplayerFlag = %d\n", numHuman, numCPU, playersFlag);

		// loadImages();
		initLayoutPane();
		initHidedLabel(numHuman + numCPU);

		setBackgroundField();

		JButton button = new JButton("戻る");
		button.setBounds(10, 600, 100, 40);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.showScreen("HOME");
			}
		});
		layeredPane.add(button, Integer.valueOf(101));

	}

	private void initLayoutPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 800, 700);

		add(layeredPane);
	}

	public void initHidedLabel(int numplayers) {
		this.turnEndLabel = new OutlineLabel("ターン終了");
		this.resultLabel = new OutlineLabel("結果発表！");

		for (int i = 0; i < numplayers; i++) {
			this.winLabels.add(i, new JLabel("Win!"));
			this.loseLabels.add(i, new JLabel("Lose.."));
			this.drawLabels.add(i, new JLabel("Draw"));
			Set.setSentence(25, new Color(0xFFBD48), winLabels.get(i));
			Set.setSentence(25, new Color(0xD1E7FF), loseLabels.get(i));
			Set.setSentence(25, new Color(0x000000), drawLabels.get(i));
			Set.setVisibles(false, winLabels.get(i), loseLabels.get(i), drawLabels.get(i));

			layeredPane.add(winLabels.get(i), JLayeredPane.MODAL_LAYER);
			layeredPane.add(loseLabels.get(i), JLayeredPane.MODAL_LAYER);
			layeredPane.add(drawLabels.get(i), JLayeredPane.MODAL_LAYER);
		}

		Set.setVisibles(false, turnEndLabel, resultLabel);
		Set.setLabelBounds(280, 300, 220, 80, turnEndLabel);
		Set.setSentence(40, Color.white, turnEndLabel);
		turnEndLabel.setOutlineWidth(2);
		Set.setLabelBounds(260, 300, 320, 100, resultLabel);
		Set.setSentence(50, Color.white, resultLabel);
		resultLabel.setOutlineWidth(4);

		layeredPane.add(turnEndLabel, JLayeredPane.MODAL_LAYER);
		layeredPane.add(resultLabel, JLayeredPane.MODAL_LAYER);

		if (numplayers == 2) {
			Set.setLabelBounds(450, 420, 100, 80, winLabels.get(0), loseLabels.get(0), drawLabels.get(0));
			Set.setLabelBounds(450, 170, 100, 80, winLabels.get(1), loseLabels.get(1), drawLabels.get(1));
		} else if (numplayers == 3) {
			Set.setLabelBounds(550, 420, 100, 80, winLabels.get(0), loseLabels.get(0), drawLabels.get(0));
			Set.setLabelBounds(350, 170, 100, 80, winLabels.get(1), loseLabels.get(1), drawLabels.get(1));
			Set.setLabelBounds(550, 170, 100, 80, winLabels.get(2), loseLabels.get(2), drawLabels.get(2));
		} else if (numplayers == 4) {
			Set.setLabelBounds(450, 420, 100, 80, winLabels.get(0), loseLabels.get(0), drawLabels.get(0));
			Set.setLabelBounds(450, 170, 100, 80, winLabels.get(1), loseLabels.get(1), drawLabels.get(1));
			Set.setLabelBounds(450, 420, 100, 80, winLabels.get(2), loseLabels.get(2), drawLabels.get(2));
			Set.setLabelBounds(450, 170, 100, 80, winLabels.get(3), loseLabels.get(3), drawLabels.get(3));
		}
	}

	private void setBackgroundField() {
		JLabel backgroundLabel = new JLabel(new ImageIcon(background));
		backgroundLabel.setBounds(0, 0, 800, 700);
		layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

		List<Player<Card>> players = warGameController.getPlayer();
		initFieldCards(players.size());

		GameContext context = new GameContext(warGameController, sc, layeredPane,
				background, backCard, cardsImage, winLabels, loseLabels, drawLabels,
				fieldCards, scoreCards, turnEndLabel, resultLabel, numHuman, playersFlag, players);

		switch (players.size()) {
			case 2:
				// num2Players(players);
				layeredPane.add(new TwoPlayersMode(context), JLayeredPane.PALETTE_LAYER);
				break;
			default:
				layeredPane.add(new PreparationPane(background), JLayeredPane.PALETTE_LAYER);
				break;
		}

	}

	private void initFieldCards(int size) {
		fieldCards.clear();
		scoreCards.clear();
		for (int i = 0; i < size; i++) {
			fieldCards.add(null);
			scoreCards.add(null);
		}
	}

	public int checkPlayers(int human, int cpu) {
		if (human == 1 && cpu == 1)
			return 11;
		else if (human == 1 && cpu == 2)
			return 12;
		else if (human == 1 && cpu == 3)
			return 13;
		else if (human == 2 && cpu == 0)
			return 20;
		else if (human == 2 && cpu == 1)
			return 21;
		else if (human == 2 && cpu == 2)
			return 22;
		else if (human == 3 && cpu == 0)
			return 30;
		else if (human == 3 && cpu == 1)
			return 31;
		else if (human == 4 && cpu == 0)
			return 40;
		else
			return 0;
	}

	// private List<JLabel> restCardsLabels = new ArrayList<JLabel>();
	// private List<Card> currentRoundCards = new ArrayList<>(); // 表面化用にカードを保持
	// private int revealedHumanCount = 0; // 今回のラウンドで表に出した人間の人数
	// private List<Integer> numRestCardsList = new ArrayList<Integer>(); //山札の残り枚数
	//
	// private ResultPane resultPane;
	// private Player<Card> winner = null;
	// private List<Player<Card>> finalWinners = new ArrayList<Player<Card>>();
	// private int flag = 0;
	//
	// public void num2Players(List<Player<Card>> players) {
	//
	//
	// initRestCardsLabel(players.size());
	// updateRestCardsLabel(players.size(), players.get(0).getHandSize(),
	// players.get(1).getHandSize());
	//
	// JLabel player1Deck = new JLabel(backCard);
	// JLabel player2Deck = new JLabel(backCard);
	//
	//
	// player1Deck.setBounds(650, 500, CARD_WIDTH, CARD_HEIGHT);
	// player2Deck.setBounds(40, 20, CARD_WIDTH, CARD_HEIGHT);
	// layeredPane.add(player1Deck, Integer.valueOf(1));
	// layeredPane.add(player2Deck, Integer.valueOf(1));
	//
	//
	// currentRoundCards.clear();
	// revealedHumanCount = 0;
	//
	// player1Deck.addMouseListener(new MouseAdapter() {
	// @Override
	// public void mouseClicked(MouseEvent e) {
	// if(fieldCards.get(0) != null) return; // 二度クリック防止
	// if(players.get(0).getHandSize() - 1 == 0) {
	// player1Deck.setVisible(false); //１枚引いて山札が0なら画像削除
	// }
	//
	// Card c = players.get(0).getHand().get(0);
	// currentRoundCards.add(0, c); // indexを0に指定して固定
	// JLabel backLabel = new JLabel(backCard);
	// backLabel.setBounds(335, 380, CARD_WIDTH+10, CARD_HEIGHT+10);
	// fieldCards.set(0, backLabel);
	// layeredPane.add(backLabel, Integer.valueOf(1));
	//
	// revealedHumanCount++;
	// if(revealedHumanCount == numHuman && playersFlag == 11) {
	// // CPUの番
	// if(players.get(1).getHandSize() - 1 == 0) {
	// player2Deck.setVisible(false); //１枚引いて山札が0なら画像削除
	// }
	// Card cpuCard = players.get(1).getHand().get(0);
	// currentRoundCards.add(1, cpuCard);
	// JLabel cpuBack = new JLabel(backCard);
	// cpuBack.setBounds(335, 130, CARD_WIDTH+10, CARD_HEIGHT+10);
	// fieldCards.set(1, cpuBack);
	// layeredPane.add(cpuBack, Integer.valueOf(1));
	// }
	// }
	// });
	//
	// if(playersFlag == 20) { //players.get(1).getType() == PlayerType.CPU
	// player2Deck.addMouseListener(new MouseAdapter() {
	// @Override
	// public void mouseClicked(MouseEvent e) {
	// if(fieldCards.get(1) != null) return; // 二度クリック防止
	// if(players.get(1).getHandSize() - 1 == 0) {
	// player2Deck.setVisible(false); //１枚引いて山札が0なら画像削除
	// }
	//
	// Card c = players.get(1).getHand().get(0);
	// currentRoundCards.add(1, c); // indexを1に指定して固定
	// JLabel backLabel = new JLabel(backCard);
	// backLabel.setBounds(335, 130, CARD_WIDTH+10, CARD_HEIGHT+10);
	// fieldCards.set(1, backLabel);
	// layeredPane.add(backLabel, Integer.valueOf(1));
	//
	// revealedHumanCount++;
	// }
	// });
	// }
	// // layeredPane クリック時に表にする
	// layeredPane.addMouseListener(new MouseAdapter() {
	// @Override
	// public void mouseClicked(MouseEvent e) {
	//
	// if(revealedHumanCount == numHuman && flag == 0) {
	// //表面を表示
	// for (int i = 0; i < currentRoundCards.size(); i++) {
	// Card card = currentRoundCards.get(i);
	// JLabel currentLabel = fieldCards.get(i);
	// layeredPane.remove(currentLabel);
	//
	// JLabel frontLabel = new JLabel(cardsImage.get(card.getString()));
	// int x, y;
	// if(i == 0) {
	// x = 335;
	// y = 380;
	// } else {
	// x = 335;
	// y = 130;
	// }
	// frontLabel.setBounds(x, y, CARD_WIDTH+10, CARD_HEIGHT+10);
	// fieldCards.set(i, frontLabel);
	// layeredPane.add(frontLabel, Integer.valueOf(1));
	// }
	// layeredPane.repaint();
	//
	//
	//
	// winner = warGameController.war();
	//
	// //WinとLoseのラベルの表示
	// if(winner == null) {
	// System.out.println("Draw");
	// for(JLabel label : drawLabels) label.setVisible(true);
	// } else {
	// System.out.println(winner.getId() + "_Winner: " + winner.getName());
	// winLabels.get(winner.getId()).setVisible(true);
	// for(int i = 0; i < players.size(); i++) {
	// if(i != winner.getId()) {
	// loseLabels.get(i).setVisible(true);
	// }
	// }
	//
	// }
	//
	// updateRestCardsLabel(players.size(), players.get(0).getHandSize(),
	// players.get(1).getHandSize());
	// layeredPane.repaint();
	//
	// flag = 1;
	//
	// }
	// else if(flag == 1) {
	//
	// //勝敗ラベルと場のカードを消す
	// for(int i = 0; i < players.size(); i++) {
	// winLabels.get(i).setVisible(false);
	// loseLabels.get(i).setVisible(false);
	// drawLabels.get(i).setVisible(false);
	// layeredPane.remove(fieldCards.get(i));
	// }
	//
	// if(winner != null) {
	// //勝者のスコアラベルにカードが残ってたら削除
	// if(scoreCards.get(winner.getId()) != null) {
	// System.out.println("removed");
	// layeredPane.remove(scoreCards.get(winner.getId()));
	//
	// }
	//
	// Card card = null;
	// //スコアの最後尾がwinner.idじゃなかったら
	// if(winner.getId() != players.size()-1) {
	// int index = winner.getScoreLog().size() - 1;
	// card = winner.getScoreLog().get(index);
	// } else {
	// int index = winner.getScoreLog().size() - 1 - 1;
	// card = winner.getScoreLog().get(index);
	// }
	//
	// System.out.println("size=" + winner.getScoreLog().size());
	// scoreCards.set(winner.getId(), new JLabel(cardsImage.get(card.getString())));
	// layeredPane.add(scoreCards.get(winner.getId()), Integer.valueOf(2));
	//
	// if(winner.getId() == 0) {
	// Set.setLabelBounds(Set.ID0_W_SCORE_2, Set.ID0_H_SCORE_2, CARD_WIDTH,
	// CARD_HEIGHT, scoreCards.get(0));
	// } else if(winner.getId() == 1) {
	// Set.setLabelBounds(Set.ID1_W_SCORE_2, Set.ID1_H_SCORE_2, CARD_WIDTH,
	// CARD_HEIGHT, scoreCards.get(1));
	// }
	// }
	//
	//
	// turnEndLabel.setVisible(true);
	// layeredPane.revalidate();
	// layeredPane.repaint();
	//
	// flag = 2;
	//
	// System.out.println("Turn end");
	// }
	// else if(flag == 2) {
	// // 次のラウンドに備えてリセット
	// hideLabel(players.size());
	// setNullFieldCards(players.size());
	// currentRoundCards.clear();
	// revealedHumanCount = 0;
	//
	// if(players.get(0).getHandSize() == 0) {
	// System.out.println("山札がなくなりました");
	// flag = 3;
	// } else {
	// flag = 0;
	// }
	//
	// }
	// else if(flag == 3) {
	// finalWinners = warGameController.displayResult();
	// resultLabel.setVisible(true);
	// flag = 4;
	// }
	// else if(flag == 4) {
	// System.out.println("結果発表画面");
	// resultLabel.setVisible(false);
	// resultPane = new ResultPane(finalWinners, new ActionListener() {
	// @Override //ホーム画面に戻って全て初期化
	// public void actionPerformed(ActionEvent e) {
	// resultPane.setVisible(false);
	// flag = 0;
	// sc.showScreen("HOME");
	// }
	// });
	//
	// layeredPane.add(resultPane, Integer.valueOf(5));
	//
	// flag = 5;
	// }
	// }
	// });
	//
	//
	// JLabel player1Name = new JLabel(players.get(0).getName());
	// JLabel player2Name = new JLabel(players.get(1).getName());
	// player1Name.setBounds(350, 620, 100, 40);
	// player2Name.setBounds(350, 0, 100, 40);
	// Set.setSentence(18, Color.white, player1Name, player2Name);
	//
	//
	// layeredPane.add(player1Name, Integer.valueOf(1));
	// layeredPane.add(player2Name, Integer.valueOf(1));
	// }
	// private void setNullFieldCards(int size) {
	// for(int i = 0; i < size; i++) {
	// fieldCards.set(i, null);
	// }
	// }
	//
	//
	//
	//
	// public void initRestCardsLabel(int numplayers) {
	// restCardsLabels.clear();
	// for(int i = 0; i < numplayers; i++) {
	// restCardsLabels.add(null);
	// }
	// }
	//
	// private void updateRestCardsLabel(int numplayers, int... numRestCards){
	// this.numRestCardsList.clear();
	// for(int num : numRestCards) {
	// numRestCardsList.add(num);
	// }
	//
	// for(int i = 0; i < numplayers; i++) {
	// if(restCardsLabels.get(i) != null) {
	// layeredPane.remove(restCardsLabels.get(i));
	// }
	// }
	//
	// restCardsLabels.clear();
	// for(int i = 0; i < numplayers; i++) {
	// this.restCardsLabels.add(new JLabel("残り : " +
	// Integer.toString(numRestCardsList.get(i)) + "枚"));
	// Set.setSentence(18, new Color(0xE2E2E2), restCardsLabels.get(i));
	// layeredPane.add(restCardsLabels.get(i), Integer.valueOf(3));
	// }
	// if(numplayers == 2) {
	// Set.setLabelBounds(Set.ID0_W_DECK_2+5, Set.ID0_H_DECK_2-45, 100, 80,
	// restCardsLabels.get(0));
	// Set.setLabelBounds(Set.ID1_W_DECK_2+10, Set.ID1_H_DECK_2+115, 100, 80,
	// restCardsLabels.get(1));
	// } else if(numplayers == 3) {
	//
	// } else if(numplayers == 4) {
	//
	// }
	//
	// }
	//
	// private void hideLabel(int numplayers) {
	// for(int i = 0; i < numplayers; i++) {
	// winLabels.get(i).setVisible(false);
	// loseLabels.get(i).setVisible(false);
	// drawLabels.get(i).setVisible(false);
	// }
	// turnEndLabel.setVisible(false);
	// resultLabel.setVisible(false);
	// }

}
