import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TenLabelsFrame extends JFrame {
	private JLabel [] gameLabels = new JLabel [10]; // 0~9까지의 레이블 배치
	private int nextPressed = 0;
	public TenLabelsFrame() {
		super("Ten 레이블 클릭");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		
		setSize(300,300);
		setVisible(true);
		
		for(int i=0; i<gameLabels.length; i++) {
			gameLabels[i] = new JLabel(Integer.toString(i)); // 레이블 생성
			gameLabels[i].setFont(new Font("Gothic", Font.PLAIN, 15));
			gameLabels[i].setForeground(Color.MAGENTA);
			c.add(gameLabels[i]); // 레이블을 콘텐트팬에 부탁
			
			// 0,1,2 순으로 삽입하면, 2,1,0 순으로 화면에 출력된다. 
			// 그러르모 2와 1이 같은 위치에 겹치게 되더라도 1이 먼저 보이기 되므로
			// 순서대로 클릭할 수 있다.
			
			gameLabels[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					JLabel la = (JLabel)e.getSource();
					if(Integer.parseInt(la.getText()) == nextPressed) { 
						nextPressed++;
						if(nextPressed == 10) {
							nextPressed = 0;
							configure();
						}
						else 
							la.setVisible(false);
					}
				}
			});
		}
		configure(); // 콘텐트 팬c에 1,2,3.. 레이블 배치
	}
	
	private void configure() {
		Container c = getContentPane();
		for(int i=0; i<gameLabels.length; i++) {
			gameLabels[i].setSize(15, 15); // 크기를 먼저 지정하여야 뒤에서 getWidth(), getHeight()의 값이 정확해짐
			
			int xBound = c.getWidth() - gameLabels[i].getWidth(); // 레이블의 폭 만큼 감소
			int yBound = c.getHeight() - gameLabels[i].getHeight(); // 레이블의 높이 만큼 감소				
			int x = (int)(Math.random()*xBound);
			int y = (int)(Math.random()*yBound);				
			gameLabels[i].setLocation(x, y);
			gameLabels[i].setVisible(true);
		}
	}
	static public void main(String [] args) {
		new TenLabelsFrame();
	}
}
