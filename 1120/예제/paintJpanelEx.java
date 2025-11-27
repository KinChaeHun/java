import java.awt.*;
import javax.swing.*;

public class paintJpanelEx extends JFrame {
    private Mypanel panel = new Mypanel();

    public paintJpanelEx() {
        setTitle("JPanel의 paintComponent() 예제");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setSize(350, 300);
        setVisible(true);
    }

    class Mypanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int size = 60; // 정사각형 한 변 길이

            // 첫 번째 정사각형
            int x1 = 30, y1 = 30;
            g.drawRect(x1, y1, size, size);

            // 두 번째 정사각형 (첫 번째의 오른쪽 아래 꼭짓점에 접하도록)
            int x2 = x1 + size, y2 = y1 + size;
            g.drawRect(x2, y2, size, size);

            // 세 번째 정사각형 (두 번째의 오른쪽 아래 꼭짓점에 접하도록)
            int x3 = x2 + size, y3 = y2 + size;
            g.drawRect(x3, y3, size, size);
        }
    }

    public static void main(String[] args) {
        new paintJpanelEx();
    }
}