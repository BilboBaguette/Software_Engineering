package connec;
import java.io.*;  
import java.net.*; 

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
		int port = 6666;
        try  {
			while(true){
				if(messageSent==true) {
					output = new ObjectOutputStream(socket.getOutputStream());
					output.writeObject((String) Senda);
					messageSent =false;
				}
			}
		} catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
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