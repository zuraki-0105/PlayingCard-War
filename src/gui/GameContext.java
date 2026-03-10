package gui;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import card.base.Card;
import card.base.Player;
import game.WarGameController;

public class GameContext {
	public WarGameController warGameController;
	public ScreenController screenController;
	public JLayeredPane layeredPane;
	public Image background;
	public ImageIcon backCard;
	public Map<String, ImageIcon> cardsImage;
	public List<JLabel> winLabels = new ArrayList<JLabel>();
	public List<JLabel> loseLabels = new ArrayList<JLabel>();
	public List<JLabel> drawLabels = new ArrayList<JLabel>();
	public List<JLabel> fieldCards = new ArrayList<JLabel>();
	public List<JLabel> scoreCards = new ArrayList<JLabel>();
	public OutlineLabel turnEndLabel;
	public OutlineLabel resultLabel;
	public int numHuman;
	public int playersFlag;
	public List<Player<Card>> players;
	public Player<Card> winner;
	
	public GameContext(WarGameController warGameController, ScreenController sc, JLayeredPane layeredPane,
			Image background, ImageIcon backCard, Map<String, ImageIcon> cardsImage,
			List<JLabel> winLabels, List<JLabel> loseLabels, List<JLabel> drawLabels,
			List<JLabel> fieldCards, List<JLabel> scoreCards,
			OutlineLabel turnEndLabel, OutlineLabel resultLabel,
			int numHuman, int playersFlag, List<Player<Card>> players) {
		this.warGameController = warGameController;
		this.screenController = sc;
		this.layeredPane = layeredPane;
		this.background = background;
		this.backCard = backCard;
		this.cardsImage = cardsImage;
		this.winLabels = winLabels;
		this.loseLabels = loseLabels;
		this.drawLabels = drawLabels;
		this.fieldCards = fieldCards;
		this.scoreCards = scoreCards;
		this.turnEndLabel = turnEndLabel;
		this.resultLabel = resultLabel;
		this.numHuman = numHuman;
		this.playersFlag = playersFlag;
		this.players = players;
	}
	
	public WarGameController getWarGameController() { return warGameController; }
	public ScreenController getScreenController() { return screenController; }
	public JLayeredPane getLayeredPane() { return layeredPane; }
	public Image getBackground() { return background; }
	public ImageIcon getBackCard() { return backCard; }
	public Map<String, ImageIcon> getCardsImage() { return cardsImage; }
	public List<JLabel> getWinLabels() { return winLabels; }
	public List<JLabel> getLoseLabels() { return loseLabels; }
	public List<JLabel> getDrawLabels() { return drawLabels; }
	public List<JLabel> getFieldCards() { return fieldCards; }
	public List<JLabel> getScoreCards() { return scoreCards; }
	public OutlineLabel getTurnEndLabel() { return turnEndLabel; }
	public OutlineLabel getResultLabel() { return resultLabel; }
	public int getNumHuman() { return numHuman; }
	public int getPlayersFlag() { return playersFlag; }
	public List<Player<Card>> getPlayers() { return players; }
}
