package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

public class OutlineLabel extends JLabel {

    private Color outlineColor = Color.BLACK;
    private int outlineWidth = 5;

    public OutlineLabel(String text) {
        super(text);
        setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.PLAIN, 40));
        setForeground(Color.WHITE); // 内側の文字色
        setHorizontalAlignment(CENTER);
    }

    public void setOutlineColor(Color color) {
        this.outlineColor = color;
    }

    public void setOutlineWidth(int width) {
        this.outlineWidth = width;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        String text = getText();
        Font font = getFont();
        FontMetrics fm = g2.getFontMetrics(font);

        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

        g2.setFont(font);

        // 縁取りを描画
        g2.setColor(outlineColor);
        for (int dx = -outlineWidth; dx <= outlineWidth; dx++) {
            for (int dy = -outlineWidth; dy <= outlineWidth; dy++) {
                if (dx != 0 || dy != 0) {
                    g2.drawString(text, x + dx, y + dy);
                }
            }
        }

        // 内側の文字を描画
        g2.setColor(getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
    }
}
