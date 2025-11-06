import java.awt.*;
import javax.swing.*;

public class Plate4x4Frame extends JFrame {
	private Color [] color = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
							Color.CYAN, Color.BLUE, Color.MAGENTA, Color.GRAY,
							Color.PINK, Color.LIGHT_GRAY, Color.WHITE, Color.DARK_GRAY,
							Color.BLACK, Color.ORANGE, Color.BLUE,Color.MAGENTA}; 

	public Plate4x4Frame() {
		super("4x4 Color 프레임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane(); // 컨텐트팬 알아내기
		c.setLayout(new GridLayout(4, 4)); // 컨텐트팬에 4x4 격자의 GridLayout 배치관리자 지정
		
		JLabel [] label = new JLabel [16]; // 16개의 JLabel 객체에 대한 레퍼런스 배열 생성
		for(int i=0; i<label.length; i++) {
			label[i] = new JLabel(Integer.toString(i)); // JLabel 객체 생성
			label[i].setOpaque(true); // JLabel 객체의 배경색이 불투명하게 출력되도록 지정
			label[i].setBackground(color[i]); // JLabel 객체에 배경색 설정
			c.add(label[i]); // JLabel 객체를 컨텐트팬에 부착
		}
		setSize(500,200);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Plate4x4Frame();
	}

}
