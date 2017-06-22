package MiniYYclient.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Vector;

import MiniYYclient.UI.ChatFrame;
import MiniYYclient.UI.MainFrame;
import MiniYYclient.UI.Sounds;
import MiniYYclient.model.entity.OnlineUserDetail;

public class CommonData
{
    private static String                            serverIP;            // ������ip
    private static int                               serverPort;          // �������˿�
    private static String                            userName;            // �û���
    private static String                            userSex;             // �Ա�

    private static HashMap<String, ChatFrame>        chatUserMap;         // ���������û���
    private static ChatFrame                         groupChatFrame;      // Ⱥ���쵥������
    private static MainFrame                         mainFrame;           // �����ڣ�������
    private static HashMap<String, OnlineUserDetail> onlineUserMap = null; // ���������û��Ļ�����Ϣ
    private static Vector<String>                    expressionPathVec;       // ����·��

    static
    {
        serverIP = "192.168.56.1"; // Ĭ��ip
        serverPort = 8802; // Ĭ�϶˿�
        userName = "";
        userSex = "��"; // Ĭ�� ��
        chatUserMap = new HashMap<>();
        
        // ��ʼ������·��
        String directoryPath;
        try
        {
            expressionPathVec = new Vector<>();
            directoryPath = URLDecoder.decode(CommonData.class.getResource("/").toString() + "MiniYYclient/UI/imgs", "utf-8");
            directoryPath = directoryPath.replace("file:/", ""); // Ҫ��ȥ getResource ���ص�·���е�  file:/ ���ܽ��ж�ȡ
            File file = new File(directoryPath);
            String[] expressionFileNames = file.list();
            for (String item : expressionFileNames)
            {
                expressionPathVec.add(directoryPath + "/" + item);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ������ip
     * 
     * @return
     */
    public static String getServerIp()
    {
        return CommonData.serverIP;
    }

    public static void setServerIp(String serverIP_)
    {
        CommonData.serverIP = serverIP_;
    }

    /**
     * ������port
     * 
     * @return
     */
    public static int getServerPort()
    {
        return CommonData.serverPort;
    }

    public static void setServerPort(int serverPort_)
    {
        CommonData.serverPort = serverPort_;
    }

    /**
     * �û���
     * 
     * @return
     */
    public static String getUserName()
    {
        return CommonData.userName;
    }

    public static void setUserName(String userName_)
    {
        CommonData.userName = userName_;
    }

    /**
     * �Ա�
     * 
     * @return
     */
    public static String getUserSex()
    {
        return CommonData.userSex;
    }

    public static void setUserSex(String userSex_)
    {
        CommonData.userSex = userSex_;
    }
    


    /**
     * �����û���
     * 
     * @return
     */
    public static HashMap<String, ChatFrame> getChatUserMap()
    {
        return CommonData.chatUserMap;
    }

    public static void setChatUserMap(HashMap<String, ChatFrame> chatUserMap_)
    {
        CommonData.chatUserMap = chatUserMap_;
    }

    /**
     * Ⱥ���쵥������
     * 
     * @return
     */
    public static ChatFrame getGroupChatFrame()
    {
        if (CommonData.groupChatFrame == null)
        {
            try
            {
                CommonData.groupChatFrame = (ChatFrame) Class.forName("MiniYYclient.UI.ChatFrame").newInstance();
            } catch(Exception e)
            {
                System.err.println("Ⱥ���촰�����쳣");
                return null;
            }
        }
        return CommonData.groupChatFrame;
    }

    public static void setGroupChatFrame(ChatFrame groupChatFrame_)
    {
        CommonData.groupChatFrame = groupChatFrame_;
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
     * ������
     * 
     * @return
     */
    public static MainFrame getMainFrame()
    {
        return CommonData.mainFrame;
    }

    public static void setMainFrame(MainFrame mainFrame_)
    {
        CommonData.mainFrame = mainFrame_;
    }
        
    /**
     * ����·��
     * 
     * @return
     */
    public static Vector<String> getExpressionPathVector()
    {
        return CommonData.expressionPathVec;
    }

    public static void setExpressionPathVector(Vector<String> expressionPathVector_)
    {
        CommonData.expressionPathVec = expressionPathVector_;
    }
}
