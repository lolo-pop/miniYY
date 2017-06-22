package MiniYYclient.thread;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import MiniYYclient.UI.LoginFrame;
import MiniYYclient.common.CommonData;

public class MainClientSocket
{
    private static final MainClientSocket mainSocket = new MainClientSocket(); // 定义本类实例对象
    private static Socket                 socket;                             // 连接对像
    private static ClientServerThread     serverThread;                       // 服务线程
    public static int cishu = 0; // 登录时判断是否需要再次连接服务器
    private MainClientSocket()
    {
        try
        {
            System.out.println(CommonData.getServerIp());
            System.out.println(CommonData.getServerPort());

            // 初始化参数
            socket = new Socket(CommonData.getServerIp().trim(), CommonData.getServerPort());
            serverThread = new ClientServerThread(socket);

        } catch(ConnectException e)
        {
            System.err.println("未能找到服务器");
//            e.printStackTrace();
        } catch(UnknownHostException e)
        {
            e.printStackTrace();
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接类实例
     * 
     * @return 连接类实例
     */
    public static MainClientSocket getMainSocket()
    {
        return mainSocket;
    }

    /**
     * 获取服务线程
     * 
     * @return 服务线程实例
     */
    public ClientServerThread getServerThread()
    {
        return serverThread;
    }

    /**
     * 连接服务器，尝试登入
     * 
     * @param userName_
     * @param userKeyWord_
     * @param sex_
     */
    public void login(String userName_, String userKeyWord_, String sex_)
    {
    	
        // 连接服务器，开户监听
        if(cishu==0) this.getServerThread().start();
        cishu++;
        // sendMsg格式为msgType:::name:::keyword:::sex
        String sendMsg = MessageTypeEnum.Login.toString() + ":::" + userName_ + ":::"+userKeyWord_+":::" + sex_;

        // 发送消息
        this.getServerThread().sendData(sendMsg);


    }
    
    /**
     * 连接服务器，尝试注册账号
     * @param ID
     * @param Name
     * @param password
     * @param sex
     * @param Province
     * @param City
     * @param Question
     * @param Answer
     * @param image
     */
    public void applyID(String id_,String userName_, String userKeyWord_, String sex_, String Province_, String City_, String Question_, String Answer_, String Image_)
    {
    	// 连接服务器，开户监听
        this.getServerThread().start();

        // sendMsg格式为msgType:::ID:::password:::sex:::province:::city:::question:::answer:::image
        String sendMsg = MessageTypeEnum.Apply.toString() + ":::" +id_+":::"+ userName_ + ":::"+userKeyWord_+":::" + sex_+":::"+Province_+":::"+City_+":::"+Question_+":::"+
        Answer_+":::"+Image_;

        // 发送消息
        this.getServerThread().sendData(sendMsg);

    }
    
    public void setCishu()
    {
    	this.cishu++;
    }
    
    public String getCishu()
    {
    	return "ecishu";
    }
}
