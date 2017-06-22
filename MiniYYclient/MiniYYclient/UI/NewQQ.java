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
    JPanel         jpl    = new JPanel();                 // ���
    Icon           icon[] = new ImageIcon[10];
    JButton        but4   = new JButton(" <<��һ��");
    JButton        but5   = new JButton("��һ��>>");
    JButton        jb     = new JButton("����ע��");
    JLabel         jl;
    JPasswordField jp     = new JPasswordField(10);       // �����
    JPasswordField jp1    = new JPasswordField(10);       // �����
    JTextField     jt     = new JTextField();             // �ı����û�����
    JCheckBox      jcb7   = new JCheckBox("�����Ķ���ͬ���������");
    JLabel questionjl = new JLabel ("��ȫ����");
    JLabel answerjl = new JLabel ("��ȫ��");
    JTextField questionjt = new JTextField(10);
    JTextField answerjt = new JTextField(10);
    JComboBox      jco1   = new JComboBox(); // ʡ��
    JComboBox      jco    = new JComboBox(); // ����
    ButtonGroup    bgp    = new ButtonGroup();// ʹ��ѡ��ť�ﵽ����
    int            count  = 0;
    String   	   RandomID = new String(); // ������ID��
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
        this.add(jpl);// �������ӵ�������

        Font f = new Font("����", Font.ITALIC + Font.BOLD, 25);// ��������
        Font f1 = new Font("�п�", Font.ITALIC + Font.BOLD, 15);// ��������
        Font f2 = new Font("����", Font.BOLD, 20);

        Image image = this.getToolkit().getImage("image/QQ.jpg");// ��Ӹı䴰���ͼƬ


        jpl.setLayout(null);// �Զ��岼��

        // ��ѡ��ͼƬ����ǩ
        JLabel jlxz = new JLabel("ѡ�����ͷ��:");
        jlxz.setBounds(280, 70, 90, 30);
        jpl.add(jlxz);

        // ͼƬ��ǩ
        jl = new JLabel(icon[0]);
        jl.setBounds(340, 90, 100, 129);
        jpl.add(jl);
        // ��һ�Ű�ť
        but4.setBounds(300, 240, 100, 20);

        but4.setForeground(Color.BLUE);
        jpl.add(but4);
        but4.setEnabled(false);
        // ��һ�Ű�ť
        but5.setBounds(400, 240, 100, 20);

        but5.setForeground(Color.BLUE);
        jpl.add(but5);
        // ��ǩ
        JLabel jll = new JLabel("�û�ע��");
        jll.setFont(f);
        jll.setBounds(160, 20, 200, 30);
        jpl.add(jll);

        // ��ǩ
        JLabel jllx = new JLabel("��    ��");
        jllx.setBounds(40, 210, 80, 30);
        jpl.add(jllx);
        
        JLabel ques = new JLabel("��ȫ����");
        ques.setBounds(40, 260, 80, 30);
        jpl.add(ques);
        

        questionjt.setBounds(100, 40, 150, 20);
        jp1.add(questionjt);
        
        
        // ��ǩ
        JLabel jllg = new JLabel("��    ַ");
        jllg.setBounds(40, 290, 80, 30);
        jpl.add(jllg);

        // ����ʡ���б�
        province=new Object[] {"����ʡ","ɽ��ʡ"};
		cities=new Object[][] {{"�Ͼ���","������","������"},{"������","������"}};
        String str1[] = { "ɽ��ʡ", "����", "�Ϻ�", "����" };
        jco1 = new JComboBox(province);
        jco1.setBounds(100, 290, 100, 25);
        jpl.add(jco1);
        // ���������б�
        String str[] = { "������", "������", "�ൺ��", "��̨��" };
        jco = new JComboBox(cities[0]);
        jco.setBounds(200, 290, 100, 25);
        jpl.add(jco);
        // JCheckBox jcb7=new JCheckBox("�����Ķ���ͬ���������");

        // �Ա�
        jrb1 = new JRadioButton("��", true);
        jrb1.setBounds(95, 210, 50, 30);

        jpl.add(jrb1);
        jrb2 = new JRadioButton("Ů");
        jrb2.setBounds(150, 210, 80, 28);

        jpl.add(jrb2);

        // ע�ᰴť

        //jb.setIcon(new ImageIcon("image/lijizhuce.png"));
        //jb.setFont(f2);
        jb.setText("����ע��");
        jb.setBounds(150, 480, 130, 50);
        jpl.add(jb);
        // ��ǩ
        JLabel jl1 = new JLabel("��            ��");
        jl1.setBounds(35, 60, 100, 40);
        jpl.add(jl1);

        JLabel jl2 = new JLabel("��            ��");
        jl2.setBounds(35, 110, 100, 40);
        jpl.add(jl2);

        JLabel jl3 = new JLabel("ȷ�� �� ��");
        jl3.setBounds(35, 160, 100, 40);
        jpl.add(jl3);
        // �ı���

        jt.setBounds(100, 72, 150, 20);
        jpl.add(jt);
        // �����

        jp.setBounds(100, 120, 150, 20);
        jp.setEchoChar('*');
        jpl.add(jp);
        // ȷ�������
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
        this.setTitle("ע�����");
        this.setResizable(false);
        this.setVisible(true);

        jb.addActionListener(this);

        but4.addActionListener(this);
        but5.addActionListener(this);
        jcb7.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e)
    {
        // �ж��û����Ƿ�Ϊ��
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
                JOptionPane.showMessageDialog(this, "�û�������Ϊ�գ�����", "��ʾ", JOptionPane.ERROR_MESSAGE);
            } else if (st.length() > 10)
            {
                JOptionPane.showMessageDialog(this, "�ǳƲ��ɴ���10λ������", "��ʾ", JOptionPane.ERROR_MESSAGE);
            }

            // �ж������Ƿ�Ϊ��
            // Object ob=e.getSource();
            String s = jp.getText();
            String s1 = jp1.getText();

            if (s.length() == 0)
            {
                JOptionPane.showMessageDialog(this, "���벻��Ϊ�գ�����", "��ʾ", JOptionPane.ERROR_MESSAGE);
            } else if (s.length() < 6)
            {
                JOptionPane.showMessageDialog(this, "���벻�ܵ���6λ������", "��ʾ", JOptionPane.ERROR_MESSAGE);
            } else if ((s1) != (s))
            {
                //JOptionPane.showMessageDialog(this, "ȷ����������벻һ�£�", "��ʾ", JOptionPane.ERROR_MESSAGE);
            }

            System.out.println(jcb7.isSelected());
            if (jcb7.isSelected())
            {
                if (s.length() >= 6 && st.length() >= 4 && s == s1)
                {
                    int str = JOptionPane.showConfirmDialog(this, "��ϲ��ע��ɹ�������", "��ʾ", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    System.out.println(str);
                }
            } else
            {
                JOptionPane.showMessageDialog(this, "������û�й�ѡ��", "��ʾ", JOptionPane.ERROR_MESSAGE);
            }
            if (jrb1.isSelected()) 
            {
            	sex=("��");
            }
            else if (jrb2.isSelected())
            {
            	sex="Ů";
            }
            Provinces = (String) jco1.getSelectedItem();
            City = (String) jco.getSelectedItem();
            MainClientSocket.getMainSocket().applyID(MainClass.cUser.getID(),jt.getText(),jp.getText(),sex,Provinces,City,"shenme?","zhege","1");    
            this.setVisible(false);

        }

       // ͷ������
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
            System.out.println("����״��Ϊtrue");
            jcb7.setSelected(true);
            System.out.println(jcb7.isSelected());
        }
    }
}
