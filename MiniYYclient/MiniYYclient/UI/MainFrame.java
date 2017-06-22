package MiniYYclient.UI;

//import CellRenderer;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListCellRenderer;

import MiniYYclient.common.CommonData;
import MiniYYclient.main.MainClass;
import MiniYYclient.model.User;
import MiniYYclient.model.entity.OnlineUserDetail;


public class MainFrame extends JFrame
{


    private JLabel            userNameLab;                             // �û���
    private ChatList          onlineUserList;                          // �����û��б�
    private ChatList          groupList;                               // Ⱥ�б�
    private Font              userNameFont;                            // �û���������ʽ
    private Color             userNameColor;                           // �û���������ɫ
    private JTabbedPane       tabPane;                                 // ��Ⱥ�ĺ͵��Ľ����л�
    private JLabel image; // ͷ��
    private TrayIcon trayIcon = null;  
    private String tubiaoName = new String();
    
    public MainFrame()
    {
        //super("Chat -- " + CommonData.getUserName());
        super("MiniYY");
        // ��ʼ������
        Image logoImage = Toolkit.getDefaultToolkit().getImage("image/tubiao2.png");
        setIconImage(logoImage);
        tubiaoName = "MiniYY:"+MiniYYclient.main.MainClass.cUser.getName()+"("+MiniYYclient.main.MainClass.cUser.getID()+")";
        jbInit();
        intiInterface();
    }

    /**
     * ��ʼ������
     */
    private void intiInterface()
    {
        userNameFont = new Font("��԰", Font.BOLD, 18);
        userNameColor = new Color(130, 150, 250);

        JPanel topPanel = new JPanel();
      
        userNameLab = new JLabel(MainClass.cUser.getName()+"("+MainClass.cUser.getID()+")");
        userNameLab.setFont(userNameFont);
        userNameLab.setBackground(userNameColor);
        
        // ͷ��
        JLabel exjl = new JLabel("          ");
        JLabel exj2 = new JLabel("  ");
        topPanel= new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon ii =new ImageIcon("image/111.png");
        image = new JLabel(ii);
        topPanel.add(exj2);
        topPanel.add(image);
        topPanel.add(exjl);
        topPanel.add(userNameLab);
        topPanel.setBackground(Color.white);

        // ----------- �����û�list ----------//
        JScrollPane userListScrollPane = new JScrollPane();
        onlineUserList = new ChatList();
        onlineUserList.setCellRenderer(new   CellRenderer());
        onlineUserList.addMouseListener(new listMouseListener());
        userListScrollPane.setViewportView(onlineUserList);

        // ----------- Ⱥ��pane ------------//
        JScrollPane groupListScrollPane = new JScrollPane();
        groupList = new ChatList();
        groupList.addMouseListener(new listMouseListener());
        groupList.addItem("Ⱥ����");
        groupListScrollPane.setViewportView(groupList);

        // ----------- tab ----------//
        tabPane = new JTabbedPane();
        tabPane.addTab("һ��һ����", userListScrollPane);
        tabPane.addTab(" Ⱥ���� ", groupListScrollPane);

        
        // ---------------------Item------------------//
        JPanel itemjp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon addimg =new ImageIcon("image/search.jpg");
        JLabel add = new JLabel (addimg);
        itemjp.add(add);
        // ------------------ ------------------------//
        this.getContentPane().setBackground(Color.white);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(tabPane, BorderLayout.CENTER);
        this.getContentPane().add(itemjp, BorderLayout.SOUTH);

        this.setAlwaysOnTop(true); // �ö�
        this.setSize(300, 650);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) ((screenDim.width - 350) / 1.2), 15);
    }

    /**
     * �� CommonData �б���������û������ͬ��
     * �ṩ���ⲿ�����
     */
    public void initUserList(HashMap<String, OnlineUserDetail> OnlineUserMap)
    {
        Iterator<Entry<String, OnlineUserDetail>> iter = OnlineUserMap.entrySet().iterator();

        while (iter.hasNext())
        {
            Entry<String, OnlineUserDetail> entry = (Entry<String, OnlineUserDetail>) iter.next();
            OnlineUserDetail onlineUserDetail = (OnlineUserDetail) entry.getValue();

            // �Լ������
            if (!onlineUserDetail.getUserName().equals(CommonData.getUserName()))
            {
                addUserItem(onlineUserDetail.getUserName(), onlineUserDetail.getUserSex());
            }
        }
    }

    /**
     * ��list������û�
     * 
     * @param nameStr_
     */
    public void addUserItem(String nameStr_,  String userSex_)
    {
    	//JPanel jp12 = new JPanel();
    	//jp12.add(image);
    	//JLabel jlab = new JLabel(nameStr_);
        onlineUserList.addItem(nameStr_);
       // onlineUserList.add(jp12);
    }

    /**
     * ��list���Ƴ��û�
     * 
     * @param nameStr_
     */
    public void removeUserItem(String nameStr_)
    {
        onlineUserList.removeItem(nameStr_);
    }

    /**
     * �����û��б�(��дjlist�ؼ�)
     * 
     * @author ������
     * 
     */
    class ChatList extends JList<Object>
    {
       

        private Vector<String>    onlineUserVector = new Vector<>();      // list
                                                                           // ����

        public ChatList()
        {
            // list�߶�
            this.setFixedCellHeight(60);

            // ������Ⱦ��Ϊ�Զ������Ⱦ��
            this.setCellRenderer(new UserListCellRenderer());
            ImageIcon ii =new ImageIcon("image/1.png");
            JLabel a=new JLabel(ii);
            this.add(a);
            // ����list����

            this.setListData(onlineUserVector);
        }

        /**
         * ��list�ṩ���item�Ľӿ�
         * 
         * @param nameStr_
         */
        public void addItem(String nameStr_)
        {
            onlineUserVector.add(nameStr_);
            this.setListData(onlineUserVector);
        }

        /**
         * ��list�ṩɾ��item�Ľӿ�
         * 
         * @param nameStr_
         */
        public void removeItem(String nameStr_)
        {
            onlineUserVector.remove(nameStr_);
            this.setListData(onlineUserVector);
        }
    }

    /**
     * list ��Ⱦ��
     * 
     * @author ������
     * 
     */
    class UserListCellRenderer extends JPanel implements ListCellRenderer<Object>
    {
      

        private String            text;                                   // list�ϵ�text
        private Color             background;                             // bei
        private Color             foreground;

        public UserListCellRenderer()
        {
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 30));

        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus)
        {
            text = (String) value;
            // background=isSelected?list.getSelectionBackground():list.getBackground();
            background = isSelected ? new Color(180, 189, 224) : new Color(249, 227, 174);
            foreground = isSelected ? list.getSelectionForeground() : list.getForeground();
            return this;
        }

        /**
         * �ػ游�����
         */
        @Override
        public void paintComponent(Graphics g)
        {
            g.setColor(background);
            g.fillRoundRect(0, 5, getWidth(), getHeight() - 10, 30, 30);
            g.setColor(foreground);
            g.drawString(text, (getWidth() - 50) / 2, (getHeight() - 10) / 2); // ���ƶ�λ�û����ı�
        }

        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(130, 120); // Cell�ĳߴ�
        }

    }

    /**
     * list ������
     * 
     * @author ������
     * 
     */
    class listMouseListener extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            JList<?> userList = (JList<?>) e.getSource();
            if (e.getClickCount() == 2)
            {
                int index = userList.locationToIndex(e.getPoint());
                if (index >= 0)
                {
                    // Ⱥ��
                    if (e.getSource() == groupList)
                    {
                        CommonData.getGroupChatFrame().setVisible(true);
                    }

                    // һ��һ����
                    else
                    {
                        String userName = userList.getModel().getElementAt(index).toString().trim();

                        if (CommonData.getChatUserMap().get(userName) == null)
                        {
                            // ��ӵ������û���
                            CommonData.getChatUserMap().put(userName, new ChatFrame(userName));
                        }

                        CommonData.getChatUserMap().get(userName).setVisible(true);
                    }
                }
            }
        }
    }

    
    // JList�пɼ�ͼƬ��
    class   CellRenderer   extends   JLabel   implements   ListCellRenderer   
    {   
          /*��CellRenderer�̳�JLabel��ʵ��ListCellRenderer.������������JLabel���ڲ�ͼ�����ԣ����CellRenderer�̳���JLabel   
            *����JList�е�ÿ����Ŀ����Ϊ��һ��JLabel.   
            */   
            CellRenderer()   
            {   
                    setOpaque(true);   
            }   
            /*�����ﵽ������ʵ��getListCellRendererComponent()����*/   
            public   Component   getListCellRendererComponent(JList   list,   
                                                                                                       Object   value,   
                                                                                                       int   index,   
                                                                                                        boolean   isSelected,   
                                                                                                        boolean   cellHasFocus)   
            {         
                   /*�����ж�list.getModel().getElementAt(index)�����ص�ֵ�Ƿ�Ϊnull,�����ϸ������У���JList�ı�����"�������   
                      *Щ���ݿ����"����index>=4����Ŀֵ����ȫ����Ϊnull.������������У���Ϊ������nullֵ�������û�м��������   
                      *�ϲ�û�й�ϵ.   
                      */   
                    if   (value   !=   null)   
                    {   
                            setText(value.toString());   
                           setIcon(new   ImageIcon("image/mini1.png"));   
                    }   
                   if   (isSelected)   {   
                            setBackground(list.getSelectionBackground());   
                            setForeground(list.getSelectionForeground());   
                    }   
                   else   {   
                            //����ѡȡ��ȡ��ѡȡ��ǰ���뱳����ɫ.   
                            setBackground(list.getBackground());   
                            setForeground(list.getForeground());   
                    }   
                    return   this;   
            }     
    }
    
    // ����
    public void jbInit(){
        if(SystemTray.isSupported()){ //��鵱ǰϵͳ�Ƿ�֧��ϵͳ����
             SystemTray tray = SystemTray.getSystemTray();//��ȡ��ʾ������������ SystemTray ʵ����
             Image image  = Toolkit.getDefaultToolkit().getImage("image/tubiao2.png");
             PopupMenu popupMenu = new PopupMenu();

             MenuItem  exitItem  = new MenuItem("�˳�"); 
             MenuItem  menuItema  = new MenuItem("���Ӻ���"); 
             MenuItem  menuItemb = new MenuItem("menu b"); 
             MenuItem  menuItemc  = new MenuItem("menu c"); 
             MenuItem  menuItemd = new MenuItem("menu d"); 
             exitItem.addActionListener(new  ActionListener(){
                 public void actionPerformed(ActionEvent e)     {   
                     try{     
                          System.exit(0);     
                       }catch(Exception   ex)   {   
                           ex.printStackTrace();   
                       }   
                 }
             });      
             popupMenu.add(menuItema); 
             popupMenu.add(menuItemb); 
             popupMenu.add(menuItemc); 
             popupMenu.add(menuItemd); 
             popupMenu.addSeparator();
             popupMenu.add(exitItem);  
             trayIcon = new TrayIcon(image, tubiaoName,  popupMenu);   
             trayIcon.addMouseListener(new java.awt.event.MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                   if(e.getClickCount()==2){   
                       showIT(true);   
                    }
                }     
             });
             try{   
                  tray.add(trayIcon);  // �� TrayIcon ��ӵ� SystemTray�� 
             } catch   (AWTException   e)     {   
                  System.err.println(e);   
             }
        }else{
            System.out.println("���ϵͳ��֧��ϵͳ����");
        }

    }
    public void showIT(boolean visable){
        if(this.isVisible() != visable)
            this.setVisible(visable);
    }

}
