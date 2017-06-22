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
    private String           separatorStr = "";   // ��Ϣ�ָ���
    private boolean          isListen     = false;
    private String           clientName   = "";   // ���̷߳���Ŀͻ����û���
    private String           clientKeyWord =""; //�ͻ�������
    public ServerThread(Socket socket_)
    {
        try
        {
            this.socket = socket_;

            // ��ʼ����Ϣ�����
            separatorStr = ":::";

            // ��ʼ�����������
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ���ӵ�б�ʵ������������û����͹㲥��Ϣ
     */
    public void broadcast(String msgStr)
    {
        // ���������û���
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
     * ��ÿͻ��˷�����Ϣ
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
     * ��ȡ��ʵ����socket����
     * 
     * @return
     */
    public Socket getSocket()
    {
        return this.socket;
    }

    /**
     * �����Ƿ��������
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
            // ��Ϣ����
            String msgStr = "";

            // �ָ������ַ����е�λ��
            int separatorIndex = 0;

            // �Ƿ������Ϣ
            isListen = true;

            while (isListen)
            {
                // ������Ϣ
                msgStr = dis.readUTF();

                // ȷ���ָ������ַ����е�λ��
                separatorIndex = msgStr.indexOf(separatorStr);

                // ��ȡ��Ϣ����
                MessageTypeEnum msgType = MessageTypeEnum.valueOf(msgStr.substring(0, separatorIndex));
                switch (msgType)
                {

                /***************** ��ʼ���ͻ���Ϣ ******************/
                    case Login:
                    {
                        // msgStr ��ʽΪmsgtype:::name:::keyword:::sex
                        clientName = msgStr.substring(separatorIndex + 3).split(":::")[0];
                        clientKeyWord = msgStr.substring(separatorIndex +3).split(":::")[1];

                        // ����biz�㴦��
                        LoginHandle.getLoginHandle().clientLoginHandle(msgStr.substring(separatorIndex + 3), this);

                        break;
                    }

                    /***************** Ⱥ����Ϣ ******************/
                    case GroupChat:
                    {
                        // ����biz�㴦��
                        ChatHandleBiz.getChatHandleBiz().groupChatHandle(msgStr.substring(separatorIndex + 3));

                        break;
                    }

                    /***************** һ��һ������Ϣ ******************/
                    case SingleChat:
                    {
                    	
                        // ����biz�㴦��
                        ChatHandleBiz.getChatHandleBiz().singleChatHandle(msgStr.substring(separatorIndex + 3));
                        
                        break;
                    }
                    
                    /************ ע�����˺� ************/
                    case Apply:
                    {
                    	// sendMsg��ʽΪmsgType:::ID:::password:::sex:::province:::city:::question:::answer:::image
                    	// ����biz�㴦��
                    	ApplyHandle.getApplyHandle().clientApplyHandle(msgStr.substring(separatorIndex + 3),this);
                    	System.out.println("ServerThread Apply��"+msgStr);
                    }
                    default:
                        break;
                }
            }

        } catch(SocketException e)
        {
            try
            {
                // �����û�����
                LoginHandle.getLoginHandle().ClientOffLineHandle(clientName, this);
                
                // �ر���������
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
