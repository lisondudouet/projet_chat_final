package Share;

import java.util.ArrayList;

import Server.Account;
import Server.Server; 
import Server.Database;


public class AuthentificationRequest extends Request {
    
    private String username; 
    private String password; 
    
    
    public AuthentificationRequest ( String username, String password) 
    { 
    	;
    	this.username = username; 
    	this.password = password; 
    }
    // accesseur 
    public String getusername () 
    {return this.username; }
    
    public String getpassword () 
    {return this.password; }
    
   
    
    
   
}
    
    
    
    
    

