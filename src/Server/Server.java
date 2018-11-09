
package Server;

import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.EOFException;
import java.io.IOException;
import Share.*;
import Client.ClientConnected;

public class Server {

	public static final int SERVER_PORT = 3000;
	public static final String DB_FILE_NAME = "Account.db";
	public static final String DB_FILE_NAME2 = "Topic.db";

	private Database db;
	private Database db2;

	private ServerSocket serversocket;

	private ArrayList<Account> Accounts;

	private ArrayList<Topic> Topics;

	private ClientConnected CC;

	private ArrayList <ClientConnected> users;
	
	private ArrayList <ClientHandler> CHusers;


	public Server() throws IOException, ClassNotFoundException {

		try 

		{


			CHusers= new ArrayList <ClientHandler>();
			Accounts = new ArrayList<Account> () ; 
			Topics = new ArrayList <Topic> () ; 


			//socket qui permet de faire le lien avec le client	 
			this.serversocket = new ServerSocket(SERVER_PORT);

			this.db = new Database(DB_FILE_NAME);
			this.db2 = new Database(DB_FILE_NAME2); 



		}

		catch ( EOFException ex)
		{ex.printStackTrace(); }

		catch (NullPointerException npe )
		{ npe.printStackTrace(); }

	}

	public ArrayList<Account> getAccounts() 
	{return this.Accounts;}

	public ArrayList<Topic> getTopics() 
	{return this.Topics;}


	public void connexionclient (Server server) throws Exception
	{
		//instruction bloquante jusqu'à  ce qu'un client se connecte 
		while (true)
		{
			//accepte la connexion demandé par le client 
			Socket client = serversocket.accept();
			System.out.println("The client is connected \n");

			//on lance le thread qui gére le client 
			ClientHandler CH =new ClientHandler (client, db, db2, CC, users,server);
			Thread t1 = new Thread (CH); 
			
			t1.start(); 
			
			CHusers.add(CH); 
			
		}}
	public ArrayList <ClientHandler> getCHusers () 
	{return CHusers;}
	
	
	public void sendMessage (ArrayList <ClientHandler> CHusers, MessageRequest MR)
	{
		
		for (int i= 0; i<CHusers.size(); i++)
		{
			try {
				CHusers.get(i).getoos().writeObject(MR);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}





