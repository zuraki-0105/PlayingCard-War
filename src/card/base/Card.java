package card.base;

public abstract class Card {
	private boolean isFaceUp = true;
	
	
	public abstract String getSuit();
	public abstract String getRank();
	public abstract String getString();
	public abstract int getValue();
	
	
	public boolean isFaceUp() { return isFaceUp; }
	public void flip() { isFaceUp = !isFaceUp; }
}
