package MiniYYserver.thread;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import MiniYYserver.common.CommonData;


public class MainServerSocket
{
    private static final MainServerSocket mainSocket = new MainServerSocket();
    
    // 监听Socket
    private static ServerSocket serverSocket;
    
    // 客户端Socket
    private static Socket clientSocket;
    
    // 是否继续监听
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
     * 获取mainSocket单例
     * @return
     */
    public static MainServerSocket getMainSocket()
    {
        return MainServerSocket.mainSocket;
    }
    
    /**
     * 开启服务器监听，开始接收客户连接
     */
    public void beginListen()
    {
        try
        {
            while(isListen)
            {
                System.out.println("listening...");
                
                // 阻塞监听
                clientSocket = serverSocket.accept();
                
                System.out.println("one client connect");
                
                // 收到客户端后启动一条新线程
                new ServerThread(clientSocket).start();
            }
            
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
}
