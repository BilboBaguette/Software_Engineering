package connec;
import java.io.*;  
import java.net.*; 

public class SimpleClient {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	private int port = 6666;
	
	public void connect(String ip)
	{
		
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
			
			
	    } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public boolean usrCheck(String ip, String usrname)
	{
		boolean usr = false;
        try {
        
            output.writeObject((String)usrname);
            
            usr = (boolean)input.readObject();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

		} catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } 
        return usr;
	}
	
	public int createId()
	{
		int id= 0;
        try  {
			id = (int) input.readObject();	//deserialize and read the Student object from the stream
	    } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
		catch  (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
        return id;
	}
	
	public boolean loginCheck(String ip, String usrname, String psword) 
	{
		boolean check = false;
        try  {
        
            String username = usrname;
            String password = psword;
            
            output.writeObject(username); //serialize and write the String to the stream
			System.out.println("output sent to the server: " + username);	
			
			output.writeObject(password); //serialize and write the String to the stream
			System.out.println("output sent to the server: " + password);
					
			check = (boolean) input.readObject();	//deserialize and read the Student object from the stream
			System.out.println(check);
	    } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
		catch  (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
        return check;
	}
}