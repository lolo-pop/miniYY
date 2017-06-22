package MiniYYserver.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import MiniYYclient.model.entity.OnlineUserDetail;
import MiniYYserver.DAL.SqlExecute;
import MiniYYserver.common.CommonData;
import MiniYYserver.thread.MessageTypeEnum;
import MiniYYserver.thread.ServerThread;

public class ApplyHandle {
	private static final ApplyHandle applyHandle = new ApplyHandle();
	String clientID = new String("");
    String clientName = new String("");
    String clientKeyWord = new String("");
    String clientSex = new String("");
    String clientProvince = new String("");
    String clientCity = new String("");
    String clientQuestion = new String("");
    String clientAnswer = new String("");
    String clientImage = new String("");
    //ID:::name:::password:::sex:::province:::city:::question:::answer:::image
    public ApplyHandle()
    {

    }

    /**
     * ���ؿͻ���¼������ʵ��
     * 
     * @return
     */
    public static ApplyHandle getApplyHandle()
    {
        return ApplyHandle.applyHandle;
    }

    /**
     * ����ͻ���������
     * 
     * @param loginStr_
     * @param serverThread_
     */
    public void clientApplyHandle(String applyStr_, ServerThread serverThread_)
    {
    	// sendMsg��ʽΪmsgType:::ID:::password:::sex:::province:::city:::question:::answer:::image
        String[] msgInfo = applyStr_.split(":::");
    	clientID = msgInfo[0];
        clientName = msgInfo[1];
        clientKeyWord = msgInfo[2];
        clientSex = msgInfo[3];
        clientProvince = msgInfo[4];
        clientCity = msgInfo[5];
        clientQuestion = msgInfo[6];
        clientAnswer = msgInfo[7];
        clientImage = msgInfo[8];
        
        // �ڴ˲����˺��ظ�����
        if(false)
        {

            System.out.println("got the same ID, Logon failed !! ");
                
            // ��Ϣ��ʽ��msgType:::loginResult
            String sendStr = MessageTypeEnum.LoginReturn.toString() + ":::" + new Integer(0).toString();
            serverThread_.sendData(sendStr);
                
            // �÷����߳� serverThread �˳�ѭ��
            serverThread_.setListen(false);
            // Thread.currentThread().interrupt();
                
            return;
            
        }

        SqlExecute sql = new SqlExecute();
        
        try {
			sql.createID(clientID,clientName,clientKeyWord,clientSex,clientProvince,clientCity,clientQuestion,clientAnswer,clientImage);
	        System.out.println("Apply ID successfully");
	        
	        // ����ɹ� ��Ϣ��ʽ��msgType:::ApplyReturn (ApplyReturn:::3)
	        String sendStr = MessageTypeEnum.ApplyReturn.toString() + ":::" + new Integer(3).toString();
	        serverThread_.sendData(sendStr);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
        	// ��¼ʧ�� ��Ϣ��ʽ��msgType:::ApplyReturn (ApplyReturn:::30)
        	
        	System.out.println("Apply ID failed");
        	String sendStr = MessageTypeEnum.ApplyReturn.toString() + ":::" + new Integer(30).toString();
        	serverThread_.sendData(sendStr);
        	}
    }
}

