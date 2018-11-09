
package Server;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import Client.ClientConnected;

import java.lang.NullPointerException;

public class MainServer {
	public static void main (String []args) throws Exception
	{ 

		try{  

			Server server = new Server ();
			System.out.println("The server is listening to port 3000");
			server.connexionclient(server);
			
			

		}

		catch (EOFException e ) {
			e.printStackTrace();
			System.exit(1);
		}
	}}
