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


    private JLabel            userNameLab;                             // 用户名
    private ChatList          onlineUserList;                          // 在线用户列表
    private ChatList          groupList;                               // 群列表
    private Font              userNameFont;                            // 用户名字体样式
    private Color             userNameColor;                           // 用户名字体颜色
    private JTabbedPane       tabPane;                                 // 对群聊和单聊进行切换
    private JLabel image; // 头像
    private TrayIcon trayIcon = null;  
    private String tubiaoName = new String();
    
    public MainFrame()
    {
        //super("Chat -- " + CommonData.getUserName());
        super("MiniYY");
        // 初始化界面
        Image logoImage = Toolkit.getDefaultToolkit().getImage("image/tubiao2.png");
        setIconImage(logoImage);
        tubiaoName = "MiniYY:"+MiniYYclient.main.MainClass.cUser.getName()+"("+MiniYYclient.main.MainClass.cUser.getID()+")";
        jbInit();
        intiInterface();
    }

    /**
     * 初始化界面
     */
    private void intiInterface()
    {
        userNameFont = new Font("幼园", Font.BOLD, 18);
        userNameColor = new Color(130, 150, 250);

        JPanel topPanel = new JPanel();
      
        userNameLab = new JLabel(MainClass.cUser.getName()+"("+MainClass.cUser.getID()+")");
        userNameLab.setFont(userNameFont);
        userNameLab.setBackground(userNameColor);
        
        // 头像
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

        // ----------- 在线用户list ----------//
        JScrollPane userListScrollPane = new JScrollPane();
        onlineUserList = new ChatList();
        onlineUserList.setCellRenderer(new   CellRenderer());
        onlineUserList.addMouseListener(new listMouseListener());
        userListScrollPane.setViewportView(onlineUserList);

        // ----------- 群聊pane ------------//
        JScrollPane groupListScrollPane = new JScrollPane();
        groupList = new ChatList();
        groupList.addMouseListener(new listMouseListener());
        groupList.addItem("群聊天");
        groupListScrollPane.setViewportView(groupList);

        // ----------- tab ----------//
        tabPane = new JTabbedPane();
        tabPane.addTab("一对一聊天", userListScrollPane);
        tabPane.addTab(" 群聊天 ", groupListScrollPane);

        
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

        this.setAlwaysOnTop(true); // 置顶
        this.setSize(300, 650);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) ((screenDim.width - 350) / 1.2), 15);
    }

    /**
     * 与 CommonData 中保存的在线用户表进行同步
     * 提供给外部类调用
     */
    public void initUserList(HashMap<String, OnlineUserDetail> OnlineUserMap)
    {
        Iterator<Entry<String, OnlineUserDetail>> iter = OnlineUserMap.entrySet().iterator();

        while (iter.hasNext())
        {
            Entry<String, OnlineUserDetail> entry = (Entry<String, OnlineUserDetail>) iter.next();
            OnlineUserDetail onlineUserDetail = (OnlineUserDetail) entry.getValue();

            // 自己不添加
            if (!onlineUserDetail.getUserName().equals(CommonData.getUserName()))
            {
                addUserItem(onlineUserDetail.getUserName(), onlineUserDetail.getUserSex());
            }
        }
    }

    /**
     * 在list内添加用户
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
     * 在list内移除用户
     * 
     * @param nameStr_
     */
    public void removeUserItem(String nameStr_)
    {
        onlineUserList.removeItem(nameStr_);
    }

    /**
     * 在线用户列表(重写jlist控件)
     * 
     * @author 于晓鹏
     * 
     */
    class ChatList extends JList<Object>
    {
       

        private Vector<String>    onlineUserVector = new Vector<>();      // list
                                                                           // 内容

        public ChatList()
        {
            // list高度
            this.setFixedCellHeight(60);

            // 设置渲染器为自定义的渲染器
            this.setCellRenderer(new UserListCellRenderer());
            ImageIcon ii =new ImageIcon("image/1.png");
            JLabel a=new JLabel(ii);
            this.add(a);
            // 设置list内容

            this.setListData(onlineUserVector);
        }

        /**
         * 给list提供添加item的接口
         * 
         * @param nameStr_
         */
        public void addItem(String nameStr_)
        {
            onlineUserVector.add(nameStr_);
            this.setListData(onlineUserVector);
        }

        /**
         * 给list提供删除item的接口
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
     * list 渲染器
     * 
     * @author 于晓鹏
     * 
     */
    class UserListCellRenderer extends JPanel implements ListCellRenderer<Object>
    {
      

        private String            text;                                   // list上的text
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
         * 重绘父类面板
         */
        @Override
        public void paintComponent(Graphics g)
        {
            g.setColor(background);
            g.fillRoundRect(0, 5, getWidth(), getHeight() - 10, 30, 30);
            g.setColor(foreground);
            g.drawString(text, (getWidth() - 50) / 2, (getHeight() - 10) / 2); // 在制定位置绘制文本
        }

        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(130, 120); // Cell的尺寸
        }

    }

    /**
     * list 监听器
     * 
     * @author 于晓鹏
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
                    // 群聊
                    if (e.getSource() == groupList)
                    {
                        CommonData.getGroupChatFrame().setVisible(true);
                    }

                    // 一对一聊天
                    else
                    {
                        String userName = userList.getModel().getElementAt(index).toString().trim();

                        if (CommonData.getChatUserMap().get(userName) == null)
                        {
                            // 添加到聊天用户表
                            CommonData.getChatUserMap().put(userName, new ChatFrame(userName));
                        }

                        CommonData.getChatUserMap().get(userName).setVisible(true);
                    }
                }
            }
        }
    }

    
    // JList中可加图片类
    class   CellRenderer   extends   JLabel   implements   ListCellRenderer   
    {   
          /*类CellRenderer继承JLabel并实作ListCellRenderer.由于我们利用JLabel易于插图的特性，因此CellRenderer继承了JLabel   
            *可让JList中的每个项目都视为是一个JLabel.   
            */   
            CellRenderer()   
            {   
                    setOpaque(true);   
            }   
            /*从这里到结束：实作getListCellRendererComponent()方法*/   
            public   Component   getListCellRendererComponent(JList   list,   
                                                                                                       Object   value,   
                                                                                                       int   index,   
                                                                                                        boolean   isSelected,   
                                                                                                        boolean   cellHasFocus)   
            {         
                   /*我们判断list.getModel().getElementAt(index)所返回的值是否为null,例如上个例子中，若JList的标题是"你玩过哪   
                      *些数据库软件"，则index>=4的项目值我们全都设为null.而在这个例子中，因为不会有null值，因此有没有加上这个判   
                      *断并没有关系.   
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
                            //设置选取与取消选取的前景与背景颜色.   
                            setBackground(list.getBackground());   
                            setForeground(list.getForeground());   
                    }   
                    return   this;   
            }     
    }
    
    // 托盘
    public void jbInit(){
        if(SystemTray.isSupported()){ //检查当前系统是否支持系统托盘
             SystemTray tray = SystemTray.getSystemTray();//获取表示桌面托盘区的 SystemTray 实例。
             Image image  = Toolkit.getDefaultToolkit().getImage("image/tubiao2.png");
             PopupMenu popupMenu = new PopupMenu();

             MenuItem  exitItem  = new MenuItem("退出"); 
             MenuItem  menuItema  = new MenuItem("增加好友"); 
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
                  tray.add(trayIcon);  // 将 TrayIcon 添加到 SystemTray。 
             } catch   (AWTException   e)     {   
                  System.err.println(e);   
             }
        }else{
            System.out.println("你的系统不支持系统托盘");
        }

    }
    public void showIT(boolean visable){
        if(this.isVisible() != visable)
            this.setVisible(visable);
    }

}
