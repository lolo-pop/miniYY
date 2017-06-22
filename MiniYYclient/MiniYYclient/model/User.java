package MiniYYclient.model;

public class User {

	private String name;
	private String Password;
	private String ID;
	private String image;
	private boolean position;
	
	public User(){
	
	}
	
	public User(String name, String password){
		this.name = name;
		this.Password = password;
	}
	
	public String getID(){
		return this.ID;
	}
	
	
	public void setID(String ID){
		this.ID = ID;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public String getImage(){
		return this.image;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getPassword(){
		return this.Password;
	}
	
	public void setPassword(String password){
		this.Password = password;
	}
	
	public boolean getPosition()
	{
		return this.position;
	}
	
	public void setPosition(boolean position)
	{
		this.position = position;
	}
}
