package MiniYYclient.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import MiniYYclient.common.CommonData;
import MiniYYclient.main.MainClass;
import MiniYYclient.thread.MainClientSocket;
import MiniYYclient.UI.*;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame implements ActionListener
{

    private JLabel         applyLabel, retrpwdLabel; // 申请帐号 找回密码
    private JTextField     userNameTF;                   // 帐号输入框
    private JPasswordField pwd_pwf;
    //private JTextField 	   pwd_pwf;                 // 密码输入框
    private JCheckBox      recdpwd_chkb, autologin_chkb; // 记住密码, 自动登录
    private JLabel         portrait_lab;                // 头像
    public static int      port = 8888;                 // 端口
    public static String   ip   = "127.0.0.1";
    private JButton loginButton = new JButton();

    public LoginFrame(){
    	
    }
    // 界面设置
    public void login_interface()
    {
        this.setTitle("MiniYY");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        Panel p1 = new Panel();
        p1.setLayout(null);

        // 头像
        portrait_lab = new JLabel();
        portrait_lab.setIcon(new ImageIcon("image/5.png"));
        portrait_lab.setBounds(20, 5, 82, 82);
        p1.add(portrait_lab);

        // qq and password 输入
        userNameTF = new JTextField("12345678");
        userNameTF.setBounds(110, 10, 160, 20);
        userNameTF.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loginAction(e);
            }
        });
        p1.add(userNameTF);
        pwd_pwf = new JPasswordField("123");
        pwd_pwf.setBounds(110, 40, 160, 20);
        pwd_pwf.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loginAction(e);
            }
        });
        p1.add(pwd_pwf);

        // 申请帐号
        applyLabel = new JLabel();
        applyLabel.setText("注册帐号");
        applyLabel.setBounds(290, -8, 50, 50);
        applyLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        applyLabel.setForeground(Color.blue);
        applyLabel.addMouseListener(new mouse());
        applyLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p1.add(applyLabel);

        // 找回密码
        retrpwdLabel = new JLabel();
        retrpwdLabel.setText("找回密码");
        retrpwdLabel.setBounds(290, 25, 80, 50);
        retrpwdLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        retrpwdLabel.setForeground(Color.blue);
        retrpwdLabel.addMouseListener(new mouse());
        retrpwdLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p1.add(retrpwdLabel);

        // 记住密码
        recdpwd_chkb = new JCheckBox();
        recdpwd_chkb.setText("记住密码");
        recdpwd_chkb.setBounds(115, 65, 80, 20);
        p1.add(recdpwd_chkb);

        // 自动登录
        autologin_chkb = new JCheckBox();
        autologin_chkb.setText("自动登录");
        autologin_chkb.setBounds(195, 65, 80, 20);
        p1.add(autologin_chkb);

        // 多帐号
        JButton moreidButton = new JButton();
        moreidButton.setIcon(new ImageIcon("image/duozhanghao.PNG"));
        moreidButton.setBounds(15, 100, 70, 25);
        p1.add(moreidButton);

        // 设置
        JButton setButton = new JButton("设置");

        setButton.setBounds(100, 100, 70, 25);
        setButton.addActionListener(this);
       
        p1.add(setButton);

        // 登录
        
        loginButton.setIcon(new ImageIcon("image/denglu.png"));
        loginButton.setBounds(280, 100, 70, 25);
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loginAction(e);
            }
        });
        p1.add(loginButton);

        // 北部图片
        JLabel mainJLabel = new JLabel();
        mainJLabel.setIcon(new ImageIcon("image/loginup.PNG"));
        add(mainJLabel, BorderLayout.NORTH);
        // 图标
        Image logoImage = Toolkit.getDefaultToolkit().getImage("image/tubiao2.png");
        setIconImage(logoImage);
        // 大小
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screensize.width / 3, screensize.height / 3);
        setSize(379, 280);
        // 添加中部panel
        cp.add(p1, BorderLayout.CENTER);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // label点击事件
    private class mouse extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            JLabel labeltype = (JLabel) e.getSource();
            if (labeltype == applyLabel)
            {
                new NewQQ();
            } else if (labeltype == retrpwdLabel)
                System.out.println("please enter safe");

            super.mouseClicked(e);
        }
    }

    // 登录事件
    protected void loginAction(ActionEvent e)
    {
    	MainClass.cUser.setID(userNameTF.getText());
    	
        if (userNameTF.getText().trim().equals("") || userNameTF.getText().trim().length() < 6
                || userNameTF.getText().trim().length() > 12)
        {
            JOptionPane.showMessageDialog(null, "请输入正确的账号", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else if (pwd_pwf.getText().trim().equals(""))
        {
            JOptionPane.showMessageDialog(null, "请输入密码", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else
        {

        	// 设置用户名和性别
            CommonData.setUserName(userNameTF.getText().trim());
            CommonData.setUserSex("男"); // 暂时默认为 “男”

            
            //打包本层消息发送信息 格式userName:::passWord:::sex
            MainClientSocket.getMainSocket().login(userNameTF.getText().trim(),pwd_pwf.getText().trim(), "男");
            this.setVisible(false);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e)
    {
        
    }        
    

}