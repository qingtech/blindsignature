package cn.edu.fzu.cmcs.hxr;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class Main extends JFrame {
	EllipticCurve ec = null;
	public static void main(String[] args){
		new Main();
		System.out.println("over!");
	}
	public Main() {
		ec = new EllipticCurve();
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel upper_panel = new JPanel();
		getContentPane().add(upper_panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("用户                      ");
		upper_panel.add(label);
		
		JButton button = new JButton("开始");
		upper_panel.add(button);
		
		JButton button_1 = new JButton("重置");
		upper_panel.add(button_1);
		
		JLabel label_1 = new JLabel("                      签名者");
		upper_panel.add(label_1);
		
		JPanel bottom_panel = new JPanel();
		getContentPane().add(bottom_panel, BorderLayout.SOUTH);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText(new EllipticCurve().toString());
		bottom_panel.add(textPane);
		
		JPanel main_panel = new JPanel();
		getContentPane().add(main_panel, BorderLayout.CENTER);
		main_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel left_main_panel = new JPanel();
		main_panel.add(left_main_panel, BorderLayout.WEST);
		left_main_panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel blind_panel = new JPanel();
		left_main_panel.add(blind_panel);
		blind_panel.setLayout(new GridLayout(5, 0, 0, 0));
		
		JPanel message_panel = new JPanel();
		blind_panel.add(message_panel);
		
		JLabel message_label = new JLabel("选取消息m:");
		message_panel.add(message_label);
		
		JComboBox message_comboBox = new JComboBox();
		String[] str = new String[ec.points.length];
		for(int i=0;i<ec.points.length;i++){
			str[i] = ec.points[i].toString();
		}
		message_comboBox.setModel(new DefaultComboBoxModel(str));
		message_panel.add(message_comboBox);
		
		JPanel deblind_panel = new JPanel();
		left_main_panel.add(deblind_panel);
		
		JPanel verify_panel = new JPanel();
		left_main_panel.add(verify_panel);
		
		JPanel center_main_panel = new JPanel();
		main_panel.add(center_main_panel, BorderLayout.CENTER);
		
		JPanel right_main_panel = new JPanel();
		main_panel.add(right_main_panel, BorderLayout.NORTH);
		
		setTitle("盲签名演示");
		this.setLocation(140, 140);
		this.setSize(700, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
