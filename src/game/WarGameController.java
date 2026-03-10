package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import card.base.Card;
import card.base.CardGame;
import card.base.Player;
import player.CPUPlayer;
import player.PlayerType;
import player.YourPlayer;

public class WarGameController extends CardGame {
	private List<Card> warField = new ArrayList<Card>();

	public WarGameController() {
	}

	// プレイヤーとCPUの登録と手札の初期化
	public void startGame(int numHuman, int numCPU) {
		super.shuffuleDeck();
		super.clearPlayer();

		for (int i = 0; i < numHuman; i++) {
			super.addPlayer(new YourPlayer("Player" + (i + 1), i, PlayerType.HUMAN));
		}
		for (int i = 0; i < numCPU; i++) {
			super.addPlayer(new CPUPlayer("CPU" + (i + 1), i + numHuman, PlayerType.CPU));
		}

		// debug_initHands(3);
		initHands();
		for (Player<Card> p : super.getPlayer()) {
			System.out.println(p.getName() + " : " + p.getHandSize() + "枚");
		}

	}

	public Player<Card> war() {

		Player<Card> winnerPlayer = findPlayerHadMax();

		if (winnerPlayer == null) {
			// System.out.printf("draw\n");

		} else {
			// System.out.printf("このターンの勝者\n-> %s\n\n", winnerPlayer.getName());
			for (Card c : warField) {
				winnerPlayer.addToLog(c);
			}
			warField.clear();
		}

		return winnerPlayer;
	}

	private void initHands() {
		List<Card> deck = super.getDeck();
		List<Player<Card>> players = super.getPlayer();
		int numPlayers = players.size();
		int numCardPerPlayers = deck.size() / numPlayers;

		// カードをディールする順番
		List<Integer> indexes = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++)
			indexes.add(i);
		Collections.shuffle(indexes);

		// 全員同じ枚数だけ配る
		for (int i = 0; i < numPlayers * numCardPerPlayers; i++) {
			int idx = indexes.get(i % numPlayers);
			players.get(idx).drawCard(deck.get(i));
		}
	}

	private void debug_initHands(int numCards) {
		List<Card> deck = super.getDeck();
		List<Player<Card>> players = super.getPlayer();
		int numPlayers = players.size();
		int numCardPerPlayers = numCards;

		// カードをディールする順番
		List<Integer> indexes = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++)
			indexes.add(i);
		Collections.shuffle(indexes);

		// 全員同じ枚数だけ配る
		for (int i = 0; i < numPlayers * numCardPerPlayers; i++) {
			int idx = indexes.get(i % numPlayers);
			players.get(idx).drawCard(deck.get(i));
		}
	}

	private Player<Card> findPlayerHadMax() {
		List<Integer> values = new ArrayList<Integer>();

		for (Player<Card> p : super.getPlayer()) {
			Card removedCard = p.putOutCard();
			System.out.println(removedCard.getString());
			warField.add(removedCard);
			values.add(removedCard.getValue());

		}

		int maxValue = Collections.max(values);
		int countMax = Collections.frequency(values, maxValue);

		if (countMax > 1)
			return null;
		else
			return super.getPlayer().get(values.indexOf(maxValue));
		// return warField.get(values.indexOf(maxValue));
	}

	@Override
	public List<Player<Card>> displayResult() {
		List<Player<Card>> players = super.getPlayer();
		List<Player<Card>> sortedWinners = new ArrayList<Player<Card>>(players);
		List<Integer> scores = new ArrayList<Integer>();

		sortedWinners.sort((p1, p2) -> Integer.compare(p2.getScoreLog().size(), p1.getScoreLog().size()));
		for (Player<Card> p : sortedWinners)
			scores.add(p.getScoreLog().size());

		int maxScore = sortedWinners.get(0).getScoreLog().size();
		int countMax = Collections.frequency(scores, maxScore);

		System.out.printf("\n結果発表！\n->");
		if (countMax > 1) {
			System.out.println("引き分け");
			for (Player<Card> p : sortedWinners) {
				if (p.getScoreLog().size() == maxScore) {
					System.out.printf("%s - ", p.getName());
				}
			}
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
		// List<Player<Card>> players = super.getPlayer();
		// List<Integer> scores = new ArrayList<Integer>();
		// Player<Card> winner;
		//
		// for(Player<Card> p : players) scores.add(p.getScoreLog().size());
		//
		// int maxScore = Collections.max(scores);
		// int countMax = Collections.frequency(scores, maxScore);

	}

	@Override
	public void startGame() {
	}
}
