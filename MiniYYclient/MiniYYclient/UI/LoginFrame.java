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

    private JLabel         applyLabel, retrpwdLabel; // �����ʺ� �һ�����
    private JTextField     userNameTF;                   // �ʺ������
    private JPasswordField pwd_pwf;
    //private JTextField 	   pwd_pwf;                 // ���������
    private JCheckBox      recdpwd_chkb, autologin_chkb; // ��ס����, �Զ���¼
    private JLabel         portrait_lab;                // ͷ��
    public static int      port = 8888;                 // �˿�
    public static String   ip   = "127.0.0.1";
    private JButton loginButton = new JButton();

    public LoginFrame(){
    	
    }
    // ��������
    public void login_interface()
    {
        this.setTitle("MiniYY");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        Panel p1 = new Panel();
        p1.setLayout(null);

        // ͷ��
        portrait_lab = new JLabel();
        portrait_lab.setIcon(new ImageIcon("image/5.png"));
        portrait_lab.setBounds(20, 5, 82, 82);
        p1.add(portrait_lab);

        // qq and password ����
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

        // �����ʺ�
        applyLabel = new JLabel();
        applyLabel.setText("ע���ʺ�");
        applyLabel.setBounds(290, -8, 50, 50);
        applyLabel.setFont(new Font("����", Font.PLAIN, 12));
        applyLabel.setForeground(Color.blue);
        applyLabel.addMouseListener(new mouse());
        applyLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p1.add(applyLabel);

        // �һ�����
        retrpwdLabel = new JLabel();
        retrpwdLabel.setText("�һ�����");
        retrpwdLabel.setBounds(290, 25, 80, 50);
        retrpwdLabel.setFont(new Font("����", Font.PLAIN, 12));
        retrpwdLabel.setForeground(Color.blue);
        retrpwdLabel.addMouseListener(new mouse());
        retrpwdLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p1.add(retrpwdLabel);

        // ��ס����
        recdpwd_chkb = new JCheckBox();
        recdpwd_chkb.setText("��ס����");
        recdpwd_chkb.setBounds(115, 65, 80, 20);
        p1.add(recdpwd_chkb);

        // �Զ���¼
        autologin_chkb = new JCheckBox();
        autologin_chkb.setText("�Զ���¼");
        autologin_chkb.setBounds(195, 65, 80, 20);
        p1.add(autologin_chkb);

        // ���ʺ�
        JButton moreidButton = new JButton();
        moreidButton.setIcon(new ImageIcon("image/duozhanghao.PNG"));
        moreidButton.setBounds(15, 100, 70, 25);
        p1.add(moreidButton);

        // ����
        JButton setButton = new JButton("����");

        setButton.setBounds(100, 100, 70, 25);
        setButton.addActionListener(this);
       
        p1.add(setButton);

        // ��¼
        
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

        // ����ͼƬ
        JLabel mainJLabel = new JLabel();
        mainJLabel.setIcon(new ImageIcon("image/loginup.PNG"));
        add(mainJLabel, BorderLayout.NORTH);
        // ͼ��
        Image logoImage = Toolkit.getDefaultToolkit().getImage("image/tubiao2.png");
        setIconImage(logoImage);
        // ��С
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screensize.width / 3, screensize.height / 3);
        setSize(379, 280);
        // ����в�panel
        cp.add(p1, BorderLayout.CENTER);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // label����¼�
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

    // ��¼�¼�
    protected void loginAction(ActionEvent e)
    {
    	MainClass.cUser.setID(userNameTF.getText());
    	
        if (userNameTF.getText().trim().equals("") || userNameTF.getText().trim().length() < 6
                || userNameTF.getText().trim().length() > 12)
        {
            JOptionPane.showMessageDialog(null, "��������ȷ���˺�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
        } else if (pwd_pwf.getText().trim().equals(""))
        {
            JOptionPane.showMessageDialog(null, "����������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
        } else
        {

        	// �����û������Ա�
            CommonData.setUserName(userNameTF.getText().trim());
            CommonData.setUserSex("��"); // ��ʱĬ��Ϊ ���С�

            
            //���������Ϣ������Ϣ ��ʽuserName:::passWord:::sex
            MainClientSocket.getMainSocket().login(userNameTF.getText().trim(),pwd_pwf.getText().trim(), "��");
            this.setVisible(false);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e)
    {
        
    }        
    

}