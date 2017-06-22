package MiniYYclient.model;

import javax.swing.JFrame;

import MiniYYclient.common.CommonData;
import MiniYYclient.UI.ChatFrame;
import MiniYYclient.UI.Sounds;


public class ChatHandleBiz
{
    private static final ChatHandleBiz chatHandleBiz = new ChatHandleBiz();

    private ChatHandleBiz()
    {

    }

    /**
     * ����������Ϣ������ʵ��
     * 
     * @return
     */
    public static ChatHandleBiz getChatHandleBiz()
    {
        return ChatHandleBiz.chatHandleBiz;
    }

    /**
     * ����Ⱥ����Ϣ
     * 
     * @param msgStr_
     *            ��ʽ��msgType:::fromUser:::content
     */
    public void groupChatHandle(String msgStr_)
    {
        String[] msgInfos = msgStr_.split(":::");
        String fromUser = msgInfos[0];
        String content = msgInfos[1];

        // ����������Ϣ����
        Sounds.getSounds().startMessageAudio();

        // �ӻ�����Ϣ���ȡ����Ⱥ���촰�壬�����������
        CommonData.getGroupChatFrame().setVisible(true);
        CommonData.getGroupChatFrame().addChatContent(content);
    }

    /**
     * ��������Ϣ
     * 
     * @param msgStr_
     *            ��ʽ��fromUser:::content
     */
    public void singleChatHandle(String msgStr_)
    {
        String[] msgInfos = msgStr_.split(":::");
        String fromUser = msgInfos[0];
        String content = msgInfos[1];

        // ����������Ϣ����
        Sounds.getSounds().startMessageAudio();

        // �ж��Ƿ�������û��Ĺ��죬���������һʵ��
        if (CommonData.getChatUserMap().get(fromUser) == null)
        {
            CommonData.getChatUserMap().put(fromUser, new ChatFrame(fromUser));
        }

        // ������ɼ�ʱ
        if (CommonData.getChatUserMap().get(fromUser).isVisible())
        {
            // ����ɼ�����С��ʱ�������ȡ���㲢������С��
            if (CommonData.getChatUserMap().get(fromUser).getState() == JFrame.ICONIFIED) // JFrame.MAXIMIZED_BOTH
            {
                CommonData.getChatUserMap().get(fromUser).setVisible(true);
                CommonData.getChatUserMap().get(fromUser).setExtendedState(JFrame.ICONIFIED);
            }

            // ���岻����С��ʱ��������
            else
            {
            }
        }

        // �����岻�ɼ�ʱ
        else
        {
            // �����岻�ɼ�������ɼ�
            CommonData.getChatUserMap().get(fromUser).setVisible(true);
        }

        // �ӻ�����Ϣ���ȡĿ���û����촰�壬�����������
        CommonData.getChatUserMap().get(fromUser).addChatContent(content);
    }

}
