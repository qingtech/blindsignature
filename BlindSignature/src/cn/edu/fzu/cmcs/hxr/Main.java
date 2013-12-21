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
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {
	EllipticCurve ec = null;
	JLabel send1_label;
	public static void main(String[] args){
		new Main();
		System.out.println("over!");
	}
	public Main() {
		ec = new EllipticCurve();
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel upper_panel = new JPanel();
		getContentPane().add(upper_panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("用户                                   ");
		upper_panel.add(label);
		
		JButton start_button = new JButton("开始");
		start_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		upper_panel.add(start_button);
		
		JButton reset_button = new JButton("重置");
		upper_panel.add(reset_button);
		
		JLabel label_1 = new JLabel("                                签名者");
		upper_panel.add(label_1);
		
		JPanel bottom_panel = new JPanel();
		getContentPane().add(bottom_panel, BorderLayout.SOUTH);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText(new EllipticCurve().toString());
		bottom_panel.add(textPane);
		
		JPanel main_panel = new JPanel();
		getContentPane().add(main_panel, BorderLayout.CENTER);
		main_panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel left_main_panel = new JPanel();
		main_panel.add(left_main_panel);
		left_main_panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel blind_panel = new JPanel();
		left_main_panel.add(blind_panel);
		blind_panel.setLayout(new GridLayout(6, 1, 0, 0));
		
		JPanel message_panel = new JPanel();
		blind_panel.add(message_panel);
		message_panel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JLabel message_label = new JLabel("选取消息m:");
		message_panel.add(message_label);
		
		JComboBox message_comboBox = new JComboBox();
		String[] strArr = new String[ec.points.length-1];
		for(int i=0;i<ec.points.length-1;i++){
			strArr[i] = ec.points[i].toString();
		}
		message_comboBox.setModel(new DefaultComboBoxModel(strArr));
		message_panel.add(message_comboBox);
		String str = "随机盲因子r = ?\n";
		str += "C1 = rm = (?,?)\n";
		str += "C2 = rG+m = (?,?)\n";
		
		JLabel r_label = new JLabel("随机盲因子r = ?");
		blind_panel.add(r_label);
		
		JLabel c1_label = new JLabel("C1 = rm = (?,?)");
		blind_panel.add(c1_label);
		
		JLabel c2_label = new JLabel("C2 = rG+m = (?,?)");
		blind_panel.add(c2_label);
		
		JButton button_2 = new JButton("盲化");
		blind_panel.add(button_2);
		
		JPanel deblind_panel = new JPanel();
		left_main_panel.add(deblind_panel);
		deblind_panel.setLayout(new GridLayout(5, 1, 0, 0));
		
		JLabel lblNewLabel_5 = new JLabel("");
		deblind_panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("");
		deblind_panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("");
		deblind_panel.add(lblNewLabel_7);
		
		JLabel s_label = new JLabel("S = D2 - Ri*r = (?,?)");
		deblind_panel.add(s_label);
		
		JButton deblind_button = new JButton("解盲");
		deblind_panel.add(deblind_button);
		
		JPanel verify_panel = new JPanel();
		left_main_panel.add(verify_panel);
		verify_panel.setLayout(new GridLayout(5, 1, 0, 0));
		
		JLabel mm_label = new JLabel("D1*r^-1 - S = (?,?)");
		verify_panel.add(mm_label);
		
		JLabel m_label = new JLabel("m = (?,?)");
		verify_panel.add(m_label);
		
		JLabel result_label = new JLabel("签名结果：未知");
		verify_panel.add(result_label);
		
		JButton verify_botton = new JButton("验证");
		verify_panel.add(verify_botton);
		
		JPanel center_main_panel = new JPanel();
		main_panel.add(center_main_panel);
		center_main_panel.setLayout(new GridLayout(10, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("");
		center_main_panel.add(lblNewLabel);
		
		send1_label = new JLabel("        发送C1(?,?),C2(?,?)");
		center_main_panel.add(send1_label);
		
		JLabel lblNewLabel_1 = new JLabel("        ----------------------------------->");
		center_main_panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		center_main_panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		center_main_panel.add(lblNewLabel_3);
		
		JLabel send2_label = new JLabel("        发送 Ri = ?, Ri = (?,?)");
		center_main_panel.add(send2_label);
		
		JLabel send3_label = new JLabel("        D1 = (?,?), D2 = (?,?)");
		center_main_panel.add(send3_label);
		
		JLabel lblNewLabel_4 = new JLabel("        <-----------------------------------");
		center_main_panel.add(lblNewLabel_4);
		
		JPanel right_main_panel = new JPanel();
		main_panel.add(right_main_panel);
		right_main_panel.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel time_label = new JLabel("时段i = ?");
		right_main_panel.add(time_label);
		
		JLabel private_key_label = new JLabel("私钥Si = ?");
		right_main_panel.add(private_key_label);
		
		JLabel public_key_label = new JLabel("公钥Ri = Si*G = (?,?)");
		right_main_panel.add(public_key_label);
		
		JLabel d1_label = new JLabel("D1 = (Si+1)C1 = (?,?)");
		right_main_panel.add(d1_label);
		
		JLabel d2_label = new JLabel("D2 = Si*C2 = (?,?)");
		right_main_panel.add(d2_label);
		
		JButton sign_button = new JButton("盲签名");
		right_main_panel.add(sign_button);
		
		setTitle("盲签名演示");
		this.setLocation(140, 140);
		this.setSize(700, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
