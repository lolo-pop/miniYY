package MiniYYclient.main;

import java.util.Random;
import MiniYYclient.model.User;
import MiniYYclient.UI.LoginFrame;


public class MainClass
{
    /**
     * @param args
     */
	public static User cUser = new User();
	public MainClass(){
		
	}
    public static void main(String[] args)
    {
    	LoginFrame lf = new LoginFrame();
    	lf.login_interface();
    	if (cUser.getPosition()) lf.setVisible(false);

    }
}
