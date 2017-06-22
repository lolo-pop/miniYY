package MiniYYclient.model;

import javax.swing.JOptionPane;

import MiniYYclient.UI.MainFrame;
import MiniYYclient.common.CommonData;
import MiniYYclient.main.MainClass;
import MiniYYclient.thread.MainClientSocket;

public class ApplyHandleBiz {

	private static final ApplyHandleBiz applyHandleBiz = new ApplyHandleBiz(); // 定义本类实例对象

    private ApplyHandleBiz()
    {

    }

    /**
     * 返回登录结果处理类实例
     * 
     * @return
     */
    public static ApplyHandleBiz getApplyHandleBiz()
    {
        return ApplyHandleBiz.applyHandleBiz;
    }

    /**
     * 处理登录结果
     * 
     * @param resultStr
     */
    public void applyHandle(String resultStr)
    {
        // resultStr 格式：result
        int resultValue = Integer.parseInt(resultStr.trim());

        // 申请成功
        if (resultValue == 3)
        {
            System.out.println("Apply successfully! \n I am " + CommonData.getUserName());
            MainClientSocket.cishu=2;
            // 创建主窗体实例
            //MainFrame mainFrame = new MainFrame();
            //mainFrame.setVisible(true);
            // 保存全局信息中的主窗体
            //CommonData.setMainFrame(mainFrame);
            JOptionPane.showMessageDialog(null,"账号申请成功MiniYY号：！"+MainClass.cUser.getID());
        }
        
        // 申请失败
        else if(resultValue == 30)
        {
        	System.out.println("Apply failed! \n I am " + CommonData.getUserName());
        	JOptionPane.showMessageDialog(null,"账号申请失败！");
        }
    }
}
