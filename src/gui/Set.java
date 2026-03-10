package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Set {
	public static Image background;
	public static ImageIcon backCard;
	public static Map<String, ImageIcon> cardsImage;
	public static int FRAME_W = 800, FRAME_H = 700;
	public static int CARD_WIDTH = 100, CARD_HEIGHT = 150;
	
	
	public static int ID0_W_DECK = 650,  ID1_W_DECK = 40,   ID2_W_DECK = 650,   ID3_W_DECK = 40;
	public static int ID0_H_DECK = 480,  ID1_H_DECK = 40,   ID2_H_DECK = 40,   ID3_H_DECK = 40;
	public static int ID0_W_SCORE = 530, ID1_W_SCORE = 160, ID2_W_SCORE = 160, ID3_W_SCORE = 160;
	public static int ID0_H_SCORE = 480, ID1_H_SCORE = 40,  ID2_H_SCORE = 160, ID3_H_SCORE = 160;
	
	public static int ID0_NAME_W = ID0_W_DECK+20,             ID1_NAME_W = ID1_W_DECK+30, ID2_NAME_W = ID2_W_DECK+30, ID3_NAME_W = 40;
	public static int ID0_NAME_H = ID0_H_DECK+CARD_WIDTH+35, ID1_NAME_H = ID1_H_DECK-25, ID2_NAME_H = ID2_H_DECK-25, ID3_NAME_H = 40;
	
	
	public static int ID0_BACK_W = 435, ID1_BACK_W = 235, ID2_BACK_W = 435, ID3_BACK_W = 235;
	public static int ID0_BACK_H = 380, ID1_BACK_H = 130, ID2_BACK_H = 130, ID3_BACK_H = 380;
	
	public static void setSentence(int size, Color color, Component... components) {
		for(Component c : components) {
			c.setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.BOLD, size));
			c.setForeground(color);
		}
	}
	
	public static void setLabelBounds(int width, int height, int sizeY, int sizeX, Component... components) {
		for(Component c : components) {
			c.setBounds(width, height, sizeY, sizeX);
		}
	}
	
	public static void setVisibles(boolean bool, Component... components) {
		for(Component c : components) {
			c.setVisible(bool);
		}
	}
	
	public static void loadImages() {
		if(cardsImage == null) {
			cardsImage = initCardImage(Set.CARD_WIDTH+10, Set.CARD_HEIGHT+10);
		}
		
		try {
			background = new ImageIcon(Set.class.getResource("/others/background2.png")).getImage();
			backCard = new ImageIcon(Set.class.getResource("/cards/back1.png"));
			backCard = resizeImageIcon(backCard, Set.CARD_WIDTH, Set.CARD_HEIGHT);
		} catch (Exception e) {
			System.out.println("画像の読み込みに失敗しました.");
			//e.printStackTrace();
		}
	}
	
	private static Map<String, ImageIcon> initCardImage(int width, int height) {
		Map<String, ImageIcon> cardImages = new HashMap<>();
		String[] suits = {"s", "d", "c", "h"};
		String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		
		for(String suit : suits) {
			for(String rank : ranks) {
				String key = suit + rank; //sA
				String fileName = "/cards/" + key + ".png"; // /cards/sA.png
				
				try {
					ImageIcon icon = new ImageIcon(Set.class.getResource(fileName));
					icon = resizeImageIcon(icon, width, height);
					cardImages.put(key, icon);
				} catch (Exception e) {
					System.out.println("pngファイルの読み込みに失敗しました : " + fileName);
				}
			}
		}
		System.out.println("画像読み込みに成功しました");
		return cardImages;
	}
	
	// ImageIconを指定サイズにリサイズして返すメソッド
	private static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
		Image originalImage = icon.getImage();
		Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage);
	}
}
