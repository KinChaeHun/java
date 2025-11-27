import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class nameDrawgemini extends JPanel {

    // ==========================================
    // 1. 설정 상수 (여기만 바꾸면 전체 적용됨)
    // ==========================================
    private static final int SCALE = 3;       // 확대 배율
    private static final int START_X = 100;   // 전체 시작 X 위치
    private static final int START_Y = 50;    // 전체 시작 Y 위치
    private static final int HUN_Y_OFFSET = -20; // '훈' 글자만 위로 올리는 정도
    private static final float LINE_THICKNESS = 5.0f; // 선 굵기

    // ==========================================
    // 2. 메인 그리기 로직
    // ==========================================
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        setupGraphics(g2); // 그래픽 품질 설정

        // 각 글자 그리기 (코드가 훨씬 깔끔해짐)
        drawKim(g2);
        drawChae(g2);
        drawHun(g2);
    }

    // 그래픽 품질 및 스타일 설정
    private void setupGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(LINE_THICKNESS));
    }

    // ==========================================
    // 3. 글자별 상세 구현
    // ==========================================
    
    private void drawKim(Graphics2D g2) {
        // ㄱ
        drawLine(g2, 10, 10, 40, 10); // 가로
        drawLine(g2, 40, 10, 40, 40); // 세로
        // ㅣ
        drawLine(g2, 50, 10, 50, 40);
        // ㅁ (사각형)
        drawRect(g2, 15, 50, 25, 25);
    }

    private void drawChae(Graphics2D g2) {
        int cx = 80; // '채' 시작 기준 X
        
        // ㅡ (두 개)
        drawLine(g2, cx + 10, 10, cx + 30, 10);
        drawLine(g2, cx + 5,  20, cx + 35, 20);

        // ㅅ (붙임)
        int centerX = cx + 20;
        drawLine(g2, centerX, 20, cx + 5,  50);
        drawLine(g2, centerX, 20, cx + 35, 50);

        // ㅐ
        int ax = cx + 45;
        drawLine(g2, ax,      10, ax,      50); // 세로 1
        drawLine(g2, ax + 10, 10, ax + 10, 50); // 세로 2
        drawLine(g2, ax,      30, ax + 10, 30); // 가로
    }

    private void drawHun(Graphics2D g2) {
        int hx = 160; // '훈' 시작 기준 X
        int centerX = hx + 20;
        
        // 훈은 전체적으로 위로 올림 (y좌표에 HUN_Y_OFFSET을 더해서 처리)
        
        // ㅎ 머리 (ㅡ 두 개)
        drawLineHun(g2, centerX - 10, 10, centerX + 10, 10);
        drawLineHun(g2, centerX - 15, 20, centerX + 15, 20);

        // ㅇ (팔각형) - y=38 기준
        drawOctagonHun(g2, centerX, 38, 12);

        // ㅜ
        int uY = 60;
        drawLineHun(g2, centerX - 20, uY, centerX + 20, uY); // 가로
        drawLineHun(g2, centerX, uY, centerX, uY + 20);      // 세로

        // ㄴ (받침) - 바짝 올림
        int nY = uY + 20; 
        int nX = centerX - 15;
        drawLineHun(g2, nX, nY, nX, nY + 20);           // 세로
        drawLineHun(g2, nX, nY + 20, nX + 35, nY + 20); // 가로
    }

    // ==========================================
    // 4. 헬퍼 메서드 (좌표 계산 자동화)
    // ==========================================

    // 일반 선 그리기 (자동 스케일링)
    private void drawLine(Graphics2D g2, int x1, int y1, int x2, int y2) {
        g2.drawLine(
            START_X + x1 * SCALE, START_Y + y1 * SCALE,
            START_X + x2 * SCALE, START_Y + y2 * SCALE
        );
    }

    // 사각형 그리기 (자동 스케일링)
    private void drawRect(Graphics2D g2, int x, int y, int w, int h) {
        g2.drawRect(
            START_X + x * SCALE, START_Y + y * SCALE,
            w * SCALE, h * SCALE
        );
    }

    // '훈' 전용 선 그리기 (Y축 오프셋 적용)
    private void drawLineHun(Graphics2D g2, int x1, int y1, int x2, int y2) {
        g2.drawLine(
            START_X + x1 * SCALE, START_Y + (y1 + HUN_Y_OFFSET) * SCALE,
            START_X + x2 * SCALE, START_Y + (y2 + HUN_Y_OFFSET) * SCALE
        );
    }

    // '훈' 전용 팔각형 그리기
    private void drawOctagonHun(Graphics2D g2, int cx, int cy, int r) {
        int realCx = START_X + cx * SCALE;
        int realCy = START_Y + (cy + HUN_Y_OFFSET) * SCALE;
        int realR = r * SCALE;

        int[] xPoints = new int[8];
        int[] yPoints = new int[8];
        
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(45 * i + 22.5);
            xPoints[i] = (int) (realCx + realR * Math.cos(angle));
            
            yPoints[i] = (int) (realCy + realR * Math.sin(angle));
        }
        g2.drawPolygon(xPoints, yPoints, 8);
    }

    // ==========================================
    // 5. 메인 실행
    // ==========================================
    public static void main(String[] args) {
        JFrame frame = new JFrame("김채훈 - Optimized Code");
        frame.add(new nameDrawgemini());
        frame.setSize(900, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}