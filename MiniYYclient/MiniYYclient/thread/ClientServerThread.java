package MiniYYclient.thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import MiniYYclient.common.CommonData;
import MiniYYclient.model.ApplyHandleBiz;
import MiniYYclient.model.ChatHandleBiz;
import MiniYYclient.model.LoginHandleBiz;


public class ClientServerThread extends Thread
{
    private Socket                  socket       = null;
    private static DataOutputStream dos          = null;
    private static DataInputStream  dis          = null;
    private String                  separatorStr = "";   // ��Ϣ�ָ���
    boolean                         isListen     = false;

    public ClientServerThread(Socket socket_)
    {
        try
        {
            separatorStr = ":::";
            this.socket = socket_;

            // ��ʼ�����������
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ������Ϣ
     * 
     * @param msgStr
     *            ��Ϣ��ʽ������
     */
    public void sendData(String msgStr)
    {
        try
        {
            dos.writeUTF(msgStr);

        } catch(IOException e)
        {
            e.printStackTrace();
        }
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

                /***************** ���� Login ��� ******************/
                    case LoginReturn:
                    {
                        // ����biz����д���
                    	
                        LoginHandleBiz.getLoginHandleBiz().loginHandle(msgStr.substring(separatorIndex + 3));

                        break;
                    }

                    /***************** ���� �������������û�������Ϣ ******************/
                    case AllOnlineUserDetailReturn:
                    {
                        // ����biz����д���
                        LoginHandleBiz.getLoginHandleBiz().onlineUserDetailReturnHandle(
                                msgStr.substring(separatorIndex + 3));

                        break;
                    }

                    /***************** ���� �����û����� ******************/
                    case NewClientConnect:
                    {
                        // ����biz����д���
                        LoginHandleBiz.getLoginHandleBiz().newClientConnectHandle(msgStr.substring(separatorIndex + 3));

                        break;
                    }


                    /***************** ���� �û����� ******************/
                    case ClientOffLine:
                    {
                        // ����biz����д���
                        LoginHandleBiz.getLoginHandleBiz().ClientOffLineHandle(msgStr.substring(separatorIndex + 3));

                        break;
                    }

                    /***************** Ⱥ����Ϣ ******************/
                    case GroupChat:
                    {
                        // ����biz����д���
                        ChatHandleBiz.getChatHandleBiz().groupChatHandle(msgStr.substring(separatorIndex + 3));

                        break;
                    }

                    /***************** һ��һ������Ϣ ******************/
                    case SingleChat:
                    {
                        // ����biz����д���
                        ChatHandleBiz.getChatHandleBiz().singleChatHandle(msgStr.substring(separatorIndex + 3));

                        break;
                    }
                    
                    /************* ����ע����******************/
                    case ApplyReturn:
                    {
                        // ����biz����д���
                        ApplyHandleBiz.getApplyHandleBiz().applyHandle(msgStr.substring(separatorIndex + 3));

                        break;
                    }

                    default:
                        break;
                }

            }

        } catch(SocketException e)
        {
            System.err.println("��������Ͽ����ӣ���");
            e.printStackTrace();
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
