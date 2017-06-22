package MiniYYclient.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.swing.JOptionPane;

import MiniYYclient.common.CommonData;
import MiniYYclient.UI.LoginFrame;
import MiniYYclient.UI.MainFrame;
import MiniYYclient.main.MainClass;
import MiniYYclient.model.entity.OnlineUserDetail;


public class LoginHandleBiz
{
    private static final LoginHandleBiz loginHandleBiz = new LoginHandleBiz(); // 定义本类实例对象

    private LoginHandleBiz()
    {

    }

    /**
     * 返回登录结果处理类实例
     * 
     * @return
     */
    public static LoginHandleBiz getLoginHandleBiz()
    {
        return LoginHandleBiz.loginHandleBiz;
    }

    /**
     * 处理登录结果
     * 
     * @param resultStr
     */
    public void loginHandle(String resultStr)
    {
        // resultStr 格式：result
        int resultValue = Integer.parseInt(resultStr.trim().split(":::")[0]);

        // 登录成功
        if (resultValue == 1)
        {
            System.out.println("login successfully! \n I am " + CommonData.getUserName());

            // 创建主窗体实例
            MainClass.cUser.setName(resultStr.trim().split(":::")[1]);
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            
            // 保存全局信息中的主窗体
            CommonData.setMainFrame(mainFrame);
            

        }
        
        // 登陆失败
        else if(resultValue == 2)
        {
        	System.out.println("login failed! \n I am " + CommonData.getUserName());
        	JOptionPane.showMessageDialog(null,"密码输入错误！");
        	LoginFrame lf = new LoginFrame();
        	lf.login_interface();
        }
        
        // 无此用户
        else if(resultValue == 20)
        {
        	System.out.println("login failed! \n I am " + CommonData.getUserName());
        	JOptionPane.showMessageDialog(null,"无此账号！");
        	//MainClass.cUser.setPosition(false);
        }
        // 存在同名用户
        else if (resultValue == 0)
        {
            JOptionPane.showMessageDialog(null, "存在同名用户，请修改用户名后再登录！", "information", JOptionPane.INFORMATION_MESSAGE);
            System.err.println("singnal...");
            return;
        }

        // 登录出错
        else if (resultValue == -1)
        {
            JOptionPane.showMessageDialog(null, "登录出错！", "erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    /**
     * 处理登录后返回的在线用户基本信息表
     * 
     * @param msgStr_
     *            格式：objectString
     */
    public void onlineUserDetailReturnHandle(String msgStr_)
    {
        byte[] buf;// 缓冲区

        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try
        {
            // 解码字符串,转换成字节数组
            buf = decoder.decodeBuffer(msgStr_);

            // 字节数组转换成流
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buf));

            // 读取对象
            HashMap<String, OnlineUserDetail> OnlineUserMap = (HashMap<String, OnlineUserDetail>) ois.readObject();

            // 同步本地信息
            CommonData.setOnlineUserMap(OnlineUserMap);

            // 初始化在线用户
            CommonData.getMainFrame().initUserList(OnlineUserMap);
        } catch(IOException e)
        {
            e.printStackTrace();
        } catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 处理有新用户上线
     * 
     * @param msgStr_
     *            消息格式：userName:::Sex
     */
    public void newClientConnectHandle(String msgStr_)
    {
        String[] msgInfos = msgStr_.split(":::");
        String userName = msgInfos[0];
        String userSex = msgInfos[1];

        // 添加到主界面中
        CommonData.getMainFrame().addUserItem(userName, userSex);
    }

    /**
     * 处理用户下线
     * 
     * @param msgStr_
     *            消息格式：userName
     */
    public void ClientOffLineHandle(String msgStr_)
    {
        String[] msgInfos = msgStr_.split(":::");
        String userName = msgInfos[0];

        CommonData.getMainFrame().removeUserItem(userName);
    }
}
