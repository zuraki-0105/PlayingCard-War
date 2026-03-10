package card.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import player.PlayerType;

public abstract class Player<E> {
	private String name;
	private int id;
	private List<Card> hand = new ArrayList<Card>();
	private List<E> scoreLog = new ArrayList<E>();
	private PlayerType type;
	
	
	
	public Player(String name, int id, PlayerType type) {
		this.name = name;
		this.id = id;
		this.type = type;
	}
	
	public String getName() { return name; }
	public int getId() { return id; }
	public List<Card> getHand() { return Collections.unmodifiableList(hand); }
	public List<E> getScoreLog() { return Collections.unmodifiableList(scoreLog); }
	public PlayerType getType() { return type; }
	public int getHandSize() { return hand.size(); }
	
	public boolean isHuman() {
		if(this.type == PlayerType.HUMAN) {
			return true;
		} else {
			return false;
		}
	}
	
	public void drawCard(Card card) {
		hand.add(card);
	}
	
	public void drawCards(List<Card> cards) {
		hand.addAll(cards);
	}
	
	
	public void addToLog(E e) {
		scoreLog.add(e);
	}
	
	public boolean hasCards() {
		return !hand.isEmpty();
	}
	
	public void clearHand() {
		hand.clear();
	}
	
	
	public Card putOutCard() {
		if (hand.isEmpty()) return null;
		return hand.remove(0);
	}
	
	public Card putOutCard(int index) {
		if (index < 0 || index >= hand.size()) return null;
		return hand.remove(index);
	}
	
	public Card putOutCard(Card card) {
		if (hand.contains(card)) {
			hand.remove(card);
			return card;
		}
		return null;
	}
	
}
