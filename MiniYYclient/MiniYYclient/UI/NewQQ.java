package MiniYYclient.UI;

import javax.swing.*;

import MiniYYclient.main.MainClass;
import MiniYYclient.thread.MainClientSocket;

import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;
import java.util.Random;

class NewQQ extends JFrame implements ActionListener, ItemListener
{
    JPanel         jpl    = new JPanel();                 // 面板
    Icon           icon[] = new ImageIcon[10];
    JButton        but4   = new JButton(" <<上一张");
    JButton        but5   = new JButton("下一张>>");
    JButton        jb     = new JButton("立即注册");
    JLabel         jl;
    JPasswordField jp     = new JPasswordField(10);       // 密码框
    JPasswordField jp1    = new JPasswordField(10);       // 密码框
    JTextField     jt     = new JTextField();             // 文本框（用户名）
    JCheckBox      jcb7   = new JCheckBox("我已阅读并同意相关条款");
    JLabel questionjl = new JLabel ("安全问题");
    JLabel answerjl = new JLabel ("安全答案");
    JTextField questionjt = new JTextField(10);
    JTextField answerjt = new JTextField(10);
    JComboBox      jco1   = new JComboBox(); // 省份
    JComboBox      jco    = new JComboBox(); // 城市
    ButtonGroup    bgp    = new ButtonGroup();// 使单选按钮达到互斥
    int            count  = 0;
    String   	   RandomID = new String(); // 保存新ID号
    Random ra = new Random();
    String sex = new String();
    JRadioButton jrb2 = new JRadioButton();
    JRadioButton jrb1 = new JRadioButton();
    String Provinces = new String();
    String City = new String();
    private Object cities[][];
    private Object province[];

    public NewQQ()
    {
        TP();
        this.add(jpl);// 将面板添加到窗体中

        Font f = new Font("隶书", Font.ITALIC + Font.BOLD, 25);// 设置字体
        Font f1 = new Font("行楷", Font.ITALIC + Font.BOLD, 15);// 设置字体
        Font f2 = new Font("仿宋", Font.BOLD, 20);

        Image image = this.getToolkit().getImage("image/QQ.jpg");// 添加改变窗体的图片


        jpl.setLayout(null);// 自定义布局

        // “选择图片”标签
        JLabel jlxz = new JLabel("选择你的头像:");
        jlxz.setBounds(280, 70, 90, 30);
        jpl.add(jlxz);

        // 图片标签
        jl = new JLabel(icon[0]);
        jl.setBounds(340, 90, 100, 129);
        jpl.add(jl);
        // 上一张按钮
        but4.setBounds(300, 240, 100, 20);

        but4.setForeground(Color.BLUE);
        jpl.add(but4);
        but4.setEnabled(false);
        // 下一张按钮
        but5.setBounds(400, 240, 100, 20);

        but5.setForeground(Color.BLUE);
        jpl.add(but5);
        // 标签
        JLabel jll = new JLabel("用户注册");
        jll.setFont(f);
        jll.setBounds(160, 20, 200, 30);
        jpl.add(jll);

        // 标签
        JLabel jllx = new JLabel("性    别");
        jllx.setBounds(40, 210, 80, 30);
        jpl.add(jllx);
        
        JLabel ques = new JLabel("安全问题");
        ques.setBounds(40, 260, 80, 30);
        jpl.add(ques);
        

        questionjt.setBounds(100, 40, 150, 20);
        jp1.add(questionjt);
        
        
        // 标签
        JLabel jllg = new JLabel("地    址");
        jllg.setBounds(40, 290, 80, 30);
        jpl.add(jllg);

        // 下拉省份列表
        province=new Object[] {"江苏省","山东省"};
		cities=new Object[][] {{"南京市","苏州市","无锡市"},{"威海市","济南市"}};
        String str1[] = { "山东省", "北京", "上海", "重庆" };
        jco1 = new JComboBox(province);
        jco1.setBounds(100, 290, 100, 25);
        jpl.add(jco1);
        // 下拉城市列表
        String str[] = { "威海市", "济南市", "青岛市", "烟台市" };
        jco = new JComboBox(cities[0]);
        jco.setBounds(200, 290, 100, 25);
        jpl.add(jco);
        // JCheckBox jcb7=new JCheckBox("我已阅读并同意相关条款");

        // 性别
        jrb1 = new JRadioButton("男", true);
        jrb1.setBounds(95, 210, 50, 30);

        jpl.add(jrb1);
        jrb2 = new JRadioButton("女");
        jrb2.setBounds(150, 210, 80, 28);

        jpl.add(jrb2);

        // 注册按钮

        //jb.setIcon(new ImageIcon("image/lijizhuce.png"));
        //jb.setFont(f2);
        jb.setText("立即注册");
        jb.setBounds(150, 480, 130, 50);
        jpl.add(jb);
        // 标签
        JLabel jl1 = new JLabel("昵            称");
        jl1.setBounds(35, 60, 100, 40);
        jpl.add(jl1);

        JLabel jl2 = new JLabel("密            码");
        jl2.setBounds(35, 110, 100, 40);
        jpl.add(jl2);

        JLabel jl3 = new JLabel("确认 密 码");
        jl3.setBounds(35, 160, 100, 40);
        jpl.add(jl3);
        // 文本框

        jt.setBounds(100, 72, 150, 20);
        jpl.add(jt);
        // 密码框

        jp.setBounds(100, 120, 150, 20);
        jp.setEchoChar('*');
        jpl.add(jp);
        // 确认密码框
        jp1.setBounds(100, 168, 150, 20);
        jp1.setEchoChar('*');
        jpl.add(jp1);

        jcb7.setBounds(100, 450, 200, 20);

        jpl.add(jcb7);

        bgp.add(jrb1);
        bgp.add(jrb2);

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setIconImage(image);
        this.setBounds(200, 200, 500, 600);
        this.setTitle("注册界面");
        this.setResizable(false);
        this.setVisible(true);

        jb.addActionListener(this);

        but4.addActionListener(this);
        but5.addActionListener(this);
        jcb7.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e)
    {
        // 判断用户名是否为空
        // Object obe=e.getSource();
        String st = jt.getText();
        if(e.getSource()== jco1)
        {
        	int i=jco1.getSelectedIndex();
        	jco.removeAllItems();
        	for(int j=0;j<this.cities[i].length;j++)
        		jco.addItem(this.cities[i][j]);
        }
        if (e.getSource() == jb)
        {

      	  	int x = ra.nextInt(99999999);
      	  	RandomID = ""+x;
      	  	MainClass.cUser.setID(RandomID);
    	  
            System.out.println(e.getSource());
            if (st.length() == 0)
            {
                JOptionPane.showMessageDialog(this, "用户名不可为空！！！", "提示", JOptionPane.ERROR_MESSAGE);
            } else if (st.length() > 10)
            {
                JOptionPane.showMessageDialog(this, "昵称不可大于10位！！！", "提示", JOptionPane.ERROR_MESSAGE);
            }

            // 判断密码是否为空
            // Object ob=e.getSource();
            String s = jp.getText();
            String s1 = jp1.getText();

            if (s.length() == 0)
            {
                JOptionPane.showMessageDialog(this, "密码不能为空！！！", "提示", JOptionPane.ERROR_MESSAGE);
            } else if (s.length() < 6)
            {
                JOptionPane.showMessageDialog(this, "密码不能低于6位！！！", "提示", JOptionPane.ERROR_MESSAGE);
            } else if ((s1) != (s))
            {
                //JOptionPane.showMessageDialog(this, "确认密码和密码不一致！", "提示", JOptionPane.ERROR_MESSAGE);
            }

            System.out.println(jcb7.isSelected());
            if (jcb7.isSelected())
            {
                if (s.length() >= 6 && st.length() >= 4 && s == s1)
                {
                    int str = JOptionPane.showConfirmDialog(this, "恭喜你注册成功！！！", "提示", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    System.out.println(str);
                }
            } else
            {
                JOptionPane.showMessageDialog(this, "条款项没有勾选！", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (jrb1.isSelected()) 
            {
            	sex=("男");
            }
            else if (jrb2.isSelected())
            {
            	sex="女";
            }
            Provinces = (String) jco1.getSelectedItem();
            City = (String) jco.getSelectedItem();
            MainClientSocket.getMainSocket().applyID(MainClass.cUser.getID(),jt.getText(),jp.getText(),sex,Provinces,City,"shenme?","zhege","1");    
            this.setVisible(false);

        }

       // 头像数组
        if (e.getSource().equals(but4))
        {
            count = count - 1;
            jl.setIcon(icon[count]);
            if (count == 0)
            {
                but4.setEnabled(false);
            } else
            {
                but4.setEnabled(true);
            }

            if (count == 9)
            {
                but5.setEnabled(false);
            } else
            {
                but5.setEnabled(true);
            }

        } else if (e.getSource().equals(but5))
        {
            count = count + 1;
            jl.setIcon(icon[count]);
            if (count == 9)
            {
                but5.setEnabled(false);
            } else
            {
                but5.setEnabled(true);
            }
            if (count == 0)
            {
                but4.setEnabled(false);
            } else
            {
                but4.setEnabled(true);
            }
        }
    }

    public void TP()
    {
        for (int i = 0; i < 10; i++)
        {
            Icon on = new ImageIcon("image/" + (i + 1) + ".png");
            icon[i] = on;
        }
    }

    public void itemStateChanged(ItemEvent e)
    {
        // TODO Auto-generated method stub
        if (e.getStateChange() == ItemEvent.SELECTED)
        {
            System.out.println(e.getSource());
            System.out.println("设置状体为true");
            jcb7.setSelected(true);
            System.out.println(jcb7.isSelected());
        }
    }
}
