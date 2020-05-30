package connec;
import java.io.*;  
import java.net.*;

import loginFeat.User; 

/**
 * Class name : SimpleClient
 * This thread is responsible to handle client connection from the client side.
 * 
 * Version : 1.0
 * 
 * Date : 29/05/2020
 */
public class SimpleClient {
	
    /**
     * This stream will handle the objects going through the sockets
     */
	private ObjectOutputStream output;
    /**
     * This stream will handle the objects coming through the sockets
     */
	private ObjectInputStream input;
	/**
	 * This is the socket variable
	 */
	private Socket socket;
	/**
	 * This string will contain the user's message 
	 */
	public String Senda;
	/**
	 * This boolean is used to tell if a message has been sent or not
	 */
	public volatile boolean messageSent = false;
	private int port = 6666;
	public User connectedAccount;
	
	
	/**
	 * This function connect the client to the server 
	 * 
	 * @param ip is the Ip address where the server is located 
	 * 
	 * @author Nils Chol; Jean-Louis Cheng
	 *
	 */
	public void connect(String ip)
	{
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);
			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
			/*while(true){
				if(messageSent==true) {
					output = new ObjectOutputStream(socket.getOutputStream());
					output.writeObject((String) Senda);
					messageSent =false;
				}
			}*/	
	    } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public boolean usrCheck(String usrname, String password)
	{
		boolean usr = false;
        try {
        	
        	output.writeObject("register");
            output.writeObject((String)usrname);
            output.writeObject((String)password);
            input = new ObjectInputStream(socket.getInputStream());
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
			connectedAccount = (User) input.readObject();
	    } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
		catch  (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		/*finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}*/
        return id;
	}
	
	public boolean loginCheck(String ip, String usrname, String psword) 
	{
		boolean check = false;
        try  {
        	
        	output.writeObject("login");
            String username = usrname;
            String password = psword;
            
            output.writeObject(username); //serialize and write the String to the stream
			System.out.println("output sent to the server: " + username);	
			
			output.writeObject(password); //serialize and write the String to the stream
			System.out.println("output sent to the server: " + password);
			
			input = new ObjectInputStream(socket.getInputStream());		

			check = (boolean) input.readObject();	//deserialize and read the Student object from the stream
			
			connectedAccount = (User) input.readObject();
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
	
	public String getLogs(String ip) {
		int port = 6666;
		String logs ="";
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			input = new ObjectInputStream(socket.getInputStream());
			
			logs = (String) input.readObject();

		} catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        
        return logs;
        
	}
}