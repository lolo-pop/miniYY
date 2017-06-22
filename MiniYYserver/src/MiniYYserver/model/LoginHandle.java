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
     * ���ؿͻ���¼������ʵ��
     * 
     * @return
     */
    public static LoginHandle getLoginHandle()
    {
        return LoginHandle.loginHandle;
    }

    /**
     * ����ͻ���¼
     * 
     * @param loginStr_
     * @param serverThread_
     */
    public void clientLoginHandle(String loginStr_, ServerThread serverThread_)
    {
        // msgStr ��ʽΪname:::keyword:::sex
        String[] msgInfo = loginStr_.split(":::");
        clientName = msgInfo[0];
        clientKeyWord = msgInfo[1];
        String clientSex = msgInfo[2];
        System.out.println("IAM SEX"+clientSex);
        // �жϸ��û��Ƿ�Ϸ�
        Iterator<Entry<String, ServerThread>> iter = CommonData.getClientThreadMap().entrySet().iterator();
        while (iter.hasNext())
        {
            Entry<String, ServerThread> entry = (Entry<String, ServerThread>) iter.next();
            
            // ������ͬ���û�
            if (entry.getKey().toString().equals(clientName))
            {
                System.out.println("got the same name, Login failed !! ");
                
                // ��Ϣ��ʽ��msgType:::loginResult
                String sendStr = MessageTypeEnum.LoginReturn.toString() + ":::" + new Integer(0).toString();
                serverThread_.sendData(sendStr);
                
                // �÷����߳� serverThread �˳�ѭ��
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
		        
		        // ��¼�ɹ� ��Ϣ��ʽ��msgType:::loginResult:::NickName
		        String sendStr = MessageTypeEnum.LoginReturn.toString() + ":::" + new Integer(1).toString()+":::"+nickName;
		        serverThread_.sendData(sendStr);

		        // ��ӵ����������û���
		        CommonData.getClientThreadMap().put(clientName, serverThread_);
		        CommonData.getOnlineUserMap().put(clientName, new OnlineUserDetail(clientName, clientSex));

		        // ��Ϣ��ʽ��msgType:::userName:::Sex
		        String sendMsg = MessageTypeEnum.NewClientConnect.toString() + ":::" + clientName +":::"+ clientSex;

		     // �㲥��֪�����û�����
		        serverThread_.broadcast(sendMsg);

		        // ��  OnlineUserMap ����ת�����ַ������з���
		        String OnlineUserMapObjectStr = " ";
		        try
		        {
		            // ���л����������������ByteArrayOutputStream��
		            ByteArrayOutputStream baos = new ByteArrayOutputStream();
		            
		            // ���л�ʹ�õ������
		            ObjectOutputStream oos = new ObjectOutputStream(baos);
		            
		            // ��д�����
		            oos.writeObject(CommonData.getOnlineUserMap());
		            byte[] buf = baos.toByteArray();
		            
		            // ת�����ַ���
		            sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		            OnlineUserMapObjectStr = encoder.encode(buf);
		            
		            oos.close();
		            
		        } catch(IOException e)
		        {
		            e.printStackTrace();
		        }
		        
		        // �������������û���Ϣ�����û�    ��Ϣ��ʽ��msgType:::objectStreamString
		        String sendMsg_2 = MessageTypeEnum.AllOnlineUserDetailReturn.toString() + ":::" + OnlineUserMapObjectStr;
		        serverThread_.sendData(sendMsg_2);
		        
		        System.out.println("finish initialization! user: \"" + clientName + "\" is Listening... ");
		    

		        }
		        else {
		        	
		        	// ��¼ʧ�� ��Ϣ��ʽ��msgType:::loginResult (LoginReturn:::2)
		        	
		        	String sendStr = MessageTypeEnum.LoginReturn.toString() + ":::" + new Integer(2).toString();
		        	System.out.println("RETURNnnn!"+sendStr);
		        	serverThread_.sendData(sendStr);
		        	}
		} catch (Exception e1) {
			// ����DAL���쳣 �˺Ų����ڣ�ֱ�ӷ���  ��ʽLoginReturn:::20
        	String sendStr = MessageTypeEnum.LoginReturn.toString() + ":::" + new Integer(20).toString();
        	serverThread_.sendData(sendStr);
		}

        
    }

    /**
     * �����û�����
     * @param msgStr_ ��Ϣ��ʽ��userName
     */
    public void ClientOffLineHandle(String clientName_, ServerThread serverThread_)
    {
        System.out.println("one client off-line");
        
        // �������û������Ƴ����û�
        CommonData.getOnlineUserMap().remove(clientName_);
        CommonData.getClientThreadMap().remove(clientName_);
        
        // ��Ϣ�ַ�
        String sendMsg = MessageTypeEnum.ClientOffLine.toString() + ":::" + clientName_;
        
        // �㲥�û�����
        serverThread_.broadcast(sendMsg);
    }
}
