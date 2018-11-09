
package Client;

import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import Server.*;
import Share.*;
import Client.*; 
import java.io.ObjectInputStream ; 
import java.io.ObjectOutputStream ; 
import java.net.Socket;






public class Client {

	private Socket socket ; 
	private ObjectOutputStream oos; 
	private ObjectInputStream ois;
	private int idTopic; 

	public Client ()throws UnknownHostException, IOException 
	{
		try { 

			this.socket = new Socket((String)null,3000); 

			// on cree deux stream là car on sait qu'on va s'en servir pour toute la connexion

			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.ois = new ObjectInputStream(this.socket.getInputStream());
			idTopic = 0; 
		}

		catch (ConnectException CEx)
		{

			CEx.printStackTrace();
		}
	}                         



	public void disconnect() throws IOException, ClassNotFoundException {

		System.out.println(" GOOD BYE ! ");
		this.oos.close();
		this.ois.close();
		this.socket.close();

	}


	public void readResponse() throws ClassNotFoundException, IOException {

		String response = (String) this.ois.readObject();
		System.out.println("Response: " + response);
	}  

	public void connexionServer() throws 	ClassNotFoundException, IOException {

		choicefirst();

	}

	public void choicefirst()

	{
		System.out.println("__________________________________________ MENU _____________________________________________"); 
		System.out.println("[1] - Authentification "); 
		System.out.println("[2] - Create an account ");

		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();


		switch(choix)
		{
		case 1 :
		{

			try {

				String username ;
				String password ;		

				System.out.println("Write your username : ");
				username = sc.next();

				System.out.println("Write your password : ");
				password = sc.next();

				AuthentificationRequest AuthReqCl = new AuthentificationRequest (username, password) ; 

				// on envoie la requete au server 
				oos.writeObject(AuthReqCl); 

				Response Response = (Response) ois.readObject(); 

				if (Response instanceof AuthentificationSuccessfulResponse)
				{
					System.out.println("Authentication successfull ! "); 
					Account A1 = new Account (username, password); 
					choiceTopic(A1); 
				}
				else if (Response instanceof AuthentificationDeniedResponse)
				{
					System.exit(1);
				}

				break;

			}

			catch (Exception ex)
			{   ex.printStackTrace();
			System.exit(1);	}
		}

		case 2 : 
		{

			try {

				CreateAccountRequest createAcc = new CreateAccountRequest (); 
				oos.writeObject(createAcc);
				String newUsername; 
				String newPassword; 
				boolean auth = false;

				do
				{
					System.out.println("Write your username : ");
					newUsername = sc.next();
					oos.writeObject(newUsername);
					System.out.println("username sent");

					Response Response = (Response) ois.readObject(); 

					if (Response instanceof CreateAccountSuccessfullResponse)
					{
						System.out.println("Write your password : ");
						newPassword = sc.next();
						oos.writeObject(newPassword);
						System.out.println("Successful creation of account ");
						auth = true;
						Account A1 = new Account (newUsername, newPassword);
						choiceTopic(A1); 

					}
					else if (Response instanceof CreateAccountDeniedResponse)
					{
						System.out.println(" Creation of account Denied ");
					}
				}
				while(!auth);

				break;
			}

			catch (Exception ex)
			{
				System.exit(1); 
			}

		}}}









	public synchronized void choiceTopic(Account A1) throws IOException

	{
		System.out.println("_________________________________________ MENU ___________________________________________");
		System.out.println("[1]- Create a topic "); 
		System.out.println("[2]- Join a topic ");
		System.out.println("[3]- discnonnect ");

		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();
		String topicName; 

		switch(choix)
		{
		case 1 :
		{

			try {


				CreateTopicRequest CreateReql = new CreateTopicRequest () ; 
				// on envoie la requete au server 
				oos.writeObject(CreateReql);

				//on charge les topics et on en ajoute un supplémentaire dans le fichier 
				System.out.println("Enter the topic name : "); 
				topicName = sc.next(); 

				oos.writeObject(topicName);
				
				Thread t2 = new Thread (new Emission (A1.getusername(), ois, oos, idTopic)); 
				t2.start(); 

				Thread t3 = new Thread (new Reception (A1.getusername(), ois, oos, idTopic)); 
				t3.start(); 

				break;

			}

			catch (Exception ex)
			{   ex.printStackTrace();
			System.exit(1);	
			}
		}

		case 2 : 
		{
			try 

			{ListTopicRequest ListReql = new ListTopicRequest () ; 

			// on envoie la requete au server 
			oos.writeObject(ListReql); 
			int size = (int) ois.readObject();

			for ( int i = 0; i< size; i++)
			{
				String topicName2 = (String) ois.readObject(); 
				System.out.print(i+1);
				System.out.print("-");
				System.out.println(topicName2);
			}	

			System.out.println("Enter your choice of topic : ");
			int choix2 = sc.nextInt();

		if (choix2==1) 
				{
			try {
					idTopic = 1; 	
					int nb = 0; 
					ChooseTopicRequest CTR1 = new ChooseTopicRequest (); 
					System.out.println(" Welcome on the chat ! ");
					// on envoie la requete au server 
					oos.writeObject(CTR1); 
					oos.writeObject(nb);

					Thread t2 = new Thread (new Emission (A1.getusername(), ois, oos, idTopic)); 
					t2.start(); 

					Thread t3 = new Thread (new Reception (A1.getusername(), ois, oos, idTopic)); 
					t3.start(); 

					break;

				}


				catch (Exception ex)
				{
					System.exit(1); 
				}}
		else if (choix2==2) 
			{
				try {
					idTopic	= 2;
					int nb = 1; 
					ChooseTopicRequest CTR1 = new ChooseTopicRequest (); 
					System.out.println(" Welcome on the chat ! ");
					// on envoie la requete au server 
					oos.writeObject(CTR1); 
					oos.writeObject(nb);

					Thread t2 = new Thread (new Emission (A1.getusername(), ois, oos, idTopic)); 
					t2.start(); 

					Thread t3 = new Thread (new Reception (A1.getusername(), ois, oos, idTopic)); 
					t3.start(); 


					break;

				}


				catch (Exception ex)
				{
					System.exit(1); 
				}}
		else if (choix2==3) 
		{
				try {
					idTopic	= 3;
					int nb = 2; 
					ChooseTopicRequest CTR1 = new ChooseTopicRequest (); 
					System.out.println(" Welcome on the chat ! ");
					// on envoie la requete au server 
					oos.writeObject(CTR1); 
					oos.writeObject(nb);

					Thread t2 = new Thread (new Emission (A1.getusername(), ois, oos, idTopic)); 
					t2.start(); 

					Thread t3 = new Thread (new Reception (A1.getusername(), ois, oos, idTopic)); 
					t3.start(); 

					break;

				}


				catch (Exception ex)
				{
					System.exit(1); 
				}
		}
			
			
			else if (choix2==4 )
			{	try {

					idTopic	= 4;
					int nb = 3; 
					ChooseTopicRequest CTR1 = new ChooseTopicRequest (); 
					System.out.println(" Welcome on the chat ! ");
					// on envoie la requete au server 
					oos.writeObject(CTR1); 
					oos.writeObject(nb);

					Thread t2 = new Thread (new Emission (A1.getusername(), ois, oos, idTopic)); 
					t2.start(); 

					Thread t3 = new Thread (new Reception (A1.getusername(), ois, oos, idTopic)); 
					t3.start(); 

					break;

				}


				catch (Exception ex)
				{
					System.exit(1); 
				}}
			else if ( choix2 ==5)
				{try {

					idTopic	= 5;
					int nb = 4; 
					ChooseTopicRequest CTR1 = new ChooseTopicRequest (); 

					// on envoie la requete au server 
					oos.writeObject(CTR1); 
					oos.writeObject(nb);
					System.out.println(" Welcome on the chat ! ");

					Thread t2 = new Thread (new Emission (A1.getusername(), ois, oos, idTopic)); 
					t2.start(); 

					Thread t3 = new Thread (new Reception (A1.getusername(), ois, oos, idTopic)); 
					t3.start(); 

					break;

				}


				catch (Exception ex)
				{
					System.exit(1); 
				}}}
			catch (ClassNotFoundException Ex )
			{
				Ex.printStackTrace();
			}}


		case 3 : 
		{

			//requete pour se deconnecter

			try {

				System.exit(0);
				break;

			}

			catch (Exception ex)
			{
				System.exit(1); 
			}
		}
		}}

}