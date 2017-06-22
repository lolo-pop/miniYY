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
     * 返回聊天消息处理类实例
     * 
     * @return
     */
    public static ChatHandleBiz getChatHandleBiz()
    {
        return ChatHandleBiz.chatHandleBiz;
    }

    /**
     * 处理群聊消息
     * 
     * @param msgStr_
     */
    public void groupChatHandle(String msgStr_)
    {
        // msgStr 格式为name:::content
        String[] msgInfo = msgStr_.split(":::");
        String fromUserName = msgInfo[0];
        String content = msgInfo[1];

        // 消息格式：msgType:::fromUser:::content
        String sendMsg = MessageTypeEnum.GroupChat.toString() + ":::" + fromUserName + ":::" + content;

        System.out.println("group chat: " + fromUserName + " say: " + " : " + content);
        
        // 群发
        CommonData.getClientThreadMap().get(fromUserName).broadcast(sendMsg);
    }

    /**
     * 处理单聊消息
     * 
     * @param msgStr_
     */
    public void singleChatHandle(String msgStr_)
    {
        // msgStr 格式为 fromUser:::toUser:::content
        String[] msgInfo = msgStr_.split(":::");
        String fromUserName = msgInfo[0];
        String toUserName = msgInfo[1];
        String content = msgInfo[2];

        // 消息格式：msgType:::fromUser:::content
        String sendMsg = MessageTypeEnum.SingleChat.toString() + ":::" + fromUserName + ":::" + content;

        System.out.println("single chat: " + fromUserName + " speak to " + toUserName + " : " + content);

        // 向指定用户发送消息
        CommonData.getClientThreadMap().get(toUserName).sendData(sendMsg);
    }
}
