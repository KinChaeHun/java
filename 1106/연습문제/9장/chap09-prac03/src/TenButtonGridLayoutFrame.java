import java.awt.*;
import javax.swing.*;

public class TenButtonGridLayoutFrame extends JFrame {
	public TenButtonGridLayoutFrame() {
		super("GridLayout으로 10개의 버튼을 배치한 프레임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane(); // 컨텐트팬 알아내기
		c.setLayout(new GridLayout(1, 10)); // 컨텐트팬에 GridLayout 배치 관리자 지정
		
		JButton [] btn = new JButton [10]; // 10의 버튼 레퍼런스를 가진 배열 생성
		for(int i=0; i<btn.length; i++) {
			btn[i] = new JButton(Integer.toString(i)); // 버튼 생성. 배열에 저장
			c.add(btn[i]); // 버튼을 컨텐트팬에 부착. 버튼이 출력됨
		}
		setSize(500,150);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new TenButtonGridLayoutFrame();
	}
}
