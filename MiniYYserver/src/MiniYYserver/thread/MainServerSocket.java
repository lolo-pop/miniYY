package MiniYYserver.thread;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import MiniYYserver.common.CommonData;


public class MainServerSocket
{
    private static final MainServerSocket mainSocket = new MainServerSocket();
    
    // ����Socket
    private static ServerSocket serverSocket;
    
    // �ͻ���Socket
    private static Socket clientSocket;
    
    // �Ƿ��������
    private boolean isListen = true; 
    
    private MainServerSocket()
    {
        try
        {
            serverSocket = new ServerSocket(CommonData.getListenPort());
            
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * ��ȡmainSocket����
     * @return
     */
    public static MainServerSocket getMainSocket()
    {
        return MainServerSocket.mainSocket;
    }
    
    /**
     * ������������������ʼ���տͻ�����
     */
    public void beginListen()
    {
        try
        {
            while(isListen)
            {
                System.out.println("listening...");
                
                // ��������
                clientSocket = serverSocket.accept();
                
                System.out.println("one client connect");
                
                // �յ��ͻ��˺�����һ�����߳�
                new ServerThread(clientSocket).start();
            }
            
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
}
