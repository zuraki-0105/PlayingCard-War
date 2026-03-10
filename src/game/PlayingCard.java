package game;

import card.base.Card;

public class PlayingCard extends Card{
	private String suit;
	private String rank;
	private int value;
	
	public PlayingCard(String suit, String rank) {
		this.suit = suit;
		this.rank = rank;
		this.value = calcValue(rank);
	}
	
	@Override
	public String getSuit() { return suit; }
	
	@Override
	public String getRank() { return rank; }
	
	@Override
	public String getString() { return String.format("%s%s", getSuit(), getRank()); }
	
	@Override
	public int getValue() { return value; }

	
	private int calcValue(String rank) {
		switch (rank) {
			case "A": return 1;
			case "J": return 11;
			case "Q": return 12;
			case "K": return 13;
			default:
				try {
					return Integer.parseInt(rank);
				} catch (NumberFormatException e) {
					return 0;
				}
		}
	}
}
