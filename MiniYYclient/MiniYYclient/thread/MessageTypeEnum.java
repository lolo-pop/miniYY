package MiniYYclient.thread;

public enum MessageTypeEnum
{
    Login, // 登录
    LoginReturn, // 返回登录结果
    AllOnlineUserDetailReturn, // 返回所有在线用户基本信息
    ClientOffLine, // 用户下线
    NewClientConnect, // 有新用户登入
    GroupChat, // 群聊
    SingleChat, // 一对一单聊
    Apply, //申请账号
    ApplyReturn; // 返回申请结果
}
