package Client;


import java.net.Socket;
import java.io.*;
import java.net.*;


public class ClientConnected {

	private String usernameCC; 
	private String topicID; 
	private Socket clientSocket; 

	public ClientConnected () 
	{}

	public ClientConnected (Socket clientSocket, String topicID, String usernameCC) throws IOException 
	{

		this.clientSocket = clientSocket; 
		this.topicID = topicID; 
		this.usernameCC = usernameCC;

	}


}
