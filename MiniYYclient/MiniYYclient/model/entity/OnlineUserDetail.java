package MiniYYclient.model.entity;

import java.io.Serializable;


public class OnlineUserDetail implements Serializable
{
    private String userName = ""; // �û���
    private String userSex  = "��"; // �û��Ա�

    /**
     * �չ��캯��, �������������ʹ��
     */
    public OnlineUserDetail()
    {
    }

    /**
     * ���ι��죬��ʼ������
     * 
     * @param userName_
     * @param userSex_
     */
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