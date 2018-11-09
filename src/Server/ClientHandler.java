
package Server;


import Share.*;
import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.ObjectInputStream ; 
import java.io.ObjectOutputStream ;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import Client.ClientConnected;

public class ClientHandler implements Runnable {

	private Socket client ;
	private ArrayList <Account> accountList;
	private ArrayList <Topic> topicList;
	private Database db;
	private Database db2;
	private ClientConnected CC; 
	private ArrayList <ClientConnected> users; 
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server Server; 
	

	public ClientHandler (Socket client, Database db, Database db2, ClientConnected CC,ArrayList <ClientConnected> users, Server Server )
	{
		this.client = client ;
		this.accountList = new ArrayList <Account>();
		this.topicList = new ArrayList <Topic> ();
		this.db=db;
		this.db2=db2;
		this.CC = CC; 
		this.users= new ArrayList <ClientConnected>(); 
		this.Server = Server ; 
	}
	 
	public ObjectOutputStream getoos()
	{return oos; }

	public void setObjectOutputStream (ObjectOutputStream oos)
	{ 
		this.oos =oos; 
	}
	public void setObjectInputStream (ObjectOutputStream oos)
	{ 
		this.ois =ois; 
	}
	
	
	// méthode qui gère le client 
	public void run ()  
	{

		try {
			accountList = db.loadAccounts();
			topicList = db2.loadTopics();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			// on crée les flux qui permmettent au client et au serveur d'échanger 
			// on lit les messages du client tant qu'il est connecté

			 ois = new ObjectInputStream(this.client.getInputStream());

			// On écrit des messages au client 

			 oos = new  ObjectOutputStream(this.client.getOutputStream());

			while ( this.client.isConnected())	{

				//instruction bloquante tant que le client n'a pas envoyé d'objet d'ou le fait d'avoir un deuxième thread qui tourne en //
				Request request = (Request) ois.readObject(); 

				// on regarde de quel type de requete il s'agit 
				if (request instanceof AuthentificationRequest) {
					AuthentificationRequest AuthReq = (AuthentificationRequest) request ; 

					// on récupère les identifiants dans le fichier et on compare les deux chaines de caractères à cet endroit là 
					//parcours de l'arrylist pour trouver si les id sont dedans


					if (isvalid(AuthReq.getusername(), AuthReq.getpassword(), accountList))
					{
						System.out.println("authentication is a success");
						//on envoie une réponse au client pour lui dire que son authentification est un succès  
						oos.writeObject(new AuthentificationSuccessfulResponse());

					}
					else {oos.writeObject( new AuthentificationDeniedResponse()); 
					System.exit(0);}
				} 

				if (request instanceof CreateAccountRequest)
				{
					boolean auth = false;

					// on cherche dans la base de données pour savoi si il existe déja et sinon on l'ajoute
					do {

						String username = (String) ois.readObject(); 
						System.out.println("username received ");  

						if (isAvailable(username,accountList)) {

							oos.writeObject(new CreateAccountSuccessfullResponse());
							String password = (String)ois.readObject(); 
							Account accountCreated = new Account(username,password); 
							ArrayList <Account> addAccount = new ArrayList <Account> (); 
							addAccount.add(accountCreated);

							for ( int i = 0; i< accountList.size(); i++) {
								addAccount.add(accountList.get(i)); 
							}

							db.saveAccounts(addAccount);
							System.out.println("the account has been created "); 
							auth = true;
						} else { 

							oos.writeObject(new CreateAccountDeniedResponse());
							System.out.println("username déja utilisé "); 
						}
					}while(!auth); 
				}

				if (request instanceof CreateTopicRequest) {

					String topicName = (String) ois.readObject(); 
					System.out.println("topicname received ");  

					users.add(CC); 
					Topic top = new Topic (topicName);

					ArrayList <Topic> addTopic = new ArrayList <Topic> (); 

					addTopic.add(top);
					for ( int i = 0; i< topicList.size(); i++)
					{
						addTopic.add(topicList.get(i)); 
					}
					db2.saveTopics(addTopic); 

					System.out.println("the topic has been created "); 

				} 

				if (request instanceof ListTopicRequest)
				{
					try 
					{

						ArrayList <Topic> allTopic = new ArrayList <Topic> (); 
						allTopic = db2.loadTopics();
						oos.writeObject(allTopic.size());
						for ( int i = 0; i< allTopic.size(); i++)
						{
							oos.writeObject(allTopic.get(i).getname());	
						}
					}
					catch (InvalidClassException ex)
					{
						ex.printStackTrace(); 
					}


				} 
				if (request instanceof ChooseTopicRequest)
				{	
					int nb = (int)ois.readObject(); 
					ArrayList <Topic> allTopic2 = new ArrayList <Topic> (); 
					allTopic2 = db2.loadTopics();

					String topic = allTopic2.get(nb).getname();
					System.out.print("The client chose the topic ");
					System.out.println(topic);					


				}

				if (request instanceof MessageRequest)
				{
					MessageRequest messageClient = (MessageRequest)ois.readObject();
					ArrayList<ClientHandler>CHusers;
					CHusers = Server.getCHusers();
					Server.sendMessage(CHusers, messageClient);
					
				}
			}}

		catch(Exception ex)
		{
			ex.printStackTrace(); 
			System.exit(1);
		}
	}

	public boolean isvalid (String username, String password, ArrayList<Account> accountList) 
	{
		boolean valid= false; 
		for ( int i = 0; i< accountList.size(); i++)
		{
			if ( username.equals(accountList.get(i).getusername()) && (password.equals(accountList.get(i).getpassword())))
			{
				valid = true; 
			}
		}

		return valid; 

	}

	public boolean isAvailable (String username, ArrayList <Account> accountList)
	{

		boolean available= false; 
		int j =0; 

		for (int i=0; i<accountList.size(); i++)
		{	 if ( !accountList.get(i).getusername().equals(username))

		{ available = true; }

		else { j++; }
		}

		if (j!=0)
		{ available = false; }
		else if ( j==0)
		{available = true;}

		return available; 
	}

}
