package MiniYYserver.model;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.Iterator;
import java.util.Map.Entry;

import MiniYYclient.model.entity.OnlineUserDetail;
import MiniYYserver.common.CommonData;
import MiniYYserver.thread.MessageTypeEnum;
import MiniYYserver.thread.ServerThread;
import MiniYYserver.DAL.*;

public class LoginHandle
{
    private static final LoginHandle loginHandle = new LoginHandle();
    String clientName = new String("");
    String clientKeyWord = new String("");
    String passWord = new String("");

    public LoginHandle()
    {

    }

    /**
     * 返回客户登录处理类实例
     * 
     * @return
     */
    public static LoginHandle getLoginHandle()
    {
        return LoginHandle.loginHandle;
    }

    /**
     * 处理客户登录
     * 
     * @param loginStr_
     * @param serverThread_
     */
    public void clientLoginHandle(String loginStr_, ServerThread serverThread_)
    {
        // msgStr 格式为name:::keyword:::sex
        String[] msgInfo = loginStr_.split(":::");
        clientName = msgInfo[0];
        clientKeyWord = msgInfo[1];
        String clientSex = msgInfo[2];
        System.out.println("IAM SEX"+clientSex);
        // 判断该用户是否合法
        Iterator<Entry<String, ServerThread>> iter = CommonData.getClientThreadMap().entrySet().iterator();
        while (iter.hasNext())
        {
            Entry<String, ServerThread> entry = (Entry<String, ServerThread>) iter.next();
            
            // 若存在同名用户
            if (entry.getKey().toString().equals(clientName))
            {
                System.out.println("got the same name, Login failed !! ");
                
                // 消息格式：msgType:::loginResult
                String sendStr = MessageTypeEnum.LoginReturn.toString() + ":::" + new Integer(0).toString();
                serverThread_.sendData(sendStr);
                
                // 让服务线程 serverThread 退出循环
                serverThread_.setListen(false);
//                Thread.currentThread().interrupt();
                
                return;
            }
        }

        SqlExecute sql = new SqlExecute();
        
        try {
			passWord = new String (sql.getPassWord(clientName));
			
			if(clientKeyWord.equals(passWord)){
	        	
		        String nickName = new String();
		        nickName=sql.getNickName(clientName);
		        System.out.println("Login successfully, initializing... ");
		        
		        // 登录成功 消息格式：msgType:::loginResult:::NickName
		        String sendStr = MessageTypeEnum.LoginReturn.toString() + ":::" + new Integer(1).toString()+":::"+nickName;
		        serverThread_.sendData(sendStr);

		        // 添加到所有在线用户表
		        CommonData.getClientThreadMap().put(clientName, serverThread_);
		        CommonData.getOnlineUserMap().put(clientName, new OnlineUserDetail(clientName, clientSex));

		        // 消息格式：msgType:::userName:::Sex
		        String sendMsg = MessageTypeEnum.NewClientConnect.toString() + ":::" + clientName +":::"+ clientSex;

		     // 广播告知有新用户上线
		        serverThread_.broadcast(sendMsg);

		        // 将  OnlineUserMap 对象转化成字符串进行发送
		        String OnlineUserMapObjectStr = " ";
		        try
		        {
		            // 序列化后的数据流保存在ByteArrayOutputStream中
		            ByteArrayOutputStream baos = new ByteArrayOutputStream();
		            
		            // 序列化使用的输出流
		            ObjectOutputStream oos = new ObjectOutputStream(baos);
		            
		            // 将写入对象
		            oos.writeObject(CommonData.getOnlineUserMap());
		            byte[] buf = baos.toByteArray();
		            
		            // 转换成字符串
		            sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		            OnlineUserMapObjectStr = encoder.encode(buf);
		            
		            oos.close();
		            
		        } catch(IOException e)
		        {
		            e.printStackTrace();
		        }
		        
		        // 发送所有在线用户信息给该用户    消息格式：msgType:::objectStreamString
		        String sendMsg_2 = MessageTypeEnum.AllOnlineUserDetailReturn.toString() + ":::" + OnlineUserMapObjectStr;
		        serverThread_.sendData(sendMsg_2);
		        
		        System.out.println("finish initialization! user: \"" + clientName + "\" is Listening... ");
		    

		        }
		        else {
		        	
		        	// 登录失败 消息格式：msgType:::loginResult (LoginReturn:::2)
		        	
		        	String sendStr = MessageTypeEnum.LoginReturn.toString() + ":::" + new Integer(2).toString();
		        	System.out.println("RETURNnnn!"+sendStr);
		        	serverThread_.sendData(sendStr);
		        	}
		} catch (Exception e1) {
			// 处理DAL层异常 账号不存在，直接返回  格式LoginReturn:::20
        	String sendStr = MessageTypeEnum.LoginReturn.toString() + ":::" + new Integer(20).toString();
        	serverThread_.sendData(sendStr);
		}

        
    }

    /**
     * 处理用户下线
     * @param msgStr_ 消息格式：userName
     */
    public void ClientOffLineHandle(String clientName_, ServerThread serverThread_)
    {
        System.out.println("one client off-line");
        
        // 在在线用户表中移除该用户
        CommonData.getOnlineUserMap().remove(clientName_);
        CommonData.getClientThreadMap().remove(clientName_);
        
        // 消息字符
        String sendMsg = MessageTypeEnum.ClientOffLine.toString() + ":::" + clientName_;
        
        // 广播用户下线
        serverThread_.broadcast(sendMsg);
    }
}
