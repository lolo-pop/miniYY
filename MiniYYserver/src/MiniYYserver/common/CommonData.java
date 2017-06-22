package MiniYYserver.common;

import java.util.HashMap;

import MiniYYclient.model.entity.OnlineUserDetail;
import MiniYYserver.thread.ServerThread;

public class CommonData
{
    private static HashMap<String, ServerThread>     clientThreadMap; // �������пͻ��˵������߳���
    private static HashMap<String, OnlineUserDetail> onlineUserMap;  // ���������û��Ļ�����Ϣ

    private static int                               listenPort;     // ���ؼ����˿�

    // ��̬�����
    static
    {
        clientThreadMap = new HashMap<>();
        onlineUserMap = new HashMap<>();
        
        listenPort = 8802;
    }

    /**
     * ���пͻ��˵������߳���
     * 
     * @return
     */
    public static HashMap<String, ServerThread> getClientThreadMap()
    {
        return CommonData.clientThreadMap;
    }

    public static void setServerIp(HashMap<String, ServerThread> clientThreadMap_)
    {
        CommonData.clientThreadMap = clientThreadMap_;
    }

    /**
     * ���������û�������Ϣ��
     * 
     * @return
     */
    public static HashMap<String, OnlineUserDetail> getOnlineUserMap()
    {
        return CommonData.onlineUserMap;
    }

    public static void setOnlineUserMap(HashMap<String, OnlineUserDetail> onlineUserMap_)
    {
        CommonData.onlineUserMap = onlineUserMap_;
    }

    /**
     * ���ؼ����˿�
     * 
     * @return
     */
    public static int getListenPort()
    {
        return CommonData.listenPort;
    }

    public static void setListenPort(int listenPort_)
    {
        CommonData.listenPort = listenPort_;
    }

}
