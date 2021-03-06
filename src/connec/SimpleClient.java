package connec;
import java.io.*;  
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import loginFeat.User; 
import Chatroom.Chatroom;
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
	 * Server port
	 */
	private int port = 6666;
	
	/**
	 * User variable to identify an user's account that is currently connected
	 */
	public User connectedAccount;
	
	
	/**
	 * This function connect the client to the server 
	 * 
	 * @param ip is the Ip address where the server is located 
	 * 
	 * @author Nils Chol; Jean-Louis Cheng; Jason Khaou
	 *
	 */
	public void connect(String ip)
	{
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);
			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
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
	
	/**
	 * Method that asks validation from the server for the Username if it already exists or not 
	 * 
	 * @see loginFeat.RegisterActionListener
	 * @see ServerThread
	 * 
	 * @param usrname User's username
	 * @param password User's Password
	 * 
	 * @return boolean
	 */
	public boolean usrCheck(String usrname, String password)
	{
		boolean usr = false;
        try {
        	
        	output.writeObject("register"); //Sending data
            output.writeObject((String)usrname);
            output.writeObject((String)password);
            usr = (boolean)input.readObject(); //Waiting for an answer
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

		} catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } 
        return usr;
	}
	
	/**
	 * Method that asks the server to generate an unique ID for the new account
	 * 
	 * @see loginFeat.RegisterActionListener 
	 * @see ServerThread
	 * 
	 * @return int
	 */
	public int createId()
	{
		int id= 0;
        try  {
			id = (int) input.readObject();	//deserialize and read the Student object from the stream
			connectedAccount = (User) input.readObject(); //Waiting for an answer
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
	
	/**
	 * Method that asks the server to check if the username and password exists in the database (XML file)
	 * 
	 * @see loginFeat.LogActionListener 
	 * @see ServerThread
	 * 
	 * @param ip The server ip
	 * @param usrname The username
	 * @param psword The password
	 * 
	 * @return boolean
	 */
	public boolean loginCheck(String ip, String usrname, String psword) 
	{
		boolean check = false;
        try  {
        	
        	output.writeObject("login");
            String username = usrname;
            String password = psword;
            
            output.writeObject(username); //serialize and write the String to the stream
			
			output.writeObject(password); //serialize and write the String to the stream
			
			check = (boolean) input.readObject();	//deserialize and read the Student object from the stream
			
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
        return check;
	}
	
	/**
	 * Method that asks for the conversations logs from the server
	 * 
	 * @see Chatroom.Chatroom
	 * 
	 * @return String
	 */
	public String getLogs() {
		String logs ="";
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number	
        	//input = new ObjectInputStream(socket.getInputStream());
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
	
	/**
	 * Function to add an user to a friend list
	 * 
	 * @param userToAdd User name to add to the friend list
	 */
	public void addUser(String userToAdd) {
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
            output.writeObject((String)"addcontact"); //serialize and write the String to the stream
            output.writeObject((String) userToAdd);

            
		} catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Function to return the contact list of an user
	 *  
	 * @return the contact list as a string
	 */
	public String[] getContactList() {
		try {
			int arraySize = (int) input.readObject();
			String tester1[] = new String[arraySize];
			for(int i=0; i<arraySize;i++) {
				tester1[i] = (String) input.readObject();
			}
			return tester1;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new String[1];
	}
	

	/**
	 * Function to start a Chatroom
	 * 
	 * @param members Chatroom members
	 * @param category Type of chatroom
	 */
	public void startChatroom(ArrayList<String> members, String category) {
		try {
			output.writeObject((String) "chatroom");
			output.writeObject((String) category);
			output.writeObject((int) members.size());
			for(int i=0; i<members.size();i++) {
				output.writeObject((String) members.get(i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Function to send a message into a chatroom
	 * 
	 * @param message message to be sent 
	 */
	public void sendMessage(String message) {
		try {
			//output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject((String) message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This function is used to display all the participant in a chatroom
	 * 
	 * @return empty string
	 */
	public String getMemberList() {
		String memberList = "";
		try {
			memberList = (String) input.readObject();
			return memberList;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * This function deletes an user's contact member
	 * 
	 * @param userToDelete User name of the user that will be deleted
	 */
	public void deleteUser(String userToDelete) {
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
            output.writeObject((String)"deletecontact"); //serialize and write the String to the stream
            output.writeObject((String) userToDelete);
            
		} catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Function to add new members to a group
	 * 
	 * @param btnClicked variable containing information on a button 
	 * @param membersGroup List of group members
	 */
	public void sendNewGroupMembers(String btnClicked, List<String> membersGroup) {
		
        try {
        	if(btnClicked.equals("send")) {
	        	output.writeObject((boolean) true);
	        	output.writeObject((int) membersGroup.size());
	            for(int i=0; i<membersGroup.size();i++) {
	            	output.writeObject(membersGroup.get(i));
	            }
	            output.writeObject(connectedAccount.getUsername());
        	}
        	else {
        		output.writeObject((boolean) false);
        	}
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

		}
		
	}
}