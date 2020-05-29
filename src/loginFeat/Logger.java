package loginFeat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


/**
 * Class that handles finding existing accounts and their corresponding IDs
 * 
 * @version 1.0
 *
 * @author Roman DIDELET
 * 
 * @see xmlLogger()
 *
 */


public class Logger {
	
	/**
	 * Constructor for the logger class
	 * @see LoggerGUI
	 * @see xmlLogger#readXMLLogger(String)
	 */
	public Logger() {

	}
 	
	public static boolean loginCheck(String ip, String usrname, String psword) 
	{
		ObjectOutputStream output;
		ObjectInputStream input;
		Socket socket;
		boolean check = false;
		int port = 6666;
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
			
            String username = usrname;
            String password = psword;
            
            output.writeObject(username); //serialize and write the String to the stream
			System.out.println("output sent to the server: " + username);	
			
			output.writeObject(password); //serialize and write the String to the stream
			System.out.println("output sent to the server: " + password);
					
 
			check = (boolean) input.readObject();	//deserialize and read the Student object from the stream
			System.out.println("input received from the server: " + check);
			
			input.close();
			output.close();
			socket.close();
			
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
	/**
	 * Method that matches the entered account name and password with existing accounts
	 * 
	 * @param name
	 * @param password
	 * @param accounts
	 * @param passwords
	 * @return boolean
	 */
	static public boolean match(String name, String password, ArrayList<String> accounts, ArrayList<String> passwords) {
		for(int i=0; i<accounts.size();i++) {
			if(name.compareTo(accounts.get(i))==0 && password.compareTo(passwords.get(i))==0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method that finds the ID of an account, returns -1 if the account doesn't exist
	 * 
	 * @param name
	 * @param password
	 * @param accounts
	 * @param passwords
	 * @param IdClients
	 * @return int
	 */
	static public int findID(String name, String password, ArrayList<String> accounts, ArrayList<String> passwords, ArrayList<Integer> IdClients) {
		for(int i=0; i<accounts.size();i++) {
			if(name.compareTo(accounts.get(i))==0 && password.compareTo(passwords.get(i))==0) {
				return IdClients.get(i);
			}
		}
		return -1;
	}
}