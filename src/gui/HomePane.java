package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HomePane extends JPanel{
	private Image background;
	
	public HomePane(ScreenController controller) {
		background = Set.background;
		
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//タイトルラベル
		OutlineLabel titleLabel = new OutlineLabel("戦  争");
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		// スタートボタン
		JButton startButton = new JButton("ゲーム開始");
		startButton.setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.BOLD, 24));
		startButton.setForeground(Color.black);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		startButton.addActionListener(e -> {
			System.out.println("Start!");
			controller.showScreen("SELECT");
		});
		
		
		add(Box.createVerticalStrut(90));
		add(titleLabel);
		add(Box.createVerticalStrut(150));
		add(startButton);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(background != null) {
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	
}

//final class OutlineLabel extends JLabel {
//
//    private Color outlineColor = Color.BLACK;
//    private int outlineWidth = 5;
//
//    public OutlineLabel(String text) {
//        super(text);
//        setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.PLAIN, 40));
//        setForeground(Color.WHITE); // 内側の文字色
//        setHorizontalAlignment(CENTER);
//    }
//
//    public void setOutlineColor(Color color) {
//        this.outlineColor = color;
//    }
//
//    public void setOutlineWidth(int width) {
//        this.outlineWidth = width;
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g.create();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        String text = getText();
//        Font font = getFont();
//        FontMetrics fm = g2.getFontMetrics(font);
//
//        int x = (getWidth() - fm.stringWidth(text)) / 2;
//        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
//
//        g2.setFont(font);
//
//        // 縁取りを描画
//        g2.setColor(outlineColor);
//        for (int dx = -outlineWidth; dx <= outlineWidth; dx++) {
//            for (int dy = -outlineWidth; dy <= outlineWidth; dy++) {
//                if (dx != 0 || dy != 0) {
//                    g2.drawString(text, x + dx, y + dy);
//                }
//            }
//        }
//
//        // 内側の文字を描画
//        g2.setColor(getForeground());
//        g2.drawString(text, x, y);
//
//        g2.dispose();
//    }
//}
