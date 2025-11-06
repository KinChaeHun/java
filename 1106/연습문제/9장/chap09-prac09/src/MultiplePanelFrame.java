import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiplePanelFrame extends JFrame {
	public MultiplePanelFrame() {
		setTitle("랜덤한 별을 가진 프레임");
		setSize(300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.add(new NorthPanel(), BorderLayout.NORTH);
		c.add(new CenterPanel(), BorderLayout.CENTER);
		c.add(new SouthPanel(), BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	class NorthPanel extends JPanel {
		public NorthPanel() {
			setBackground(Color.LIGHT_GRAY);
			add(new JLabel("별 개수"));
			add(new JTextField(10));
			add(new JButton("별 만들기"));
		}
	}
	
	class CenterPanel extends JPanel {
		private JLabel starList[] = new JLabel [15];
		public CenterPanel() {
			setBackground(Color.WHITE);
			setLayout(null);
			for(int i=0; i<starList.length; i++) {
				JLabel star = new JLabel("*");
				starList[i] = star;
				star.setSize(10,10);
				int x = (int)(Math.random()*280);
				int y = (int)(Math.random()*180);
				star.setLocation(x, y);
				star.setForeground(Color.RED);
				add(star);
			}
			
			JButton refreshBtn = new JButton("Refresh");
			refreshBtn.setSize(80, 20);
			refreshBtn.setLocation(10, 10);
			add(refreshBtn);
		}		
	}
	
	class SouthPanel extends JPanel {
		public SouthPanel() {
			setBackground(Color.YELLOW);
			add(new JButton("Exit"));			
		}
	}
	
	public static void main(String[] args) {
		new MultiplePanelFrame();
	}

}
