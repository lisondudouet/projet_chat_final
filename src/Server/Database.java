package Server;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Database {

	private File file;

	public Database(String fileName) {
		this.file = new File(fileName);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Account> loadAccounts() throws IOException, ClassNotFoundException

	{

		ArrayList<Account> data = new ArrayList<Account>();

		try {
			// This checks if the file actually exists
			if (this.file.exists() && !this.file.isDirectory()) 
			{
				FileInputStream fis = new FileInputStream(this.file);
				ObjectInputStream ois = new ObjectInputStream(fis);

				data = (ArrayList<Account>) ois.readObject();
			} 

		}

		catch (EOFException ex) {
			ex.printStackTrace();
		}

		return data;
	}

	public void saveAccounts(ArrayList<Account> data) throws IOException {

		FileOutputStream fos = new FileOutputStream(this.file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(data);

		System.out.println("The file is saved" + data.size() + " Account ");
	}

	public void check(String username, String password) {
		// méthode qui vérifie que la saisie du client est conforme à ce qu'il y a dans le fichier
		ArrayList<Account> data = new ArrayList<Account>();
		try {
			data = loadAccounts();
		} catch (ClassNotFoundException | IOException e) {

			e.printStackTrace();
		}

		for (int i = 0; i < data.size(); i++) {
			if ((data.get(i).getusername().equals(username)) && (data.get(i).getpassword().equals(password)))
				;
			{
				System.out.println("existing passwords and login");
			}

		}

	}

	@SuppressWarnings("unchecked")
	public ArrayList<Topic> loadTopics() throws IOException, ClassNotFoundException

	{

		ArrayList<Topic> data = new ArrayList<Topic>();

		try {
			// This checks if the file actually exists
			if (this.file.exists() && !this.file.isDirectory()) 
			{
				FileInputStream fis = new FileInputStream(this.file);
				ObjectInputStream ois = new ObjectInputStream(fis);

				data = (ArrayList<Topic>) ois.readObject();
			} 

		}

		catch (EOFException ex) {
			ex.printStackTrace();
		}

		return data;
	}

	public void saveTopics(ArrayList<Topic> data) throws IOException {

		FileOutputStream fos   = new FileOutputStream(this.file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(data);
		//oos.close();

		System.out.println("The file is saved " + data.size() + " Topics ");
	}





}
