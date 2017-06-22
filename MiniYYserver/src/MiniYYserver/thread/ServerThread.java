package MiniYYserver.thread;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map.Entry;

import MiniYYserver.common.CommonData;
import MiniYYserver.thread.MessageTypeEnum;
import MiniYYserver.model.ApplyHandle;
import MiniYYserver.model.ChatHandleBiz;
import MiniYYserver.model.LoginHandle;

public class ServerThread extends Thread
{
    private Socket           socket       = null;
    private DataInputStream  dis          = null;
    private DataOutputStream dos          = null;
    private String           separatorStr = "";   // 消息分隔符
    private boolean          isListen     = false;
    private String           clientName   = "";   // 该线程服务的客户端用户名
    private String           clientKeyWord =""; //客户端密码
    public ServerThread(Socket socket_)
    {
        try
        {
            this.socket = socket_;

            // 初始化消息定界符
            separatorStr = ":::";

            // 初始化输入输出流
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 向除拥有本实例以外的所有用户发送广播消息
     */
    public void broadcast(String msgStr)
    {
        // 遍历在线用户表
        Iterator<Entry<String, ServerThread>> iter = CommonData.getClientThreadMap().entrySet().iterator();

        while (iter.hasNext())
        {
            Entry<String, ServerThread> entry = (Entry<String, ServerThread>) iter.next();
            ServerThread clientThread = (ServerThread) entry.getValue();
            if (clientThread != this)
            {
                clientThread.sendData(msgStr);
            }
            // String key = entry.getKey().toString();
        }
    }

    /**
     * 向该客户端发送消息
     * 
     * @param socket_
     * @param msgStr
     */
    public void sendData(String msgStr_)
    {
        try
        {
            dos.writeUTF(msgStr_);

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取本实例的socket对象
     * 
     * @return
     */
    public Socket getSocket()
    {
        return this.socket;
    }

    /**
     * 设置是否断续监听
     * @param value
     */
    public void setListen(boolean value)
    {
        this.isListen = value;
    }
    
    @Override
    public void run()
    {
        try
        {
            // 消息内容
            String msgStr = "";

            // 分隔符在字符串中的位置
            int separatorIndex = 0;

            // 是否接收消息
            isListen = true;

            while (isListen)
            {
                // 接收消息
                msgStr = dis.readUTF();

                // 确定分隔符在字符串中的位置
                separatorIndex = msgStr.indexOf(separatorStr);

                // 提取消息类型
                MessageTypeEnum msgType = MessageTypeEnum.valueOf(msgStr.substring(0, separatorIndex));
                switch (msgType)
                {

                /***************** 初始化客户信息 ******************/
                    case Login:
                    {
                        // msgStr 格式为msgtype:::name:::keyword:::sex
                        clientName = msgStr.substring(separatorIndex + 3).split(":::")[0];
                        clientKeyWord = msgStr.substring(separatorIndex +3).split(":::")[1];

                        // 交给biz层处理
                        LoginHandle.getLoginHandle().clientLoginHandle(msgStr.substring(separatorIndex + 3), this);

                        break;
                    }

                    /***************** 群聊消息 ******************/
                    case GroupChat:
                    {
                        // 交给biz层处理
                        ChatHandleBiz.getChatHandleBiz().groupChatHandle(msgStr.substring(separatorIndex + 3));

                        break;
                    }

                    /***************** 一对一聊天消息 ******************/
                    case SingleChat:
                    {
                    	
                        // 交给biz层处理
                        ChatHandleBiz.getChatHandleBiz().singleChatHandle(msgStr.substring(separatorIndex + 3));
                        
                        break;
                    }
                    
                    /************ 注册新账号 ************/
                    case Apply:
                    {
                    	// sendMsg格式为msgType:::ID:::password:::sex:::province:::city:::question:::answer:::image
                    	// 交给biz层处理
                    	ApplyHandle.getApplyHandle().clientApplyHandle(msgStr.substring(separatorIndex + 3),this);
                    	System.out.println("ServerThread Apply！"+msgStr);
                    }
                    default:
                        break;
                }
            }

        } catch(SocketException e)
        {
            try
            {
                // 处理用户下线
                LoginHandle.getLoginHandle().ClientOffLineHandle(clientName, this);
                
                // 关闭流和连接
                dos.flush();
                dos.close();
                dis.close();
                socket.close();
                
            } catch(IOException e1)
            {
                e1.printStackTrace();
            }
//            e.printStackTrace();
            
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
