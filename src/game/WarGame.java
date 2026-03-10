package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import card.base.Card;
import card.base.CardGame;
import card.base.Player;
import player.CPUPlayer;
import player.PlayerType;
import player.YourPlayer;

public class WarGame extends CardGame{
	private List<Card> warField = new ArrayList<Card>();
	Scanner sn = new Scanner(System.in);
	
//	public static void main(String[] args) {
//		new WarGame();
//	}
	
	public WarGame() { 
		startGame();
		war();
		displayResult();
		endGame();
	}
	
	private void war() {
		while(super.getPlayer().get(0).hasCards()) {
			Player<Card> winnerPlayer = findPlayerHadMax();
			
			if(winnerPlayer == null) {
				System.out.printf("次のターンへ\n");
				accceptEnter();
				continue;
			} else {
				System.out.printf("このターンの勝者\n-> %s\n\n", winnerPlayer.getName());
				for(Card c : warField) {
					winnerPlayer.addToLog(c);
				}
				warField.clear();
				accceptEnter();
			}
		}
	}
	
	private void initPlayers() {
		
		System.out.printf("プレイヤーの人数: ");
        int numHuman = sn.nextInt();

        System.out.printf("CPUの人数: ");
        int numCPU = sn.nextInt();
        
        for (int i = 0; i < numHuman; i++) {
        	System.out.printf("プレイヤー%dの名前 : ", i + 1);
        	String name = sn.next();
        	super.addPlayer(new YourPlayer(name, i, PlayerType.HUMAN));
        }

        for (int i = 0; i < numCPU; i++) {
        	String name = "CPU" + (i + 1);
        	super.addPlayer(new CPUPlayer(name, i+numHuman, PlayerType.CPU));
        }
        System.out.printf("\nプレイヤー初期化完了: %d人\n", super.getPlayer().size());
	}
	
	private void initHands() {
		List<Card> deck = super.getDeck();
		List<Player<Card>> players = super.getPlayer();
		int numPlayers = players.size();
		int numCardPerPlayers = deck.size() / numPlayers;
		
//		int i = 0;
//		for(Card card : deck) {
//			players.get(i % numPlayers).drawCard(card);
//			i++;
//		}
		
		List<Integer> indexes = new ArrayList<>();
		
		for(int i = 0; i < numPlayers; i++) indexes.add(i);
		Collections.shuffle(indexes);
		
		for(int i = 0; i < numPlayers * numCardPerPlayers; i++) {
			int idx = indexes.get(i % numPlayers);
			players.get(idx).drawCard(deck.get(i));
		}
		
		for(Player<Card> p : players) {
			System.out.printf("%s %d枚\n", p.getName(), p.getHandSize());
		}
		System.out.println();
		sn.nextLine();
		accceptEnter();
	}
	
	private Player<Card> findPlayerHadMax() {
		List<Integer> values = new ArrayList<Integer>();
		
		for(Player<Card> p : super.getPlayer()) {
			Card removedCard = p.putOutCard();
			warField.add(removedCard);
			values.add(removedCard.getValue());
			System.out.printf("%s : %s\n\n", p.getName(), removedCard.getString());
			accceptEnter();
		}
		
		int maxValue = Collections.max(values);
		int countMax = Collections.frequency(values, maxValue);
		
		if(countMax > 1) return null;
		else return super.getPlayer().get(values.indexOf(maxValue));
//		return warField.get(values.indexOf(maxValue));
	}
	
	@Override
	public void startGame() {
		System.out.printf("\nゲームスタート!\n\n");
		super.shuffuleDeck();
		initPlayers();
		initHands();
	}

	@Override
	public List<Player<Card>> displayResult() {
		List<Player<Card>> players = super.getPlayer();
		List<Player<Card>> sortedWinners = new ArrayList<Player<Card>>(players);
		List<Integer> scores = new ArrayList<Integer>();
		
		sortedWinners.sort((p1, p2) -> Integer.compare(p2.getScoreLog().size(), p1.getScoreLog().size()));
		for(Player<Card> p : sortedWinners) scores.add(p.getScoreLog().size());
		
		int maxScore = sortedWinners.get(0).getScoreLog().size();
		int countMax = Collections.frequency(scores, maxScore);
		
		System.out.printf("\n結果発表！\n->");
		if(countMax > 1) {
			System.out.println("引き分け");
			for(Player<Card> p : sortedWinners) {
				if(p.getScoreLog().size() == maxScore) {
					System.out.printf("%s - ", p.getName());
				}
			}
			sortedWinners = null;
			System.out.printf("\nスコア : %d\n", maxScore);
		} else {
			System.out.printf("%s の勝ち! スコア: %d\n", sortedWinners.get(0).getName(), maxScore);
		}
		System.out.printf("\nゲームエンド！\n");
		
		return sortedWinners;
	}

	@Override
	public void drawCards() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void endGame() {
		
		System.out.printf("\nゲームエンド！\n");
		sn.close();
	}
	
	private void accceptEnter() {
		
		
		System.out.printf("---Enter---\n\n");
		sn.nextLine();
		
		
	}
}
