package jtdge.demo.ewt1;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SwingDemo1 {
	public static void main(String[] args) {
		new SwingDemo1();
	}
	
	public SwingDemo1() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		for(int i=0;i<5;i++) {
			panel.add(new JButton("button_"+(i+1)));
		}
		JButton inviButton = new JButton("button_invi");
		inviButton.setVisible(false);
		panel.add(inviButton);
		panel.add(new JTextField("This is a test or?"));
		frame.add(panel, BorderLayout.CENTER);
		
		frame.setSize(640, 480);
		frame.setVisible(true);
	}
}
