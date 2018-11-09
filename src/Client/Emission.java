package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import Share.*;

public class Emission implements Runnable{

	private String username; 
	private ObjectInputStream ois; 
	private ObjectOutputStream oos; 
	private int IDtopic;


	public Emission(String username, ObjectInputStream ois, ObjectOutputStream oos, int IDtopic ) 
	{
		this.username = username; 
		this.ois = ois;
		this.oos = 	oos; 
		this.IDtopic = IDtopic;
	}

	public void run ()  
	{
		while(true)
		{
			Scanner sc = new Scanner(System.in);
			String message = sc.nextLine();
			if (message.contains("!out"))
			{		
				System.out.println("The client is deconnected");
				System.exit(0);
			}
			else
			{
			MessageRequest MR = new MessageRequest(message, username, IDtopic); 
			MessageRequest MR2 = new MessageRequest("", "", IDtopic);
			try 
			{			
				oos.writeObject(MR2);
				oos.writeObject(MR);
			} 

			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}}}
}
