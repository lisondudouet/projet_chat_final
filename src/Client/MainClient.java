package Client;

import Server.Server;

public class MainClient {
	
    public static void main (String []args)
    { 
       
        try{  
        	        	        	
        	Client client = new Client(); 
        	
        	client.connexionServer();      	         
        	
            }

        catch (Exception e ) {
        	
            e.printStackTrace();
            System.exit(1);
        }

}

}
