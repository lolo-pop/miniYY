package MiniYYclient.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;


import MiniYYclient.common.CommonData;
import MiniYYclient.thread.MainClientSocket;
import MiniYYclient.thread.MessageTypeEnum;

public class ChatFrame extends JFrame implements ActionListener
{

    private JLabel            titleLab;                                // 标题
    private JTextArea         inputTA;                                 // 输入框
    private ChatPanel         chatContentPanel;                        // 聊天内容显示框
    private ControllToolbar   controllToolbar;                         // 工具条
    private JButton           sendMsgBtn;                              // 发送按钮
    private JButton           closeBtn;                                // 关闭按钮
    private String            targetUser;                              // 正在聊天的目标用户
    private boolean           isSingleChat;                            // 是否是一对一聊天（false群聊）
    private Font              inputFont;                               // 输入框字体格式
    private String            chatRecordsStr;                          // 聊天记录
    private ChatFrame         parentFrame;                             // 供子控件在内部类里调用
    private JScrollBar   jb;
    // 群聊入口
    public ChatFrame()
    {
        isSingleChat = false;
        this.targetUser = "";
        
        // 设置标题
        titleLab = new JLabel("<html><span style='font-weight:bold;font-size:14px;'>Group Chat</span></html>");

        // 初始化界面
        intiInterface();
        
        // 初始化基本参数
        initBasicData();
    }

    // 一对一聊天（单聊入口）
    public ChatFrame(String name_)
    {
        isSingleChat = true;
        this.targetUser = name_;
        
        // 设置标题
        titleLab = new JLabel("<html>与  <font color = blue>" + targetUser + "</font> 聊天 </html>");

        // 初始化界面
        intiInterface();

        // 初始化基本参数
        initBasicData();
    }

    /**
     * 初始化界面
     */
    private void intiInterface()
    {
        // ----------- 北方标题 ------------//
        JPanel northPanel = new JPanel();
        northPanel.add(titleLab);
        northPanel.setBackground(Color.white);

        // ---------- 中部显示内容 ----------//
        JPanel centerPanel = new JPanel(new BorderLayout());
        // message
        chatContentPanel = new ChatPanel();
        JScrollPane msgScrollPane = new JScrollPane(chatContentPanel);
        msgScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        msgScrollPane.setBorder(new TitledBorder("消息"));
        jb   =   msgScrollPane.getVerticalScrollBar(); 
        jb.setValue(jb.getMaximum()); 
        jb.setValue(jb.getMaximum()); 
        // center south panel ( tool bar + input )
        JPanel centerSouthPanel = new JPanel(new BorderLayout());
        inputFont = new Font(null, Font.PLAIN, 12);
        controllToolbar = new ControllToolbar();
        inputTA = new JTextArea(4, 0);
        inputTA.setAutoscrolls(true);
        JScrollPane inputScroll = new JScrollPane(inputTA);
        inputScroll.setBorder(new TitledBorder("请输入"));
        inputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        centerSouthPanel.setBackground(Color.white);
        centerSouthPanel.add(controllToolbar, BorderLayout.NORTH);
        centerSouthPanel.add(inputScroll, BorderLayout.SOUTH);
        inputTA.setLineWrap(true);
        inputTA.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                // Ctrl + enter 换行
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    inputTA.setText(inputTA.getText() + "\n");
                }

                // enter 发送
                else if (!e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    sendMsgBtn.doClick();
                    e.setKeyChar('\0');
                }
            }
        });

        centerPanel.add(msgScrollPane, BorderLayout.CENTER);
        centerPanel.add(centerSouthPanel, BorderLayout.SOUTH);

        msgScrollPane.setBackground(Color.white);
        inputScroll.setBackground(Color.white);
        centerPanel.setBackground(Color.white);

        // ----------- 南方按钮 ------------//
        JPanel southPanel = new JPanel();
        sendMsgBtn = new JButton("发送");
        closeBtn = new JButton("关闭");
        sendMsgBtn.setActionCommand("send");
        sendMsgBtn.addActionListener(this);
        closeBtn.setActionCommand("close");
        closeBtn.addActionListener(this);
        southPanel.add(sendMsgBtn);
        southPanel.add(closeBtn);
        southPanel.setBackground(Color.white);

        // ----------- 东方图片------------//
//        JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // ------------------ ------------------------//
        this.getContentPane().setBackground(Color.white);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(northPanel, BorderLayout.NORTH);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(southPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 默认点关闭按钮时退出程序
        this.setMinimumSize(new Dimension(550, 500));
        this.setSize(550, 500);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenDim.width - 550) / 2, (screenDim.height - 500) / 2); // 屏幕居中
        
        // 添加关闭事件
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
                eixtCurrentChat(); 
            }
        });
    }

    /**
     * 初始化基本参数
     */
    private void initBasicData()
    {
        // 窗体标题
        this.setTitle("我是 " + CommonData.getUserName());
        
        // 给聊天记录赋初值
        this.chatRecordsStr = "";

        // 初始化父类窗体
        this.parentFrame = this;
        
        // 播放有新消息声音
//        Sounds.getSounds().startMessageAudio(); // 不在这播放，改成在接收消息时播放
        
    }
    
    /**
     * 退出当前聊天
     */
    private void eixtCurrentChat()
    {
        // 若是不是群聊则从聊天用户表中移除对应项
        if(isSingleChat)
        {
//            CommonData.getChatUserMap().remove(targetUser);
        }
        
        parentFrame.dispose();
    }
    
    /**
     * 监听器
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String actionStr = e.getActionCommand();
        switch (actionStr)
        {
            case "send":
            {
                String inputText = inputTA.getText().trim();

                if (!inputText.equals(""))
                {
                    String fontStyle = "";
                    fontStyle += inputFont.isBold() ? ("font-weight:bold;") : "";
                    fontStyle += inputFont.isItalic() ? ("font-style:italic;") : "";
                    String chatContent = "<span style='font-size:" + inputFont.getSize() + "px;" + fontStyle + "'>"
                            + inputText + "</span>";
                    String formatContent = ""; // 格式化的聊天内容
                    
                    // 发送至服务器的消息
                    String sendMsg = "";

                    // 设置日期格式
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // 获取当前系统时间
                    String timeStr = df.format(new Date());

                    // 格式化聊天内容
                    formatContent = "<html>" + "<font color = green>" + CommonData.getUserName() + "  " + timeStr
                            + " :</font><br>" + chatContent + "</html>";

                    // 一对一聊天
                    if (isSingleChat)
                    {
                        // 消息格式：msgType:::fromUser:::toUser:::formatContent
                        sendMsg = MessageTypeEnum.SingleChat.toString() + ":::" + CommonData.getUserName() + ":::"
                                + targetUser + ":::" + formatContent;
                    }
                    // 群聊
                    else
                    {
                        // 消息格式：msgType:::fromUser:::formatContent
                        sendMsg = MessageTypeEnum.GroupChat.toString() + ":::" + CommonData.getUserName() + ":::"
                                + formatContent;
                    }

                    // 添加聊天内容到本地聊天窗口
                    addChatContent("<html><font color = blue>" + CommonData.getUserName() + " (me) :</font><br>"
                            + chatContent + "</html>");

                    // 发送消息至服务器
                    MainClientSocket.getMainSocket().getServerThread().sendData(sendMsg);

                    // 清空聊天窗口
                    inputTA.setText("");

                } else
                {
                    JOptionPane.showMessageDialog(this, "消息不完整", "infor",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                break;
            }

            case "close":
            {
            	this.setVisible(false);
                break;
            }

            default:
                break;
        }
    }

    /**
     * 添加聊天内容
     * 
     * @param chatContent_
     */
    public void addChatContent(String chatContent_)
    {
        chatContentPanel.addContent(chatContent_);

        // 添加到聊天记录字符串里
        chatRecordsStr += (chatContent_ + "<br>");
        
    }

    /**
     * 重写的聊天panel
     * 
     * @author 于晓鹏
     * 
     */
    class ChatPanel extends JPanel
    {


        public ChatPanel()
        {
            initInterface();
        }

        private void initInterface()
        {
            // 背景
            this.setBackground(Color.white);

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }

        /**
         * 供外部使用的添加内容接口
         * 
         * @param content_
         */
        public void addContent(String content_)
        {
            
            // 先创一个label
            JLabel dialogLab = new JLabel(content_);

            // 左对齐
            dialogLab.setAlignmentX(Component.LEFT_ALIGNMENT);

            // 添加到面板里
            this.add(dialogLab);

            jb.setValue(jb.getMaximum()); 
            jb.setValue(jb.getMaximum()); 
            // 刷新界面
            this.updateUI();
        }

    }

    /**
     * 重写工具栏
     * 
     * @author 于晓鹏
     * 
     */
    class ControllToolbar extends JToolBar implements ActionListener, ItemListener
    {
       

        public ControllToolbar()
        {
            this.intiInterface();
        }

        /**
         * 初始化工具栏
         */
        public void intiInterface()
        {
            try
            {
                // 发送表情
                JButton expressionBtn;
                expressionBtn = new JButton("发送表情", new ImageIcon(URLDecoder.decode(this.getClass().getResource("/").toString() + "/imgs/zk.gif", "utf-8")));
                
                // font size
                JLabel fontSizeLab = new JLabel("  字体大小: ");
                JComboBox<String> fontSizeCombbox = new JComboBox<>();
                fontSizeCombbox.addItem("10");
                fontSizeCombbox.addItem("12");
                fontSizeCombbox.addItem("14");
                fontSizeCombbox.addItem("16");
                fontSizeCombbox.addItem("18");
                fontSizeCombbox.addItem("22");
                fontSizeCombbox.setSelectedItem("12"); // 默认大小为 12
                
                // font style
                JButton fontBoldBtn = new JButton("加粗");
                JButton fontItalicBtn = new JButton("斜体");
                
                // save records
                JButton saveRecordsBtn = new JButton("保存聊天记录到文件");
                
                // layout
                this.setLayout(new FlowLayout(LEADING, 10, 5));
                
                this.add(fontSizeLab);
                this.add(fontSizeCombbox);
                this.add(fontBoldBtn);
                this.add(fontItalicBtn);
                this.add(expressionBtn);
                this.add(saveRecordsBtn);
                
                // 添加监听
                fontBoldBtn.addActionListener(this);
                fontItalicBtn.addActionListener(this);
                saveRecordsBtn.addActionListener(this);
                expressionBtn.addActionListener(this);
                fontSizeCombbox.addItemListener(this);
                fontBoldBtn.setActionCommand("SetFontBlod");
                fontItalicBtn.setActionCommand("SetFontItalic");
                expressionBtn.setActionCommand("SendExpression");
                saveRecordsBtn.setActionCommand("SaveRecords");
                
                // 设置不可浮动
                this.setFloatable(false);
                
            } catch(UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }

        /**
         * 工具栏事件
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String actionStr = e.getActionCommand();
            switch (actionStr)
            {
                case "SetFontBlod":
                {
                    // 若原本是粗体
                    if (inputFont.isBold())
                    {
                        // 若原本是斜体
                        if (inputFont.isItalic())
                        {
                            // 改成只是斜体
                            inputFont = new Font(inputFont.getFamily(), Font.ITALIC, inputFont.getSize());
                        } else
                        {
                            // 改成无 style
                            inputFont = new Font(inputFont.getFamily(), Font.PLAIN, inputFont.getSize());
                        }
                    }
                    // 若原本不是粗体
                    else
                    {
                        // 若原本是斜体
                        if (inputFont.isItalic())
                        {
                            // 改成粗斜体
                            inputFont = new Font(inputFont.getFamily(), Font.ITALIC | Font.BOLD, inputFont.getSize());
                        } else
                        {
                            // 改成只是粗体
                            inputFont = new Font(inputFont.getFamily(), Font.BOLD, inputFont.getSize());
                        }
                    }

                    // 应用到输入框
                    inputTA.setFont(inputFont);

                    break;
                }

                case "SetFontItalic":
                {
                    // 若原本是斜体
                    if (inputFont.isItalic())
                    {
                        // 若原本是粗体
                        if (inputFont.isBold())
                        {
                            // 改成只是粗体
                            inputFont = new Font(inputFont.getFamily(), Font.BOLD, inputFont.getSize());
                        } else
                        {
                            // 改成无 style
                            inputFont = new Font(inputFont.getFamily(), Font.PLAIN, inputFont.getSize());
                        }
                    }
                    // 若原本不是斜体
                    else
                    {
                        // 若原本是粗体
                        if (inputFont.isBold())
                        {
                            // 改成粗斜体
                            inputFont = new Font(inputFont.getFamily(), Font.ITALIC | Font.BOLD, inputFont.getSize());
                        } else
                        {
                            // 改成只是斜体
                            inputFont = new Font(inputFont.getFamily(), Font.ITALIC, inputFont.getSize());
                        }
                    }

                    // 应用到输入框
                    inputTA.setFont(inputFont);

                    break;
                }

                case "SaveRecords":
                {
                    try
                    {
                        // 选择保存路径
                        FileDialog saveRecordsFD;
                        if (isSingleChat)
                        {
                            saveRecordsFD = new FileDialog(CommonData.getChatUserMap().get(targetUser), "Save Records",
                                    FileDialog.SAVE);
                        } else
                        {
                            saveRecordsFD = new FileDialog(CommonData.getGroupChatFrame(), "Save Records",
                                    FileDialog.SAVE);
                        }
                        saveRecordsFD.setFile("records.html"); // 设置保存名
                        saveRecordsFD.setVisible(true);
                        if (saveRecordsFD.getDirectory() == null)
                        {
                            break;
                        }

                        // 写入记录文件
                        String recordPath = saveRecordsFD.getDirectory() + saveRecordsFD.getFile();
                        RandomAccessFile raf = new RandomAccessFile(new java.io.File(recordPath), "rw");
                        byte[] recordsArray = chatRecordsStr.getBytes();
                        raf.write(recordsArray);
                        raf.close();

                    } catch(FileNotFoundException e1)
                    {
                        e1.printStackTrace();
                    } catch(IOException e1)
                    {
                        e1.printStackTrace();
                    }

                    break;
                }

                case "SendExpression":
                {
                    ExpressionPanel expressionPanel = new ExpressionPanel();
                    JDialog dialog = new JDialog();
                    dialog.setUndecorated(true);
                    dialog.setModal(false);
                    dialog.getContentPane().add(expressionPanel, BorderLayout.CENTER);
                    dialog.setSize(200, 150);
                    int x = ((JButton)e.getSource()).getLocation().x + controllToolbar.getLocation().x + parentFrame.getLocation().x;
                    int y = (parentFrame.getLocation().y + parentFrame.getSize().height) - 370;
                    dialog.setLocation(x, y);
                    dialog.setVisible(true);
                    
                    // 让表情面板失去焦点就关闭
                    dialog.addWindowFocusListener(new WindowAdapter()
                    {
                        public void windowLostFocus(WindowEvent e)
                        {
                            ((JDialog)e.getSource()).dispose();
                        };
                    });
                    
                    break;
                }
                default:
                    break;
            }
        }

        /**
         * 字体大小
         * 
         * @param e
         */
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            inputFont = new Font(inputFont.getFamily(), inputFont.getStyle(), Integer.parseInt(e.getItem().toString()));

            // 应用到输入框
            inputTA.setFont(inputFont);
        }
    }

    /**
     * 表情面板
     * @author 于晓鹏
     *
     */
    class ExpressionPanel extends JPanel implements MouseListener
    {
   
        public ExpressionPanel()
        {
            initInterface();
        }
        
        private void initInterface()
        {
            this.setLayout(new GridLayout(4, 5, 5, 5));
            
            for (String path : CommonData.getExpressionPathVector())
            {
                JLabel label = new JLabel(new ImageIcon(path));
                label.addMouseListener(this);
                label.setBorder(new TitledBorder(""));
                this.add(label);
            }
            
            this.setBorder(new TitledBorder("Expression"));
            this.setBackground(Color.white);
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
//            dispose();
        }

        @Override
        public void mousePressed(MouseEvent e){}

        @Override
        public void mouseReleased(MouseEvent e){}

        @Override
        public void mouseEntered(MouseEvent e)
        {
            // TODO 自动生成的方法存根
            
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            // TODO 自动生成的方法存根
            
        }
    
    }}
