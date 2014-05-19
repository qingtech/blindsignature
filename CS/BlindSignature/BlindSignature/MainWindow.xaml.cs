using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Forms;
using System.IO;
namespace BlindSignature
{
    /// <summary>
    /// MainWindow.xaml 的交互逻辑
    /// </summary>
    public partial class MainWindow : Window
    {
        //
        long hash_value = 2;
        User user;
        Signer signer;
        EllipticCurve ec;
		EllipticCurve[] ecs;
		System.Windows.Controls.Label[] label_ecs;
        //
        double scroll_viewer_position = 0;
		int label_ecs_index = 0;
        //
        Color whole_background = Color.FromArgb(255,255,74,0);
        public MainWindow()
        {
            ecs = new EllipticCurve[9];
            for (int i = 0; i < 9; i++)
            {
                ecs[i] = new EllipticCurve(i);
            }
           
            InitializeComponent();
			label_ecs = new System.Windows.Controls.Label[9];
			for(int i=0;i<9;i++)
			{
				label_ecs[i] = FindName("label_ecs_"+i) as System.Windows.Controls.Label;
				label_ecs[i].Content = ecs[i];
			}
            this.choose_ec(this.label_ecs_index);
            this.init();
            this.scroll_viewer_1.PreviewMouseWheel += this.scroll_viewer_1_PreviewMouseWheel;
        }
        private void choose_ec(int index)
        {
            user = new User(index);
            ec = user.EC;
            signer = user.SIGNER;
        }
        private void text_box_file_name_GotFocus(object sender, RoutedEventArgs e)
        {
            // 在此处添加事件处理程序实现。
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Title = "选择文件";
            openFileDialog.Filter = "zip文件|*.zip|rar文件|*.rar|所有文件|*.*";
            openFileDialog.FileName = string.Empty;
            openFileDialog.FilterIndex = 3;
            openFileDialog.RestoreDirectory = true;
            openFileDialog.DefaultExt = "*";
            DialogResult result = openFileDialog.ShowDialog();
            if (result == System.Windows.Forms.DialogResult.Cancel)
            {
                this.get_message();
                this.scroll_viewer_1.Focus();
                return;
            }
            string file_name = openFileDialog.FileName;
            this.text_box_file_name.Text = file_name;
            this.button_blind.Focus();
            
        }
        private void text_box_file_name_TextChanged(object sender, TextChangedEventArgs e)
        {
            /*
            if (this.button_blind == null) { this.init(); return; }
            string file_name = this.text_box_file_name.Text;
            if (!File.Exists(file_name)) { this.init(); return; }
            string file_hash = FileMD5.HashFile(file_name, "MD5");
            //this.label_inter.Content = file_hash;
            hash_value = file_hash.GetHashCode();
            this.ready_to_blind_message();
            this.button_blind.Focus();
             * */
            this.get_message();
        }
        private void text_box_message_string_TextChanged(object sender, TextChangedEventArgs e)
        {
            /*
            string str = this.text_box_message_string.Text;
            if (str.Equals(""))
            {
                this.init();
                return;
            }
            hash_value = str.GetHashCode();
            
            this.ready_to_blind_message();
             */
            this.get_message();
        }

        private void tab_control_1_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            this.get_message();
        }

        private void get_message()
        {
            //this.label_inter.Content = this.tab_control_1.SelectedIndex + "";
            if (this.tab_control_1.SelectedIndex == 0) //文件
            {
                if (this.button_blind == null) { this.init(); return; }
                string file_name = this.text_box_file_name.Text;
                if (!File.Exists(file_name)) { this.init(); return; }
                string file_hash = FileMD5.HashFile(file_name, "MD5");
                //this.label_inter.Content = file_hash;
                hash_value = file_hash.GetHashCode();
                this.ready_to_blind_message();
                this.button_blind.Focus();
            }
            else if (this.tab_control_1.SelectedIndex == 1) //字符串
            {
                string str = this.text_box_message_string.Text;
                if (str.Equals(""))
                {
                    this.init();
                    return;
                }
                hash_value = str.GetHashCode();
                this.ready_to_blind_message();
            }
            this.scroll_viewer_1.ScrollToHome();
        }

        private void scroll_viewer_1_PreviewMouseWheel(object sender, MouseWheelEventArgs e)
        {
            // 在此处添加事件处理程序实现。

            this.scroll_viewer_position -= Math.Sign(e.Delta);
            int offset = -1;
            if (this.scroll_viewer_position > 1)
            {
                offset = (int)this.scroll_viewer_1.ContentVerticalOffset / 380 + 1;
                if (offset > 2) offset = 2;
                this.scroll_viewer_1.ScrollToVerticalOffset(offset*380);
                this.scroll_viewer_position = 0;
            }
            else if (this.scroll_viewer_position < -1)
            {
                offset = (int)this.scroll_viewer_1.ContentVerticalOffset / 380;
                if (offset < 0) offset = 0;
                this.scroll_viewer_1.ScrollToVerticalOffset(offset * 380);
                this.scroll_viewer_position = 0;
            }
            if (offset == -1) return;
            this.scroll_viewer_offset_change(offset);
            
        }
        private void scroll_viewer_offset_change(int offset)
        {
            this.label_message_blind.Background = new SolidColorBrush(whole_background);
            this.label_blind_signature.Background = new SolidColorBrush(whole_background);
            this.label_deblind.Background = new SolidColorBrush(whole_background);
            this.label_verify.Background = new SolidColorBrush(whole_background);
            switch (offset)
            {
                case 0:
                    this.label_message_blind.Background = new SolidColorBrush(Color.FromArgb(255, 51, 51, 51));
                    this.header_gird.Background = new SolidColorBrush(Color.FromArgb(255, 51, 51, 51));
                    break;
                case 1:
                    this.label_blind_signature.Background = new SolidColorBrush(Color.FromArgb(255, 120, 132, 189));
                    this.header_gird.Background = new SolidColorBrush(Color.FromArgb(255, 120, 132, 189));
                    break;
                case 2:
                    this.label_deblind.Background = new SolidColorBrush(Color.FromArgb(255, 119, 156, 197));
                    this.label_verify.Background = new SolidColorBrush(Color.FromArgb(255, 119, 156, 197));
                    this.header_gird.Background = new SolidColorBrush(Color.FromArgb(255, 119, 156, 197));
                    break;
                default:
                    break;
            }
        }
        private void button_blind_Click(object sender, RoutedEventArgs e)
        {
            this.ready_to_blind_signature();
        }

        private void button_blind_signature_Click(object sender, RoutedEventArgs e)
        {
            this.ready_to_deblind();
        }

        private void button_deblind_Click(object sender, RoutedEventArgs e)
        {
            this.ready_to_verify();
        }

        private void button_verify_Click(object sender, RoutedEventArgs e)
        {
            /***********************
            Point rd = ec.multiply(ec.getMulInverse(user.R,ec.ORDG), signer.D1);
            Point ss = user.EC.getInverse(user.S);
            Point mm = ec.add(rd,ss);
            this.label_user.Content = "rd:" + rd;
            this.label_inter.Content = "-S = " + ss;
            this.label_signer.Content = "mm = " + mm;
             ***********************/
            this.label_user_mm.Content = user.MM;
            //this.label_user_mm.Content = mm;
            this.label_user_m_2.Content = user.M;
            this.button_verify.IsEnabled = false;
        }

        public void init()
        {
            //user
            //message blind
            if (this.label_user_hash_value == null) return;
            this.label_user_hash_value.Content = "?";
            this.label_user_m_1.Content = "(?,?)";
            this.label_user_r.Content = "?";
            this.label_user_c1.Content = "(?,?)";
            this.label_user_c2.Content = "(?,?)";
            this.button_blind.IsEnabled = false;
            //blind signature
            this.label_user_i.Content = "?";
            this.label_user_ri.Content = "(?,?)";
            this.label_user_d1.Content = "(?,?)";
            this.label_user_d2.Content = "(?,?)";
            //deblind & verify
            this.label_user_s.Content = "(?,?)";
            this.label_user_mm.Content = "(?,?)";
            this.label_user_m_2.Content = "(?,?)";
            this.button_deblind.IsEnabled = false;
            this.button_verify.IsEnabled = false;

            //inter
            //message blind
            this.label_inter_c1.Content = "(?,?)";
            this.label_inter_c2.Content = "(?,?)";
            //blind signature
            this.label_inter_i.Content = "?";
            this.label_inter_ri.Content = "(?,?)";
            this.label_inter_d1.Content = "(?,?)";
            this.label_inter_d2.Content = "(?,?)";
            //deblind & verify

            //signer
            //message blind
            this.label_signer_c1.Content = "(?,?)";
            this.label_signer_c2.Content = "(?,?)";
            //blind signature
            this.label_signer_s0.Content = "?";
            this.label_signer_i.Content = "?";
            this.label_signer_si.Content = "?";
            this.label_signer_ri.Content = "(?,?)";
            this.label_signer_d1.Content = "(?,?)";
            this.label_signer_d2.Content = "(?,?)";
            this.button_blind_signature.IsEnabled = false;
            //deblind & verify
			
			//
			this.scroll_viewer_1.ScrollToVerticalOffset(0);
        }
        public void ready_to_blind_message()
        {
            //
            if (hash_value < 0) hash_value = 0 - hash_value;
            /***************
            hash_value = 100;
             ***************/
            user.process(hash_value);
           
            //user
            //message blind
            if (this.label_user_hash_value == null) return;
            this.label_user_hash_value.Content = hash_value;
            this.label_user_m_1.Content = this.user.M;
            this.label_user_r.Content = "?";
            this.label_user_c1.Content = "(?,?)";
            this.label_user_c2.Content = "(?,?)";
            this.button_blind.IsEnabled = true;
            //blind signature
            this.label_user_i.Content = "?";
            this.label_user_ri.Content = "(?,?)";
            this.label_user_d1.Content = "(?,?)";
            this.label_user_d2.Content = "(?,?)";
            //deblind & verify
            this.label_user_s.Content = "(?,?)";
            this.label_user_mm.Content = "(?,?)";
            this.label_user_m_2.Content = "(?,?)";
            this.button_deblind.IsEnabled = false;
            this.button_verify.IsEnabled = false;

            //inter
            //message blind
            this.label_inter_c1.Content = "(?,?)";
            this.label_inter_c2.Content = "(?,?)";
            //blind signature
            this.label_inter_i.Content = "?";
            this.label_inter_ri.Content = "(?,?)";
            this.label_inter_d1.Content = "(?,?)";
            this.label_inter_d2.Content = "(?,?)";
            //deblind & verify

            //signer
            //message blind
            this.label_signer_c1.Content = "(?,?)";
            this.label_signer_c2.Content = "(?,?)";
            //blind signature
            this.label_signer_s0.Content = "?";
            this.label_signer_i.Content = "?";
            this.label_signer_si.Content = "?";
            this.label_signer_ri.Content = "(?,?)";
            this.label_signer_d1.Content = "(?,?)";
            this.label_signer_d2.Content = "(?,?)";
            this.button_blind_signature.IsEnabled = false;
            //deblind & verify
        }
        public void ready_to_blind_signature()
        {
            //message blind
            this.label_user_hash_value.Content = hash_value + "";
            this.label_user_m_1.Content = user.M;
            this.label_user_r.Content = user.R;
            this.label_user_c1.Content = user.C1;
            this.label_user_c2.Content = user.C2;
           
			//this.button_blind.Background = new SolidColorBrush(Color.FromArgb(255, 0, 0, 0));
            this.button_blind.IsEnabled = false;
			
            //blind signature
            this.label_user_i.Content = "?";
            this.label_user_ri.Content = "(?,?)";
            this.label_user_d1.Content = "(?,?)";
            this.label_user_d2.Content = "(?,?)";
            //deblind & verify
            this.label_user_s.Content = "(?,?)";
            this.label_user_mm.Content = "(?,?)";
            this.label_user_m_2.Content = "(?,?)";
            this.button_deblind.IsEnabled = false;
            this.button_verify.IsEnabled = false;

            //inter
            //message blind
            this.label_inter_c1.Content = user.C1;
            this.label_inter_c2.Content = user.C2;
            //blind signature
            this.label_inter_i.Content = "?";
            this.label_inter_ri.Content = "(?,?)";
            this.label_inter_d1.Content = "(?,?)";
            this.label_inter_d2.Content = "(?,?)";
            //deblind & verify

            //signer
            //message blind
           this.label_signer_c1.Content = user.C1;
           this.label_signer_c2.Content = user.C2;
            //blind signature
            this.label_signer_s0.Content = "?";
            this.label_signer_i.Content = "?";
            this.label_signer_si.Content = "?";
            this.label_signer_ri.Content = "(?,?)";
            this.label_signer_d1.Content = "(?,?)";
            this.label_signer_d2.Content = "(?,?)";
            this.button_blind_signature.IsEnabled = true;
            this.scroll_viewer_1.ScrollToVerticalOffset(1*380);
            this.scroll_viewer_offset_change(1);
            this.button_blind_signature.Focus();
            //deblind & verify
        }
        public void ready_to_deblind()
        {
            //user
            //message blind
            this.label_user_hash_value.Content = hash_value + "";
            this.label_user_m_1.Content = user.M;
            this.label_user_r.Content = user.R;
            this.label_user_c1.Content = user.C1;
            this.label_user_c2.Content = user.C2;
            this.button_blind.IsEnabled = false;
            //blind signature
            this.label_user_i.Content = signer.I;
            this.label_user_ri.Content = signer.RI;
            this.label_user_d1.Content = signer.D1;
            this.label_user_d2.Content = signer.D2;
            //deblind & verify
            this.label_user_s.Content = "(?,?)";
            this.label_user_mm.Content = "(?,?)";
            this.label_user_m_2.Content = "(?,?)";
            this.button_deblind.IsEnabled = true;
            this.scroll_viewer_1.ScrollToVerticalOffset(2 * 380);
            this.scroll_viewer_offset_change(2);
            this.button_deblind.Focus();
            this.button_verify.IsEnabled = false;

            //inter
            //message blind
            this.label_inter_c1.Content = user.C1;
            this.label_inter_c2.Content = user.C2;
            //blind signature
            this.label_inter_i.Content = signer.RI;
            this.label_inter_ri.Content = signer.RI;
            this.label_inter_d1.Content = signer.D1;
            this.label_inter_d2.Content = signer.D2;
            //deblind & verify

            //signer
            //message blind
            this.label_signer_c1.Content = user.C1;
            this.label_signer_c2.Content = user.C2;
            //blind signature
            this.label_signer_s0.Content = signer.S0;
            this.label_signer_i.Content = signer.I;
            this.label_signer_si.Content = signer.SI;
            this.label_signer_ri.Content = signer.RI;
            this.label_signer_d1.Content = signer.D1;
            this.label_signer_d2.Content = signer.D2;
            this.button_blind_signature.IsEnabled = false;
            //deblind & verify
        }
        public void ready_to_verify()
        {
            //message blind
            this.label_user_hash_value.Content = hash_value + "";
            this.label_user_m_1.Content = user.M;
            this.label_user_r.Content = user.R;
            this.label_user_c1.Content = user.C1;
            this.label_user_c2.Content = user.C2;
            this.button_blind.IsEnabled = false;
            //blind signature
            this.label_user_i.Content = signer.I;
            this.label_user_ri.Content = signer.RI;
            this.label_user_d1.Content = signer.D1;
            this.label_user_d2.Content = signer.D2;
            //deblind & verify
            this.label_user_s.Content = user.S;
            this.label_user_mm.Content = "(?,?)";
            this.label_user_m_2.Content = "(?,?)";
            this.button_deblind.IsEnabled = false;
            this.button_verify.IsEnabled = true;
            this.button_verify.Focus();

            //inter
            //message blind
            this.label_inter_c1.Content = user.C1;
            this.label_inter_c2.Content = user.C2;
            //blind signature
            this.label_inter_i.Content = signer.RI;
            this.label_inter_ri.Content = signer.RI;
            this.label_inter_d1.Content = signer.D1;
            this.label_inter_d2.Content = signer.D2;
            //deblind & verify

            //signer
            //message blind
            this.label_signer_c1.Content = user.C1;
            this.label_signer_c2.Content = user.C2;
            //blind signature
            this.label_signer_s0.Content = signer.S0;
            this.label_signer_i.Content = signer.I;
            this.label_signer_si.Content = signer.SI;
            this.label_signer_ri.Content = signer.RI;
            this.label_signer_d1.Content = signer.D1;
            this.label_signer_d2.Content = signer.D2;
            this.button_blind_signature.IsEnabled = false;
            //deblind & verify
        }
		
        private void label_ecs_MouseDown(object sender, System.Windows.Input.MouseButtonEventArgs e)
        {
        	// TODO: Add event handler implementation here.
			System.Windows.Controls.Label label = sender as System.Windows.Controls.Label;
			for(int i=0;i < this.label_ecs.Length; i++)
			{
				if(this.label_ecs[i] == label)
				{
					this.label_ecs_change(i);
					return;
				}
			}
        }

        private void label_ecs_MouseWheel(object sender, System.Windows.Input.MouseWheelEventArgs e)
        {
        	// TODO: Add event handler implementation here.
			int index = this.label_ecs_index;
			index -= Math.Sign(e.Delta);
			index += this.label_ecs.Length;
			index %= this.label_ecs.Length;
			this.label_ecs_change(index);
        }
		private void label_ecs_change(int index)
		{
			if(index == this.label_ecs_index) return;
			this.label_ecs_index = index;
            this.choose_ec(this.label_ecs_index);
			for(int i=0;i < this.label_ecs.Length; i++)
			{
				label_ecs[i].Foreground = new SolidColorBrush(Color.FromArgb(255, 200, 200, 200));
                label_ecs[i].Background = new SolidColorBrush(whole_background);
			}
			label_ecs[index].Background = new SolidColorBrush(Color.FromArgb(255, 0, 0, 0));
			label_ecs[index].Foreground = new SolidColorBrush(Color.FromArgb(255, 255, 255, 255));

            this.get_message();
		}

		private void button_exit_PreviewMouseLeftButtonUp(object sender, System.Windows.Input.MouseButtonEventArgs e)
		{
			// 在此处添加事件处理程序实现。
			this.Close();
		}

    }
}
