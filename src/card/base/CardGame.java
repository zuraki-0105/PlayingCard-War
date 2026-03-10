package card.base;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.PlayingCard;

public abstract class CardGame{
	private List<Card> deck = new ArrayList<Card>();
	private List<Player<Card>> players = new ArrayList<Player<Card>>();
	
	public CardGame() {
		initializeDeck();
	}
	
	protected void shuffuleDeck() {
		Collections.shuffle(deck);
	}
	
	public List<Card> getDeck() { return Collections.unmodifiableList(deck); }
	public List<Player<Card>> getPlayer() { return Collections.unmodifiableList(players); }
	
	protected void addPlayer(Player<Card> player) {
		players.add(player);
	}
	
	protected void clearPlayer() {
		players.clear();
	}
	
	protected void initializeDeck() {
		deck.clear();
		String[] suits = {"s", "d", "c", "h"};
		String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		
		for(String suit : suits) {
			for(String rank : ranks) {
				deck.add(new PlayingCard(suit, rank));
			}
		}
	}
	
	
	
	public abstract void drawCards();
	public abstract List<Player<Card>> displayResult();
	public abstract void startGame();
	public abstract void endGame();
	
	

}
