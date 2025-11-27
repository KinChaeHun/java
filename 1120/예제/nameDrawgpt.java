import java.awt.*;
import javax.swing.*;

public class nameDrawgpt extends JFrame {

    public nameDrawgpt() {
        setTitle("김채훈 그리기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 400);
        add(new NamePanel());
        setVisible(true);
    }

    class NamePanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);

            // 김
            g.drawLine(30, 70, 200, 70);   // ㄱ 가로
            g.drawLine(200, 70, 200, 250); // ㄱ 세로
            g.drawLine(240, 70, 240, 250); // ㅣ
            g.drawRect(280, 120, 150, 150); // ㅁ

            // 채
            g.drawLine(370, 70, 520, 70);  // ㅊ 윗선
            g.drawLine(400, 70, 460, 180); // ㅊ 대각
            g.drawLine(520, 70, 460, 180); // ㅊ 대각

            g.drawLine(560, 70, 560, 250); // ㅐ - ㅏ 세로
            g.drawLine(560, 160, 620, 160); // ㅐ - ㅏ 가로
            g.drawLine(650, 70, 650, 250);  // ㅐ - ㅣ

            // 훈
            g.drawLine(700, 60, 740, 60);   // 위 짧은 가로
            g.drawLine(680, 100, 780, 100); // 위 긴 가로

            int cx = 730, cy = 170, r = 40;
            for (int i = 0; i < 8; i++) {
                double a1 = Math.toRadians(45 * i);
                double a2 = Math.toRadians(45 * (i + 1));
                int x1 = (int)(cx + r * Math.cos(a1));
                int y1 = (int)(cy + r * Math.sin(a1));
                int x2 = (int)(cx + r * Math.cos(a2));
                int y2 = (int)(cy + r * Math.sin(a2));
                g.drawLine(x1, y1, x2, y2);
            }

            g.drawLine(780, 250, 880, 250); // 받침 윗가로
            g.drawLine(780, 250, 780, 300); // 받침 세로
            g.drawLine(780, 300, 840, 300); // 받침 가로
        }
    }

    public static void main(String[] args) {
        new nameDrawgpt();
    }
}
