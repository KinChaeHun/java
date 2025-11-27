import java.awt.*;
import javax.swing.*;

public class nameDrawclaude extends JPanel {
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLACK);
        
        // 김 (金)
        drawKim(g2d, 50, 100);
        
        // 채 (采)
        drawChae(g2d, 200, 100);
        
        // 훈 (勳) - ㅎ의 ㅇ을 팔각형으로
        drawHoon(g2d, 350, 100);
    }
    
    private void drawKim(Graphics2D g, int x, int y) {
        // ㄱ
        g.drawLine(x, y, x + 40, y);
        g.drawLine(x + 40, y, x + 40, y + 40);
        
        // ㅣ
        g.drawLine(x + 60, y, x + 60, y + 80);
        
        // ㅁ
        g.drawLine(x + 10, y + 50, x + 50, y + 50);
        g.drawLine(x + 50, y + 50, x + 50, y + 90);
        g.drawLine(x + 50, y + 90, x + 10, y + 90);
        g.drawLine(x + 10, y + 90, x + 10, y + 50);
    }
    
    private void drawChae(Graphics2D g, int x, int y) {
        // ㅊ
        g.drawLine(x, y + 10, x + 50, y + 10);
        g.drawLine(x + 25, y, x + 25, y + 30);
        g.drawLine(x, y + 30, x + 50, y + 30);
        
        // ㅐ
        g.drawLine(x + 15, y + 50, x + 15, y + 90);
        g.drawLine(x + 35, y + 50, x + 35, y + 90);
        g.drawLine(x + 35, y + 70, x + 50, y + 70);
    }
    
    private void drawHoon(Graphics2D g, int x, int y) {
        // ㅎ의 위 부분
        g.drawLine(x, y, x + 50, y);
        g.drawLine(x, y, x, y + 20);
        g.drawLine(x + 50, y, x + 50, y + 20);
        
        // ㅎ의 ㅇ (팔각형)
        int cx = x + 25;
        int cy = y + 40;
        int radius = 15;
        int[] xPoints = new int[8];
        int[] yPoints = new int[8];
        
        for (int i = 0; i < 8; i++) {
            double angle = Math.PI / 4 * i;
            xPoints[i] = (int) (cx + radius * Math.cos(angle));
            yPoints[i] = (int) (cy + radius * Math.sin(angle));
        }
        
        for (int i = 0; i < 8; i++) {
            g.drawLine(xPoints[i], yPoints[i], xPoints[(i + 1) % 8], yPoints[(i + 1) % 8]);
        }
        
        // ㅜ
        g.drawLine(x, y + 70, x + 50, y + 70);
        g.drawLine(x + 25, y + 70, x + 25, y + 90);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("nameDrawclaude");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new nameDrawclaude());
            frame.setSize(600, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}