package MiniYYclient.model.entity;



import java.io.Serializable;

public class OnlineUserDetail implements Serializable
{
    private String userName = ""; // 用户名
    private String userSex = "男"; // 用户性别
    
    public OnlineUserDetail()
    {
        
    }
    
    public OnlineUserDetail(String userName_, String userSex_)
    {
        this.userName = userName_;
        this.userSex = userSex_;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName_)
    {
        this.userName = userName_;
    }

    public String getUserSex()
    {
        return userSex;
    }

    public void setUserSex(String userSex_)
    {
        this.userSex = userSex_;
    }
    
}
