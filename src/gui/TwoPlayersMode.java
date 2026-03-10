package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import card.base.Card;
import card.base.Player;
import game.WarGameController;

public class TwoPlayersMode extends JPanel{
	
//	private GameContext context;
	private WarGameController warGameController;
	private ScreenController sc;
	private JLayeredPane layeredPane;
	
	private int numHuman = -1;
	private int playersFlag = -1;
	private List<Player<Card>> players;
	private List<JLabel> winLabels = new ArrayList<JLabel>();
	private List<JLabel> loseLabels = new ArrayList<JLabel>();
	private List<JLabel> drawLabels = new ArrayList<JLabel>();
	private List<JLabel> restCardsLabels = new ArrayList<JLabel>();
	private List<JLabel> fieldCards = new ArrayList<JLabel>();
	private List<JLabel> scoreCards = new ArrayList<JLabel>();
	private OutlineLabel turnEndLabel;
	private OutlineLabel resultLabel;
	private Map<String, ImageIcon> cardsImage;
	private ImageIcon backCard;
	
	private int flag = 0;
	private List<Card> currentRoundCards = new ArrayList<>(); // 表面化用にカードを保持
	private int revealedHumanCount = 0; // 今回のラウンドで表に出した人間の人数
	private Player<Card> winner = null;
	private List<Integer> numRestCardsList = new ArrayList<Integer>(); //山札の残り枚数
	private ResultPane resultPane;
	private List<Player<Card>> finalWinners = new ArrayList<Player<Card>>();
	
	
	public TwoPlayersMode(GameContext context) {
//		this.context = context;
		
		this.warGameController = context.getWarGameController();
		this.sc = context.getScreenController();
		this.layeredPane = context.getLayeredPane();
		
		this.numHuman = context.getNumHuman();
		this.playersFlag = context.getPlayersFlag();
		this.players = context.getPlayers();
		
		this.winLabels = context.getWinLabels();
		this.loseLabels = context.getLoseLabels();
		this.drawLabels = context.getDrawLabels();
		this.fieldCards = context.getFieldCards();
		this.scoreCards = context.getScoreCards();
		this.turnEndLabel = context.getTurnEndLabel();
		this.resultLabel = context.getResultLabel();
		
		this.cardsImage  = context.getCardsImage();
		this.backCard = context.getBackCard();
		
		mainFunction();
	}
	
	
	public void mainFunction() {
		initRestCardsLabel(players.size());
		updateRestCardsLabel(players.size(), players.get(0).getHandSize(), players.get(1).getHandSize());
		
		JLabel player1Deck = new JLabel(backCard);
		JLabel player2Deck = new JLabel(backCard);
		player1Deck.setBounds(Set.ID0_W_DECK, Set.ID0_H_DECK, Set.CARD_WIDTH, Set.CARD_HEIGHT);
		player2Deck.setBounds(Set.ID1_W_DECK, Set.ID1_H_DECK, Set.CARD_WIDTH, Set.CARD_HEIGHT);
		layeredPane.add(player1Deck, JLayeredPane.PALETTE_LAYER);
		layeredPane.add(player2Deck, JLayeredPane.PALETTE_LAYER);
		
		JLabel player1Name = new JLabel(players.get(0).getName());
		JLabel player2Name = new JLabel(players.get(1).getName());
		player1Name.setBounds(Set.ID0_NAME_W, Set.ID0_NAME_H, 100, 40);
		player2Name.setBounds(Set.ID1_NAME_W, Set.ID1_NAME_H, 100, 40);
		Set.setSentence(18, Color.white, player1Name, player2Name);
		layeredPane.add(player1Name, JLayeredPane.PALETTE_LAYER);
		layeredPane.add(player2Name, JLayeredPane.PALETTE_LAYER);
		
		
		currentRoundCards.clear();
		revealedHumanCount = 0;
		
		player1Deck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(fieldCards.get(0) != null) return; // 二度クリック防止
				if(players.get(0).getHandSize() - 1 == 0) {
					player1Deck.setVisible(false);	//１枚引いて山札が0なら画像削除
				}
				
				Card c = players.get(0).getHand().get(0);
				currentRoundCards.add(0, c); // indexを0に指定して固定
				JLabel backLabel = new JLabel(backCard);
				backLabel.setBounds(335, 380, Set.CARD_WIDTH+10, Set.CARD_HEIGHT+10);
				fieldCards.set(0, backLabel);
				layeredPane.add(backLabel, JLayeredPane.PALETTE_LAYER);
				
				revealedHumanCount++;
				if(revealedHumanCount == numHuman && playersFlag == 11) {
					// CPUの番
					if(players.get(1).getHandSize() - 1 == 0) {
						player2Deck.setVisible(false);	//１枚引いて山札が0なら画像削除
					}
					Card cpuCard = players.get(1).getHand().get(0);
					currentRoundCards.add(1, cpuCard);
					JLabel cpuBack = new JLabel(backCard);
					cpuBack.setBounds(335, 130, Set.CARD_WIDTH+10, Set.CARD_HEIGHT+10);
					fieldCards.set(1, cpuBack);
					layeredPane.add(cpuBack, JLayeredPane.PALETTE_LAYER);
				}
			}
		});
		
		if(playersFlag == 20) {   //players.get(1).getType() == PlayerType.CPU
			player2Deck.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(fieldCards.get(1) != null) return; // 二度クリック防止
					if(players.get(1).getHandSize() - 1 == 0) {
						player2Deck.setVisible(false);	//１枚引いて山札が0なら画像削除
					}
					
					Card c = players.get(1).getHand().get(0);
					currentRoundCards.add(1, c); // indexを1に指定して固定
					JLabel backLabel = new JLabel(backCard);
					backLabel.setBounds(335, 130, Set.CARD_WIDTH+10, Set.CARD_HEIGHT+10);
					fieldCards.set(1, backLabel);
					layeredPane.add(backLabel, JLayeredPane.PALETTE_LAYER);
					
					revealedHumanCount++;					
				}
			});
		}	
		// layeredPane クリック時に表にする
		layeredPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(revealedHumanCount == numHuman && flag == 0) {
					//表面を表示
					for (int i = 0; i < currentRoundCards.size(); i++) {
						Card card = currentRoundCards.get(i);
						JLabel currentLabel = fieldCards.get(i);
						layeredPane.remove(currentLabel);
						
						JLabel frontLabel = new JLabel(cardsImage.get(card.getString()));
						int x, y;
						if(i == 0) {
							x = 335;
							y = 380;
						} else {
							x = 335;
							y = 130;
						}
						frontLabel.setBounds(x, y, Set.CARD_WIDTH+10, Set.CARD_HEIGHT+10);
						fieldCards.set(i, frontLabel);
						layeredPane.add(frontLabel, JLayeredPane.PALETTE_LAYER);
					}
					layeredPane.repaint();
					
					
					
					winner = warGameController.war();
					
					//WinとLoseのラベルの表示
					if(winner == null) {
						System.out.println("Draw");
						for(JLabel label : drawLabels) label.setVisible(true);
					} else {
						System.out.println(winner.getId() + "_Winner: " + winner.getName());
						winLabels.get(winner.getId()).setVisible(true);
						for(int i = 0; i < players.size(); i++) {
							if(i != winner.getId()) {
								loseLabels.get(i).setVisible(true);
							}
						}
						
					}
					
					updateRestCardsLabel(players.size(), players.get(0).getHandSize(), players.get(1).getHandSize());
					layeredPane.repaint();
					
					flag = 1;
					
				}
				else if(flag == 1) {
					
					//勝敗ラベルと場のカードを消す
					for(int i = 0; i < players.size(); i++) {
						winLabels.get(i).setVisible(false);
						loseLabels.get(i).setVisible(false);
						drawLabels.get(i).setVisible(false);
						layeredPane.remove(fieldCards.get(i));
					}
					
					if(winner != null) {
						//勝者のスコアラベルにカードが残ってたら削除
						if(scoreCards.get(winner.getId()) != null) {
							System.out.println("removed");
							layeredPane.remove(scoreCards.get(winner.getId()));
							
						}
						
						Card card = null;
						//スコアの最後尾がwinner.idじゃなかったら
						if(winner.getId() != players.size()-1) {
							int index = winner.getScoreLog().size() - 1;
							card = winner.getScoreLog().get(index);
						} else {
							int index = winner.getScoreLog().size() - 1 - 1;
							card = winner.getScoreLog().get(index);
						}
						
						System.out.println("size=" + winner.getScoreLog().size());
						scoreCards.set(winner.getId(), new JLabel(cardsImage.get(card.getString())));
						layeredPane.add(scoreCards.get(winner.getId()), JLayeredPane.PALETTE_LAYER);
						
						if(winner.getId() == 0) {
							Set.setLabelBounds(Set.ID0_W_SCORE, Set.ID0_H_SCORE, Set.CARD_WIDTH, Set.CARD_HEIGHT, scoreCards.get(0));
						} else if(winner.getId() == 1) {
							Set.setLabelBounds(Set.ID1_W_SCORE, Set.ID1_H_SCORE, Set.CARD_WIDTH, Set.CARD_HEIGHT, scoreCards.get(1));
						}
					}
					
					
					turnEndLabel.setVisible(true);
					layeredPane.revalidate();
					layeredPane.repaint();
					
					flag = 2;
					
					System.out.println("Turn end");
				}
				else if(flag == 2) {
					// 次のラウンドに備えてリセット
					hideLabel(players.size());
					setNullFieldCards(players.size());
					currentRoundCards.clear();
					revealedHumanCount = 0;
					System.out.println("正常にリセットされました");
					
					if(players.get(0).getHandSize() == 0) {
						System.out.println("山札がなくなりました");
						flag = 3;
					} else {
						flag = 0;
					}
					
				}
				else if(flag == 3) {
					finalWinners = warGameController.displayResult();
					resultLabel.setVisible(true);
					flag = 4;
				}
				else if(flag == 4) {
					System.out.println("結果発表画面");
					resultLabel.setVisible(false);
					resultPane = new ResultPane(finalWinners, new ActionListener() {
						@Override //ホーム画面に戻って全て初期化
						public void actionPerformed(ActionEvent e) {
							resultPane.setVisible(false);
							flag = 0;
							sc.showScreen("HOME");
						}
					});
					
					layeredPane.add(resultPane, JLayeredPane.POPUP_LAYER);
					
					flag = 5;
				}
			}
		});
		
		
		
	}
	
	public void initRestCardsLabel(int numplayers) {
		restCardsLabels.clear();
		for(int i = 0; i < numplayers; i++) {
			restCardsLabels.add(null);
		}
	}
	
	private void updateRestCardsLabel(int numplayers, int... numRestCards){
		this.numRestCardsList.clear();
		for(int num : numRestCards) {
			numRestCardsList.add(num);
		}
		
		for(int i = 0; i < numplayers; i++) {
			if(restCardsLabels.get(i) != null) {
				layeredPane.remove(restCardsLabels.get(i));
			}
		}
		
		restCardsLabels.clear();
		for(int i = 0; i < numplayers; i++) {
			this.restCardsLabels.add(new JLabel("残り : " + Integer.toString(numRestCardsList.get(i)) + "枚"));
			Set.setSentence(18, new Color(0xE2E2E2), restCardsLabels.get(i));
			layeredPane.add(restCardsLabels.get(i), JLayeredPane.PALETTE_LAYER);
		}
		if(numplayers == 2) {
			Set.setLabelBounds(Set.ID0_W_DECK+5, Set.ID0_H_DECK-45, 100, 80, restCardsLabels.get(0));
			Set.setLabelBounds(Set.ID1_W_DECK+10, Set.ID1_H_DECK+115, 100, 80, restCardsLabels.get(1));
		} else if(numplayers == 3) {
			
		} else if(numplayers == 4) {
			
		}
		
	}
	
	private void hideLabel(int numplayers) {
		for(int i = 0; i < numplayers; i++) {
			winLabels.get(i).setVisible(false);
			loseLabels.get(i).setVisible(false);
			drawLabels.get(i).setVisible(false);
		}
		turnEndLabel.setVisible(false);
		resultLabel.setVisible(false);
	}
	
	private void setNullFieldCards(int size) {
		for(int i = 0; i < size; i++) {
			fieldCards.set(i, null);
		}
	}

}
