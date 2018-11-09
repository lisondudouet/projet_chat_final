package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Share.MessageRequest;

public class Reception implements Runnable{

	private String username; 
	private ObjectInputStream ois; 
	private ObjectOutputStream oos; 
	private int IDtopic;

	public Reception (String username, ObjectInputStream ois, ObjectOutputStream oos, int IDtopic ) 
	{
		this.username = username; 
		this.ois = ois;
		this.oos = 	oos;
		this.IDtopic = IDtopic;
	}
	public void run ()  
	{
		while (true)
		{
			try
			{
				MessageRequest messageClient = (MessageRequest)ois.readObject();
				if (IDtopic == messageClient.getidTopic())
				{
				System.out.print(messageClient.getusername());
				System.out.print(" dit : ");
				System.out.print(messageClient.getmessage());
				System.out.print("\n");
				}

			} 


			catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}	

		}}

}
