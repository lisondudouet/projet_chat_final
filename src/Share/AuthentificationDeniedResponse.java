
package Share;
import Share.AuthentificationRequest; 
import Share.AuthentificationSuccessfulResponse; 

public class AuthentificationDeniedResponse extends Response{
    
    public AuthentificationDeniedResponse ()
    {
    super(); 
    }
    
    public void Run () 
         
    {
        System.out.println("Your username or password is false try again : ");
        // on lui repropose de donner son mdp et identifiant
        Request request2= new Request(); 
        AuthentificationRequest AuthReq = (AuthentificationRequest) request2 ; 
         
    
    
    }
}
