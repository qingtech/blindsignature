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

namespace BlindSignature
{
    /// <summary>
    /// MainWindow.xaml 的交互逻辑
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            this.tabcontrol_1.MouseWheel += this.tabcontrol_1_MouseWheel;
        }
        void tabcontrol_1_MouseWheel(object sender, MouseWheelEventArgs e)
        {
            TabControl tc = sender as TabControl;
            int tabpage_cnt = tc.Items.Count;
            int new_i = (tc.SelectedIndex - Math.Sign(e.Delta)) % tabpage_cnt;
            if (new_i < 0)
            {
                new_i = new_i + tabpage_cnt;
            }
            tc.SelectedIndex = new_i;
        }
    }
}
