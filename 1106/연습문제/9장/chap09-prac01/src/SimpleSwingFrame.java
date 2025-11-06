import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class SimpleSwingFrame extends JFrame {
	public SimpleSwingFrame() {
		super("Make a Window using Swing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 150);
		Container c = getContentPane();
		c.setBackground(Color.YELLOW);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new SimpleSwingFrame();
	}

}
