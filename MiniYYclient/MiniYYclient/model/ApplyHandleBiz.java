package MiniYYclient.model;

import javax.swing.JOptionPane;

import MiniYYclient.UI.MainFrame;
import MiniYYclient.common.CommonData;
import MiniYYclient.main.MainClass;
import MiniYYclient.thread.MainClientSocket;

public class ApplyHandleBiz {

	private static final ApplyHandleBiz applyHandleBiz = new ApplyHandleBiz(); // ���屾��ʵ������

    private ApplyHandleBiz()
    {

    }

    /**
     * ���ص�¼���������ʵ��
     * 
     * @return
     */
    public static ApplyHandleBiz getApplyHandleBiz()
    {
        return ApplyHandleBiz.applyHandleBiz;
    }

    /**
     * �����¼���
     * 
     * @param resultStr
     */
    public void applyHandle(String resultStr)
    {
        // resultStr ��ʽ��result
        int resultValue = Integer.parseInt(resultStr.trim());

        // ����ɹ�
        if (resultValue == 3)
        {
            System.out.println("Apply successfully! \n I am " + CommonData.getUserName());
            MainClientSocket.cishu=2;
            // ����������ʵ��
            //MainFrame mainFrame = new MainFrame();
            //mainFrame.setVisible(true);
            // ����ȫ����Ϣ�е�������
            //CommonData.setMainFrame(mainFrame);
            JOptionPane.showMessageDialog(null,"�˺�����ɹ�MiniYY�ţ���"+MainClass.cUser.getID());
        }
        
        // ����ʧ��
        else if(resultValue == 30)
        {
        	System.out.println("Apply failed! \n I am " + CommonData.getUserName());
        	JOptionPane.showMessageDialog(null,"�˺�����ʧ�ܣ�");
        }
    }
}
