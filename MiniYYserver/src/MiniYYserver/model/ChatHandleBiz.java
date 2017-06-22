package MiniYYserver.model;


import MiniYYserver.common.CommonData;
import MiniYYserver.thread.MessageTypeEnum;

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
     */
    public void groupChatHandle(String msgStr_)
    {
        // msgStr ��ʽΪname:::content
        String[] msgInfo = msgStr_.split(":::");
        String fromUserName = msgInfo[0];
        String content = msgInfo[1];

        // ��Ϣ��ʽ��msgType:::fromUser:::content
        String sendMsg = MessageTypeEnum.GroupChat.toString() + ":::" + fromUserName + ":::" + content;

        System.out.println("group chat: " + fromUserName + " say: " + " : " + content);
        
        // Ⱥ��
        CommonData.getClientThreadMap().get(fromUserName).broadcast(sendMsg);
    }

    /**
     * ��������Ϣ
     * 
     * @param msgStr_
     */
    public void singleChatHandle(String msgStr_)
    {
        // msgStr ��ʽΪ fromUser:::toUser:::content
        String[] msgInfo = msgStr_.split(":::");
        String fromUserName = msgInfo[0];
        String toUserName = msgInfo[1];
        String content = msgInfo[2];

        // ��Ϣ��ʽ��msgType:::fromUser:::content
        String sendMsg = MessageTypeEnum.SingleChat.toString() + ":::" + fromUserName + ":::" + content;

        System.out.println("single chat: " + fromUserName + " speak to " + toUserName + " : " + content);

        // ��ָ���û�������Ϣ
        CommonData.getClientThreadMap().get(toUserName).sendData(sendMsg);
    }
}
