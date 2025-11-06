import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SwingCalculator extends JFrame {
	public SwingCalculator() {
		super("자바 스윙 계산기");
		setSize(300, 300);
		Container c = getContentPane();
		c.add(new SouthPanel(), BorderLayout.SOUTH);
		c.add(new CenterPanel(), BorderLayout.CENTER);
		c.add(new NorthPanel(), BorderLayout.NORTH);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new SwingCalculator();
	}
}

class SouthPanel extends JPanel {
	public SouthPanel() {
		setBackground(Color.YELLOW);
		setOpaque(true); // 배경색을 불투명하게 출력 
		setLayout(new FlowLayout(FlowLayout.LEFT)); // JPanel이 디폴트로 FlowLayout이지만, 확실히 하기 위해
		add(new JLabel("계산 결과"));
		add(new JTextField(20));
	}
}

class CenterPanel extends JPanel {
	private JButton divButton = new JButton("/");
	private JButton mulButton = new JButton("x");
	private JButton minusButton = new JButton("-");
	private JButton plusButton = new JButton("+");
	private JButton moduloButton = new JButton("%");
	private JButton calcButton = new JButton("=");
	
	private JButton opButtons [] = { divButton, mulButton, minusButton, plusButton, moduloButton };
	private JButton buttons [] = { 
			new JButton("C"), new JButton("UN"), new JButton("BK"), divButton,
			new JButton("7"), new JButton("8"), new JButton("9"), mulButton,
			new JButton("4"), new JButton("5"), new JButton("6"), minusButton,			
			new JButton("1"), new JButton("2"), new JButton("3"), plusButton,
			new JButton("0"), new JButton("."), calcButton, moduloButton 
	};
	
	public CenterPanel() {
		setBackground(Color.WHITE);
		setOpaque(true); // 배경색을 불투명하게 출력		
		setLayout(new GridLayout(5,4,5,5)); // GridLayout 배치관리자
		// 모든 버튼을 이 패널에 그리드 배치 방식으로 삽입
		for(int i=0; i<buttons.length; i++) {
			add(buttons[i]);
		}
		calcButton.setBackground(Color.CYAN); // "=" 버튼 만 배경색 CYAN으로 칠하기
	}
}

class NorthPanel extends JPanel {
	public NorthPanel() {
		setBackground(Color.LIGHT_GRAY); // 패널의 배경색을 밝은 회색으로 지정
		setOpaque(true); // 배경색을 불투명하게 출력
		setLayout(new FlowLayout()); // JPanel이 디폴트로 FlowLayout이지만, 확실히 하기 위해		
		add(new JLabel("수식"));
		add(new JTextField(20));
	}
}
