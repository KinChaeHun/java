import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckBoxPracticeFrame extends JFrame {
	private JButton btn = new JButton("test button");
	
	public CheckBoxPracticeFrame() {
		super("CheckBox Practice Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		
		// 체크박스 컴포넌트 2개 생성
		JCheckBox a = new JCheckBox("버튼 비활성화");
		JCheckBox b = new JCheckBox("버튼 감추기");
		
		// 체크박스 컴포넌트들과 버튼을 컨텐트팬에 부착
		c.add(a);
		c.add(b);		
		c.add(btn);
		
		a.addItemListener(new ItemListener() { // 체크 박스 a가 선택/해제 되는 경우 리스너
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) // 선택 상태가 될 때
					btn.setEnabled(false);
				else
					btn.setEnabled(true);
			}
		});
		
		b.addItemListener(new ItemListener() { // 체크 박스 b가 선택/해제 되는 경우 리스너
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) // 선택 상태가 될 때
					btn.setVisible(false);
				else
					btn.setVisible(true);
			}
		});

		setSize(250,130);
		setVisible(true);
	}
	public static void main(String[] args) {
		new CheckBoxPracticeFrame();
	}
}
