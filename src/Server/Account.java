package Server;

import java.io.Serializable;

public class Account implements Serializable {
	
	  private String username; 
	  private String password ; 
	  
	  
	  public Account (String username, String password)
	  {
		  this.username = username ; 
		  this.password = password ; 
	  }
	  
	  
	    public String getusername () 
	    {return username; }
	    
	    public String getpassword () 
	    {return password ; }
}
