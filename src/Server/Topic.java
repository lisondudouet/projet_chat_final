package Server;

import java.io.Serializable;
import java.util.ArrayList;

import Client.*;
public class Topic implements Serializable {

	private String name; 


	public Topic ()
	{}



	public Topic (String name) 
	{

		this.name = name;

	}

	public String getname() {return name; }



}
