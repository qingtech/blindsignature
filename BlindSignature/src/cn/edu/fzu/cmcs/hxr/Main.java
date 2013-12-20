package cn.edu.fzu.cmcs.hxr;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Main extends JFrame {
	public static void main(String[] args){
		new Main();
		System.out.println("over!");
	}
	public Main() {
		setTitle("盲签名演示");
		this.setLocation(140, 140);
		this.setSize(500, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("用户                      ");
		panel.add(label);
		
		JButton button = new JButton("开始");
		panel.add(button);
		
		JButton button_1 = new JButton("重置");
		panel.add(button_1);
		
		JLabel label_1 = new JLabel("                      签名者");
		panel.add(label_1);
	}

}
