package cn.edu.fzu.cmcs.hxr;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Main extends JFrame {
	EllipticCurve ec = null;
	Signer signer = null;
	User user = null;
	// 开始，重置
	JButton start_button = new JButton("开始");
	JButton reset_button = new JButton("重置");
	// 盲化
	JTextArea original_message_textArea;

	JLabel hash_value_label;

	JLabel message_point_label;

	JLabel r_label;

	JLabel c1_label;

	JLabel c2_label;

	JButton blind_button;

	// 解盲

	JLabel s_label;

	JButton deblind_button;

	JLabel mm_label;

	JLabel m_label;

	JLabel result_label;

	JButton verify_botton;

	// 中间发送过程
	JLabel send1_label;

	JLabel send_to_signer_porcess_label;

	JLabel send2_label;

	JLabel send3_label;

	JLabel send_to_user_porcess_label;

	// 签名
	JLabel time_label;

	JLabel private_key_label;

	JLabel public_key_label;

	JLabel d1_label;

	JLabel d2_label;

	JButton sign_button;
	private JComboBox ec_comboBox;
	private JButton randomec_button;
	private JPanel panel_1;
	private JComboBox message_from_comboBox;
	private JButton choose_file_button;
	private JScrollPane scrollPane_1;
	private JTextArea message_file;

	public static void main(String[] args) {
		new Main();
		// System.out.println("over!");
	}

	public Main() {

		user = new User(0);
		signer = user.getSigner();
		ec = user.getEc();

		getContentPane().setLayout(new BorderLayout(0, 0));
		// 上部panel，用户label，开始，重置，签名者label
		JPanel upper_panel = new JPanel();
		getContentPane().add(upper_panel, BorderLayout.NORTH);

		JLabel label = new JLabel("用户                                   ");
		upper_panel.add(label);

		start_button = new JButton("开始");
		start_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.this.start();
			}
		});
		// upper_panel.add(start_button);

		reset_button = new JButton("重置");
		reset_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.this.reset();
			}
		});
		upper_panel.add(reset_button);

		JLabel label_1 = new JLabel("                                签名者");
		upper_panel.add(label_1);
		// 底部panel,椭圆曲线信息
		JPanel bottom_panel = new JPanel();
		getContentPane().add(bottom_panel, BorderLayout.SOUTH);

		ec_comboBox = new JComboBox();
		ec_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = ec_comboBox.getSelectedIndex();

				user = new User(index);
				signer = user.getSigner();
				ec = user.getEc();
				Main.this.original_message_changed();
			}
		});
		String ecs[] = new String[9];
		for (int i = 0; i < 9; i++) {
			ecs[i] = new EllipticCurve(i).toString();
		}
		ec_comboBox.setModel(new DefaultComboBoxModel(ecs));
		bottom_panel.add(ec_comboBox);

		randomec_button = new JButton("随机选择");
		randomec_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = (int) (9 * Math.random());
				ec_comboBox.setSelectedIndex(index);

				user = new User(index);
				signer = user.getSigner();
				ec = user.getEc();
				Main.this.original_message_changed();
			}
		});
		bottom_panel.add(randomec_button);

		// 主panel
		JPanel main_panel = new JPanel();
		getContentPane().add(main_panel, BorderLayout.CENTER);
		main_panel.setLayout(new GridLayout(1, 3, 0, 0));
		// 左主panel，用户
		JPanel left_main_panel = new JPanel();
		main_panel.add(left_main_panel);
		left_main_panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		panel_1 = new JPanel();
		left_main_panel.add(panel_1);
				panel_1.setLayout(new GridLayout(5, 1, 0, 0));
				
				message_from_comboBox = new JComboBox();
				message_from_comboBox.setModel(new DefaultComboBoxModel(new String[] {"字符串", "文件"}));
				panel_1.add(message_from_comboBox);
				
				choose_file_button = new JButton("选择文件");
				choose_file_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//成员变量

						JFileChooser jfc = new JFileChooser();
						//点击按钮

						int n = jfc.showOpenDialog(null);

						String filename = jfc.getSelectedFile().toString();
						
						System.out.println(filename);
						message_file.setText(filename);

						if (n == JFileChooser.APPROVE_OPTION) {

						          //选择文件后操作

						}
					}
				});
				panel_1.add(choose_file_button);
				
				message_file = new JTextArea();
				message_file.setEditable(false);
				message_file.setLineWrap(true);
				message_file.setEnabled(true);
				
				scrollPane_1 = new JScrollPane(message_file);
				panel_1.add(scrollPane_1);
		
				JLabel original_message_label = new JLabel("请输入消息：");
				panel_1.add(original_message_label);
				
						original_message_textArea = new JTextArea();
						original_message_textArea.setLineWrap(true);
						original_message_textArea.getDocument().addDocumentListener(
								new DocumentListener() {

									@Override
									public void changedUpdate(DocumentEvent arg0) {
										// TODO Auto-generated method stub
										Main.this.original_message_changed();
									}

									@Override
									public void insertUpdate(DocumentEvent arg0) {
										// TODO Auto-generated method stub
										Main.this.original_message_changed();
									}

									@Override
									public void removeUpdate(DocumentEvent arg0) {
										// TODO Auto-generated method stub
										Main.this.original_message_changed();
									}

								});
						// blind_panel.add(original_message_textArea);

						JScrollPane scrollPane = new JScrollPane(original_message_textArea);
						panel_1.add(scrollPane);
				
						JPanel blind_panel = new JPanel();
						left_main_panel.add(blind_panel);
						
								// 盲化
								blind_panel.setLayout(new GridLayout(8, 1, 0, 0));
														
																hash_value_label = new JLabel("消息哈希值：？");
																blind_panel.add(hash_value_label);
																
																		message_point_label = new JLabel("消息曲线点(?,?)");
																		blind_panel.add(message_point_label);
																		
																				r_label = new JLabel("随机盲因子r = ?");
																				blind_panel.add(r_label);
																				
																						c1_label = new JLabel("C1 = rm = (?,?)");
																						blind_panel.add(c1_label);
																						
																								c2_label = new JLabel("C2 = rG+m = (?,?)");
																								blind_panel.add(c2_label);
																								
																										blind_button = new JButton("盲化");
																										blind_button.addActionListener(new ActionListener() {
																											public void actionPerformed(ActionEvent arg0) {
																												Main.this.blind();
																											}
																										});
																										blind_panel.add(blind_button);
		
				JPanel panel = new JPanel();
				left_main_panel.add(panel);
				
						// 解盲
						JPanel deblind_panel = new JPanel();
						panel.add(deblind_panel);
						deblind_panel.setLayout(new GridLayout(5, 1, 0, 0));
						
								s_label = new JLabel("S = D2 - Ri*r = (?,?)");
								deblind_panel.add(s_label);
								
										deblind_button = new JButton("解盲");
										deblind_button.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												Main.this.deblind();
											}
										});
										deblind_panel.add(deblind_button);
										
												JPanel verify_panel = new JPanel();
												panel.add(verify_panel);
												verify_panel.setLayout(new GridLayout(5, 1, 0, 0));
												
														mm_label = new JLabel("D1*r^-1 - S = (?,?)");
														verify_panel.add(mm_label);
														
																m_label = new JLabel("m = (?,?)");
																verify_panel.add(m_label);
																
																		result_label = new JLabel("签名结果：未知");
																		verify_panel.add(result_label);
																		
																				verify_botton = new JButton("验证");
																				verify_botton.addActionListener(new ActionListener() {
																					public void actionPerformed(ActionEvent e) {
																						Main.this.verify();
																					}
																				});
																				verify_panel.add(verify_botton);

		// 中主panel,消息传送过程
		JPanel center_main_panel = new JPanel();
		main_panel.add(center_main_panel);
		center_main_panel.setLayout(new GridLayout(10, 1, 0, 0));

		JLabel lblNewLabel = new JLabel("");
		center_main_panel.add(lblNewLabel);

		send1_label = new JLabel("        发送C1(?,?),C2(?,?)");
		center_main_panel.add(send1_label);

		send_to_signer_porcess_label = new JLabel(
				"        ----------------------------------->");
		center_main_panel.add(send_to_signer_porcess_label);

		JLabel lblNewLabel_2 = new JLabel("");
		center_main_panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("");
		center_main_panel.add(lblNewLabel_3);

		send2_label = new JLabel("        发送 Ri = ?, Ri = (?,?)");
		center_main_panel.add(send2_label);

		send3_label = new JLabel("        D1 = (?,?), D2 = (?,?)");
		center_main_panel.add(send3_label);

		send_to_user_porcess_label = new JLabel(
				"        <-----------------------------------");
		center_main_panel.add(send_to_user_porcess_label);

		// 右主panel，签名者
		JPanel right_main_panel = new JPanel();
		main_panel.add(right_main_panel);
		right_main_panel.setLayout(new GridLayout(7, 1, 0, 0));

		time_label = new JLabel("时段i = ?");
		right_main_panel.add(time_label);

		private_key_label = new JLabel("私钥Si = ?");
		right_main_panel.add(private_key_label);

		public_key_label = new JLabel("公钥Ri = Si*G = (?,?)");
		right_main_panel.add(public_key_label);

		d1_label = new JLabel("D1 = (Si+1)C1 = (?,?)");
		right_main_panel.add(d1_label);

		d2_label = new JLabel("D2 = Si*C2 = (?,?)");
		right_main_panel.add(d2_label);

		sign_button = new JButton("盲签名");
		sign_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.this.sign();
			}
		});
		right_main_panel.add(sign_button);
		this.reset();
		setTitle("盲签名演示");
		this.setLocation(350, 50);
		this.setSize(700, 650);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void start() {
		// 盲化
		hash_value_label.setText("消息哈希值：1");

		message_point_label.setText("消息曲线点(2,3)");

		r_label.setText("随机盲因子r = 4");

		c1_label.setText("C1 = rm = (4,5)");

		c2_label.setText("C2 = rG+m = (5,5)");

		blind_button.setEnabled(false);

		// 解盲

		s_label.setText("S = D2 - Ri*r = (3,3)");

		deblind_button.setEnabled(true);

		mm_label.setText("D1*r^-1 - S = (3,3)");

		m_label.setText("m = (2,2)");

		result_label.setText("签名结果：sss");

		verify_botton.setEnabled(true);

		// 中间发送过程
		send1_label.setText("        发送C1(2,2),C2(2,2)");

		send_to_signer_porcess_label
				.setText("        ----------------------------------->");

		send2_label.setText("        发送 Ri = 2, Ri = (2,2)");

		send3_label.setText("        D1 = (2,2), D2 = (2,1)");

		send_to_user_porcess_label
				.setText("        <-----------------------------------");

		// 签名
		time_label.setText("时段i = 2");

		private_key_label.setText("私钥Si = 4");

		public_key_label.setText("公钥Ri = Si*G = (4,3)");

		d1_label.setText("D1 = (Si+1)C1 = (3,3)");

		d2_label.setText("D2 = Si*C2 = (3,3)");

		sign_button.setEnabled(true);
	}

	public void reset() {
		// 盲化
		original_message_textArea.setEnabled(true);

		original_message_textArea.setText("");

		hash_value_label.setText("消息哈希值：？");

		message_point_label.setText("消息曲线点m(?,?)");

		r_label.setText("随机盲因子r = ?");

		c1_label.setText("C1 = rm = (?,?)");

		c2_label.setText("C2 = rG+m = (?,?)");

		blind_button.setEnabled(false);

		// 解盲

		s_label.setText("S = D2 - Ri*r = (?,?)");

		deblind_button.setEnabled(false);

		mm_label.setText("D1*r^-1 - S = (?,?)");

		m_label.setText("m = (?,?)");

		result_label.setText("签名结果：未知");

		verify_botton.setEnabled(false);

		// 中间发送过程
		send1_label.setText("        发送C1(?,?),C2(?,?)");

		send_to_signer_porcess_label
				.setText("------------------------------------------->");

		send2_label.setText("        发送 i = ?, Ri = (?,?)");

		send3_label.setText("        D1 = (?,?), D2 = (?,?)");

		send_to_user_porcess_label
				.setText("<------------------------------------------");

		// 签名
		time_label.setText("时段i = ?");

		private_key_label.setText("私钥Si = ?");

		public_key_label.setText("公钥Ri = Si*G = (?,?)");

		d1_label.setText("D1 = (Si+1)C1 = (?,?)");

		d2_label.setText("D2 = Si*C2 = (?,?)");

		sign_button.setEnabled(false);

		// 椭圆曲线选择
		ec_comboBox.setEnabled(true);
		randomec_button.setEnabled(true);
	}

	public void original_message_changed() {
		if (DebugTool.DEBUG) {
			System.out.println("original_message_chaged()");
		}
		String original_message = original_message_textArea.getText();
		if (original_message == null || original_message.equals("")) {
			blind_button.setEnabled(false);
			hash_value_label.setText("消息哈希值：？");

			message_point_label.setText("消息曲线点(?,?)");
			return;
		}
		blind_button.setEnabled(true);
		BigInteger m_hash = new BigInteger(
				Math.abs(original_message.hashCode()) + "");

		hash_value_label.setText(String.format("消息哈希值：%s", m_hash));

		user.blindMessage(m_hash);

		message_point_label.setText(String.format("消息曲线点%s", user.getM()));
	}

	public void blind() {
		// 盲化
		original_message_textArea.setEnabled(false);

		String original_message = original_message_textArea.getText();

		BigInteger m_hash = new BigInteger(
				Math.abs(original_message.hashCode()) + "");

		hash_value_label.setText(String.format("消息哈希值：%s", m_hash));

		user.blindMessage(m_hash);

		message_point_label.setText(String.format("消息曲线点%s", user.getM()));

		r_label.setText(String.format("随机盲因子r = %s", user.getR()));

		c1_label.setText(String.format("C1 = rm = %s", user.getC1()));

		c2_label.setText(String.format("C2 = rG+m = %s", user.getC2()));

		blind_button.setEnabled(false);

		// 解盲

		s_label.setText("S = D2 - Ri*r = (?,?)");

		deblind_button.setEnabled(false);

		mm_label.setText("D1*r^-1 - S = (?,?)");

		m_label.setText("m = (?,?)");

		result_label.setText("签名结果：未知");

		verify_botton.setEnabled(false);

		// 中间发送过程
		send1_label.setText(String.format("   发送C1%s,C2%s", user.getC1(),
				user.getC2()));
		sendToSigner();
		send2_label.setText("        发送 i = ?, Ri = (?,?)");

		send3_label.setText("        D1 = (?,?), D2 = (?,?)");

		// 签名
		time_label.setText("时段i = ?");

		private_key_label.setText("私钥Si = ?");

		public_key_label.setText("公钥Ri = Si*G = (?,?)");

		d1_label.setText("D1 = (Si+1)C1 = (?,?)");

		d2_label.setText("D2 = Si*C2 = (?,?)");

		// 椭圆曲线选择
		ec_comboBox.setEnabled(false);
		randomec_button.setEnabled(false);

	}

	public void sendToSigner() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Main.this.reset_button.setEnabled(false);
				Main.this.sign_button.setEnabled(false);
				int i = 7;
				String bar1 = "";
				for (int j = 0; j < 42; j++)
					bar1 += "-";
				String bar2 = "";
				while (--i >= 0) {
					if (i % 2 == 0) {
						Main.this.send1_label.setText(String.format(
								"   发送C1%s,C2%s", user.getC1(),
								user.getC2()));
					} else {
						Main.this.send1_label.setText("");
					}
					bar2 += ">>>";
					bar1 = bar1.substring(6);
					Main.this.send_to_signer_porcess_label.setText(bar2 + bar1);
					// System.out.println(bar2+bar1);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Main.this.reset_button.setEnabled(true);
				Main.this.sign_button.setEnabled(true);
			}

		};
		thread.start();
	}

	public void sign() {
		signer.blindSignature(user.getC1(), user.getC2());
		// 中间发送过程
		send2_label.setText(String.format("   发送 i = %d, Ri = %s",
				signer.getI(), signer.getRi()));

		send3_label.setText(String.format("   D1 = %s, D2 = %s",
				signer.getD1(), signer.getD2()));

		sendToUser();

		// 签名
		time_label.setText(String.format("时段i = %d", signer.getI()));

		private_key_label.setText(String.format("私钥Si = %s", signer.getSi()));

		public_key_label.setText(String.format("公钥Ri = Si*G = %s",
				signer.getRi()));

		d1_label.setText(String.format("D1 = (Si+1)C1 = %s", signer.getD1()));

		d2_label.setText(String.format("D2 = Si*C2 = %s", signer.getD2()));

		sign_button.setEnabled(false);

		// 解盲

		s_label.setText("S = D2 - Ri*r = (?,?)");

		// deblind_button.setEnabled(true);

		mm_label.setText("D1*r^-1 - S = (?,?)");

		m_label.setText("m = (?,?)");

		result_label.setText("签名结果：未知");

		verify_botton.setEnabled(false);
	}

	public void sendToUser() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Main.this.reset_button.setEnabled(false);
				Main.this.deblind_button.setEnabled(false);
				int i = 7;
				String bar1 = "";
				for (int j = 0; j < 42; j++)
					bar1 += "-";
				String bar2 = "";
				while (--i >= 0) {
					if (i % 2 == 0) {
						Main.this.send2_label.setText(String.format(
								"   发送 i = %d, Ri = %s", signer.getI(),
								signer.getRi()));

						Main.this.send3_label.setText(String.format(
								"   D1 = %s, D2 = %s", signer.getD1(),
								signer.getD2()));
					} else {
						Main.this.send2_label.setText("");

						Main.this.send3_label.setText("");

					}
					bar1 = bar1.substring(6);
					bar2 += "<<<";
					Main.this.send_to_user_porcess_label.setText(bar1 + bar2);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Main.this.reset_button.setEnabled(true);
				Main.this.deblind_button.setEnabled(true);
			}

		};
		thread.start();
	}

	public void deblind() {
		// 解盲
		user.deblind();
		s_label.setText(String.format("S = D2 - Ri*r = %s", user.getS()));

		deblind_button.setEnabled(false);

		mm_label.setText("D1*r^-1 - S = (?,?)");

		m_label.setText("m = (?,?)");

		result_label.setText("签名结果：未知");

		verify_botton.setEnabled(true);
	}

	public void verify() {
		// 验证
		boolean res = user.verify();
		mm_label.setText(String.format("D1*r^-1 - S = %s", user.getMm()));

		m_label.setText(String.format("m = %s", user.getM()));

		if (res) {
			result_label.setText("签名结果：签名成功");
		} else {
			result_label.setText("签名结果：签名失败");
		}

		verify_botton.setEnabled(false);
	}
}
