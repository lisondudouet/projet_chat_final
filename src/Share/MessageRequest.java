package Share;

public class MessageRequest extends Request{

	private String message ; 
	private String username ; 
	private int idTopic;

	public MessageRequest (String message , String username, int idTopic) 
	{
		this.message = message; 
		this.username = username; 
		this.idTopic = idTopic;


	}
	public String getmessage ()
	{return message; }
	
	public String getusername ()
	{return username; }
	
	public int getidTopic ()
	{return idTopic; }




}
