package connec;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Chatroom.Messages;
import Chatroom.XMLLog;
import javax.swing.JOptionPane;

import loginFeat.Logger;
import loginFeat.User;
import loginFeat.XMLUser;
 
/**
 * Class name : ServerThread 
 * This thread is responsible to handle client connection From the server side.
 * 
 * Version : 2.0
 * 
 * Date : 30/05/2020
 */

public class ServerThread extends Thread {
	
	/**
	 * This variable countains the server port 
	 *
	 * Arraylist used to store account names
	 * @see match()
	 * @see loginInit()
	 */
	public ArrayList<String> users = new ArrayList<String>();
	/**
	 * Arraylist used to store passwords
	 * @see match()
	 * @see loginInit()
	 */
	public ArrayList<String> passwords = new ArrayList<String>();
	/**
	 * Arraylist used to store client IDs as Strings
	 * @see loginInit()
	 */
	public ArrayList<String> IdUserString = new ArrayList<String>();
	/**
	 * Arraylist used to store client IDs as integers
	 * @see loginInit()
	 */
	public ArrayList<Integer> IdUser = new ArrayList<Integer>();

	/**
	 * Arraylist used to store lists of contacts
	 * 
	 */
	public ArrayList<ArrayList<String>> contactList = new ArrayList<ArrayList<String>>();
	
    private Socket socket;
    /**
     * This stream will handle the objects going through the sockets
     */
	private ObjectInputStream input;
	/**
	 * This stream will handle the objects coming through the sockets
	 */
	private ObjectOutputStream output;
 

	private User userToAdd = new User("name", "password", 0);
	private boolean accountExists=false;
	String contactUsername;
	
	/**
	 * This is the constructor of the class
	 * 
	 * @param socket Is the socket of the server 
	 * 
	 * @author Nils Chol; Jean-Louis Cheng; Jason Khaou
	 *
	 */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
	/**
	 * This function will run the client connection and executes the task leading to the exchange with the server
	 * 
	 * @see java Class Thread; java Class Socket; java Class ObjectInputStream; java Class ObjectOutputStream
	 *
	 */
    public void run() {
    	String choice = "";
        try {
    		input = new ObjectInputStream(socket.getInputStream());
 			output = new ObjectOutputStream(socket.getOutputStream());
        	System.out.println("Here");
        	choice = clickState();
        	loginInit();
        	switch(choice) {
        	case "register":
        		System.out.println("Here2");
        		registerCheck();
        		System.out.println("Here3");
        		idCheck();
        		System.out.println("Here4");
        		if(!accountExists) {
        			XMLUser.addToXML(userToAdd);
        		}
        		break;
        		
        	case "login":
        		System.out.println("Here5");
        		loginCheck();
        		System.out.println("Here6");
        		break;
        		
        	case "addcontact":
        		System.out.println("Here7");
        		if(!checkContact())
        		{
        			System.out.println("Here9");
        			XMLUser.addContactToUserXML(userToAdd.getId(), contactUsername);
        			System.out.println("Here10");
        		}
        		System.out.println("Here8");
        		break;
        	}
			//create the streams that will handle the objects coming through the sockets
			ArrayList<String> messageContent = XMLLog.readXMLLog("MessageContent");
			ArrayList<String> messageUsername = XMLLog.readXMLLog("UserName");
			String wholeText = "";
			for(int i=0;i<messageContent.size()-1;i++) {
				wholeText += messageUsername.get(i) + ": " + messageContent.get(i) + "\n";
			}
			wholeText += messageUsername.get(messageUsername.size()-1) + ": " + messageContent.get(messageContent.size()-1);
						
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject((String) wholeText);
			
			while(true) {
				input = new ObjectInputStream(socket.getInputStream());
				String helo = (String) input.readObject();
				System.out.println("The server received: " + helo);
				XMLLog.addToXML(new Messages("user", helo));
			}
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();


		} catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } catch(Exception e){ 
        	e.printStackTrace();
        }finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

        	      		
        }
    }
    
    /**
	 * Method that checks will receive the info about which button the user has clicked on
	 * 
	 * @see SimpleClient()
	 * 
	 * @return String
	 */
    private String clickState()
    {
    	String state = "";
    	try {
    		System.out.println("BO");
    		System.out.println("BO2");
  			state = (String)input.readObject();  //read the object received through the stream and deserialize it
  			System.out.println(state);
         } catch (IOException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();

 		} catch (ClassNotFoundException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();
         } 
    	System.out.println(state);
    	return state;
    }
    
    /**
	 * Method that updates every ArrayList of the class so it is up to date
	 */
    private void loginInit() 
    {
    	try {
			users = XMLUser.readXMLUser("UserName");
			passwords = XMLUser.readXMLUser("Password");
			IdUserString = XMLUser.readXMLUser("ID");
			contactList = XMLUser.readContactXMLUser();
			//Since it was simpler to store the IDs in the XML files as string and then convert them, we use parseInt to do the conversion
			for(int i=0; i<IdUserString.size(); i++) {
				IdUser.add(Integer.parseInt(IdUserString.get(i)));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * Method that checks if the username and password exists in the database (XML file) and send the answer to the user
	 * 
	 * @see SimpleClient()
	 * @see loginCheck()
	 */
    private void loginCheck()
    {
    	 try {
  			String username = (String)input.readObject();  //read the object received through the stream and deserialize it
 			System.out.println("server received input:" + username);
 			String password = (String)input.readObject();  //read the object received through the stream and deserialize it
 			System.out.println("server received input:" + password);
 			
 			loginInit();
 			
 			boolean matchCheck = match(username, password);
 			output.writeObject(matchCheck);		//serialize and write the Student object to the stream
 			int id = Logger.findID(username, password, users, passwords, IdUser);
 			output.writeObject((User) new User(username, password, id));
 			System.out.println(matchCheck);
         } catch (IOException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();

 		} catch (ClassNotFoundException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();
         } finally {
 			try {
 				output.close();
 				input.close();
 			} catch (IOException ioe) {
 				ioe.printStackTrace();
 			}
 		}
    }
    
    /**
	 * Method that checks if the account already exists in the database if so then send back a negative response
	 * 
	 * @see SimpleClient()
	 * @see usrCheck()
	 */
    private void registerCheck()
    {
    	loginInit();
    	try {
    		boolean existingAccount = false;
 			System.out.println("test");
 			String usr = (String)input.readObject();  //read the object received through the stream and deserialize it
 			System.out.println("usr = " + usr);
 			String psd = (String)input.readObject();
 			System.out.println("password = " + psd);
 			userToAdd.setUsername(usr);
 			userToAdd.setPassword(psd);
 			System.out.println("test");
 			for(int i=0; i<users.size(); i++){ //check that the account name is unique
				if(usr.compareTo(users.get(i)) == 0){
					accountExists=true;
					existingAccount=true;
					System.out.println("ji");
					JOptionPane.showMessageDialog(null,"Account already exist", //if it isn't display an error message
							  "Warning", JOptionPane.WARNING_MESSAGE);
					System.out.println("ju ");
				}
			}
 			System.out.println("test2");
 			System.out.println(existingAccount);
 			output.writeObject((boolean)existingAccount);
  
         } catch (IOException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();

 		} catch (ClassNotFoundException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();
         } 
    }
    
    /**
	 * Method that creates and send a valid ID for the newly created user
	 * 
	 * @see SimpleClient()
	 * @createId()
	 */
    private void idCheck()
    {
    	try {
 			int id= (int)(Math.random() * 999999); //generate a random account id 			
 			for(int i=0; i<IdUser.size();i++) { //check that it is unique
 				if(id==IdUser.get(i)) {
 					i=0;
 					id= (int)(Math.random() * 999999);
 				}
 			}
 			userToAdd.setId(id);
 			output.writeObject((int)id);		//serialize and write the Student object to the stream
 			output.writeObject((User) userToAdd); 
         } catch (IOException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();
 		} 
    }
    
    /**
	 * Method that checks if a contact is already registered in a contact list.
	 * 
	 * @see SimpleClient()
	 */
    private boolean checkContact()
    {
    	try {
    		contactUsername = (String)input.readObject(); // getting the username we want to add
        	for(int i = 0; i < users.size(); i++)
        	{
        		String node = users.get(i); //get the current User
        		if(userToAdd.getUsername().equals(node))
        		{
        			for(int j = 0; j < contactList.size(); j++) //Going through his contact list
        			{
        				if(contactUsername.equals(contactList.get(i).get(j))) //If exists then return true
        				{
        					return true;
        				}
        			}
        		}
			}		
         } catch (IOException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();

 		} catch (ClassNotFoundException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();
         }
    	return false;
    }
    
    /**
	 * Method that matches the entered account name and password with existing accounts
	 * 
	 * @param name
	 * @param password
	 * @return boolean
	 */
	public boolean match(String name, String password) {
		for(int i=0; i<users.size();i++) {
			if(name.compareTo(users.get(i))==0 && password.compareTo(passwords.get(i))==0) {
				return true;
			}
		}
		return false;
	}
}