import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SwingCalculatorIm extends JFrame {
	private JTextField resultField = new JTextField(20); // 계산 결과를 출력하는 창
	private JTextField inputField = new JTextField(20); // 계산 식이 입력되는 창
	private int status = 0; // 0: 현재 inputField에 계산식이 처음으로 입력되어야 할 상태, 1: 입력 중인 상태
	
	public SwingCalculatorIm() {
		super("자바 스윙 계산기");
		setSize(300, 300);
		Container c = getContentPane();
		c.add(new SouthPanel(), BorderLayout.SOUTH);
		c.add(new CenterPanel(), BorderLayout.CENTER);
		c.add(new NorthPanel(), BorderLayout.NORTH);
		setVisible(true);
	}
	
	class SouthPanel extends JPanel {
		public SouthPanel() {
			setBackground(Color.YELLOW);
			setOpaque(true); // 배경색을 불투명하게 출력 
			setLayout(new FlowLayout(FlowLayout.LEFT)); // JPanel이 디폴트로 FlowLayout이지만, 확실히 하기 위해
			add(new JLabel("계산 결과"));
			add(resultField);
		}
	}

	class MyAction implements ActionListener { // 계산기의 버튼 전체에 대해 클릭될 때 실행되는 이벤트 리스너
		public void actionPerformed(ActionEvent e) {
			if(status == 0) { // 계산식이 처음으로 입력되어야 하는 상태라면,
				status = 1; // 입력 중인 상태로 변경
				inputField.setText(""); // 현재 입력된 내용 지우기
				resultField.setText(""); // 현재 출력된 결과 지우기
			}
			
			String cmd = e.getActionCommand(); // 입력된 버튼의 문자열
			switch(cmd) {
			case "C": // 입력된 내용을 모두 지우기
				inputField.setText("");
				break;
			case "UN": break; // 현재로서는 작업 없음
			case "BK": // 입력된 내용 중 마지막 문자 지우기
				String s = inputField.getText();
				if(s.length() == 0) 
					break;
				inputField.setText(s.substring(0, s.length()-1));
				break;
			case "=": // 입력된 계산식 계산
				calculate(); // 계산 후 출력 창에 결과 출력
				status = 0; // init 상태로 변경. 새로 계산식이 입력되어야하는 상태임을 표시
				break;
			default: // 그외 입력의 경우 입력 창에 덧붙여 출력
					inputField.setText(inputField.getText() + cmd);			
			}
		}
		
		public void calculate() {
			String expr = inputField.getText(); // 입력 창에 입력된 계산식 텍스트 알아내기
			StringTokenizer st = new StringTokenizer(expr, "+-x/%"); // 연산자로 토큰나이징
			if(st.countTokens() != 2) { // 토큰이 2개가 아니면 수식 오류
				resultField.setText("한 개의 연산만 할 수 있음");
				return; // 수식 입력 오류. 계산하지 않음
			}
			
			double leftValue = 0; // 계산식의 왼쪽 수
			double rightValue = 0; // 계산식의 오른쪽 수
			try {
				leftValue = Double.parseDouble(st.nextToken().trim());
				rightValue = Double.parseDouble(st.nextToken().trim());
			}
			catch(NumberFormatException e) { // double 타입 혹은 정수 타입의 수가 입력되지 않은 예외 발생
				resultField.setText("연산식 오류");
				return;
			}

			// 계산 실행
			double res = 0;
			if(expr.contains("+"))
				res = leftValue + rightValue;
			else if(expr.contains("-"))
				res = leftValue - rightValue;
			else if(expr.contains("x"))			
				res = leftValue * rightValue;
			else if(expr.contains("%")) {			
				if(rightValue == 0) {
					resultField.setText("0으로 나눌 수 없음");
					return;
				}
				res = (double)leftValue % rightValue;
			}
			else if(expr.contains("/")) {			
				if(rightValue == 0) {
					resultField.setText("0으로 나눌 수 없음");
					return;
				}
				res = (double)leftValue / rightValue;
			}
			else {
				resultField.setText("연산자 오류");
			}
			
			resultField.setText(Double.toString(res));
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
			MyAction act = new MyAction(); // Action 이벤트 리스너 객체 생성
			setBackground(Color.WHITE); // 패널의 배경색을 흰색으로 지정
			setOpaque(true); // 배경색을 불투명하게 출력
			setLayout(new GridLayout(5,4,5,5)); // GridLayout 배치관리자
			// 모든 버튼을 이 패널에 그리드 배치 방식으로 삽입
			for(int i=0; i<buttons.length; i++) {
				add(buttons[i]);
				buttons[i].addActionListener(act); // Action 이벤트 리스너 등록
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
			add(inputField);
		}
	}
	
	public static void main(String[] args) {
		new SwingCalculatorIm();
	}
}