package MiniYYclient.thread;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import MiniYYclient.UI.LoginFrame;
import MiniYYclient.common.CommonData;

public class MainClientSocket
{
    private static final MainClientSocket mainSocket = new MainClientSocket(); // ���屾��ʵ������
    private static Socket                 socket;                             // ���Ӷ���
    private static ClientServerThread     serverThread;                       // �����߳�
    public static int cishu = 0; // ��¼ʱ�ж��Ƿ���Ҫ�ٴ����ӷ�����
    private MainClientSocket()
    {
        try
        {
            System.out.println(CommonData.getServerIp());
            System.out.println(CommonData.getServerPort());

            // ��ʼ������
            socket = new Socket(CommonData.getServerIp().trim(), CommonData.getServerPort());
            serverThread = new ClientServerThread(socket);

        } catch(ConnectException e)
        {
            System.err.println("δ���ҵ�������");
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
     * ��ȡ������ʵ��
     * 
     * @return ������ʵ��
     */
    public static MainClientSocket getMainSocket()
    {
        return mainSocket;
    }

    /**
     * ��ȡ�����߳�
     * 
     * @return �����߳�ʵ��
     */
    public ClientServerThread getServerThread()
    {
        return serverThread;
    }

    /**
     * ���ӷ����������Ե���
     * 
     * @param userName_
     * @param userKeyWord_
     * @param sex_
     */
    public void login(String userName_, String userKeyWord_, String sex_)
    {
    	
        // ���ӷ���������������
        if(cishu==0) this.getServerThread().start();
        cishu++;
        // sendMsg��ʽΪmsgType:::name:::keyword:::sex
        String sendMsg = MessageTypeEnum.Login.toString() + ":::" + userName_ + ":::"+userKeyWord_+":::" + sex_;

        // ������Ϣ
        this.getServerThread().sendData(sendMsg);


    }
    
    /**
     * ���ӷ�����������ע���˺�
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
    	// ���ӷ���������������
        this.getServerThread().start();

        // sendMsg��ʽΪmsgType:::ID:::password:::sex:::province:::city:::question:::answer:::image
        String sendMsg = MessageTypeEnum.Apply.toString() + ":::" +id_+":::"+ userName_ + ":::"+userKeyWord_+":::" + sex_+":::"+Province_+":::"+City_+":::"+Question_+":::"+
        Answer_+":::"+Image_;

        // ������Ϣ
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
