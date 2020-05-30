package connec;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Chatroom.Messages;
import Chatroom.XMLLog;
import javax.swing.JOptionPane;

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
	 * @see #match(String, String)
	 * @see #loginInit()
	 */
	public ArrayList<String> users = new ArrayList<String>();
	/**
	 * Arraylist used to store passwords
	 * @see #match(String, String)
	 * @see #loginInit()
	 */
	public ArrayList<String> passwords = new ArrayList<String>();
	/**
	 * Arraylist used to store client IDs as Strings
	 * @see #loginInit()
	 */
	public ArrayList<String> IdUserString = new ArrayList<String>();
	/**
	 * Arraylist used to store client IDs as integers
	 * @see #loginInit()
	 */
	public ArrayList<Integer> IdUser = new ArrayList<Integer>();

	/**
	 * Arraylist used to store lists of contacts
	 * 
	 */
	public ArrayList<ArrayList<String>> contactList = new ArrayList<ArrayList<String>>();
	
	/**
	 * Server socket variable
	 */
    private Socket socket;
    /**
     * This stream will handle the objects going through the sockets
     */
	private ObjectInputStream input;
	/**
	 * This stream will handle the objects coming through the sockets
	 */
	private ObjectOutputStream output;
 
	/**
	 * User variable containing user information to add to an XML file
	 */
	private User userToAdd = new User("name", "password", 0);
	
	/**
	 * boolean to verify if an account exist
	 */
	private boolean accountExists=false;
	
	/**
	 * String containing user's username 
	 */
	String contactUsername;
	
	/**
	 * Function to find the ID of an user 
	 * @param name The name of the client
	 * @param password the password of the client 
	 * @param accounts List of accounts
	 * @param passwords List of passwords
	 * @param IdClients List of clients IDs 
	 * @return
	 */
	static private int findID(String name, String password, ArrayList<String> accounts, ArrayList<String> passwords, ArrayList<Integer> IdClients) {
        for(int i=0; i<accounts.size();i++) {
            if(name.compareTo(accounts.get(i))==0 && password.compareTo(passwords.get(i))==0) {
                return IdClients.get(i);
            }
        }
        return -1;
    }
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
	 * @see Thread 
	 * @see Socket 
	 * @see ObjectInputStream 
	 * @see ObjectOutputStream
	 *
	 */
    public void run() {
    	String choice = "";
        try {
        	//partie logger
    		input = new ObjectInputStream(socket.getInputStream());
 			output = new ObjectOutputStream(socket.getOutputStream());
        	choice = clickState();
        	loginInit();
        	switch(choice) {
        	case "register":
        		registerCheck();
        		idCheck();
        		if(!accountExists) {
        			XMLUser.addToXML(userToAdd);
        		}
        		break;
        		
        	case "login":
        		loginCheck();
        		break;
        		
        	}
        	
        	//partie menu
        	boolean menu;
        	boolean chatroom;
        	sendContactList();
        	while(true) {
	        	menu=true;
	        	while(menu) {
	        	String menuChoice = (String)input.readObject();
	        	switch(menuChoice) {
		        	case "addcontact":
		        		if(!checkContact())
		        		{
		        			XMLUser.addContactToUserXML(userToAdd.getId(), contactUsername);
		        			loginInit();
		        			sendContactList();
		        		}
		        		break;
		        	case "chatroom":
		        		menu=false;
		        		break;
		        	}
	        	}
        	
	        	//partie chatroom
				ArrayList<String> messageContent = XMLLog.readXMLLog("MessageContent");
				ArrayList<String> messageUsername = XMLLog.readXMLLog("UserName");
				String wholeText = "";
				for(int i=0;i<messageContent.size()-1;i++) {
					wholeText += messageUsername.get(i) + ": " + messageContent.get(i) + "\n";
				}
				wholeText += messageUsername.get(messageUsername.size()-1) + ": " + messageContent.get(messageContent.size()-1);
							
				output = new ObjectOutputStream(socket.getOutputStream());
				output.writeObject((String) wholeText);
				chatroom=true;
				while(chatroom){
					String helo = (String) input.readObject();
					if(helo.compareTo("/quit")==0) {
						chatroom=false;
					}else {
						XMLLog.addToXML(new Messages("user", helo));
					}
				}
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
  			state = (String)input.readObject();  //read the object received through the stream and deserialize it
         } catch (IOException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();

 		} catch (ClassNotFoundException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();
         } 
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
     * Function that gets a user's friend list
     */
    private void sendContactList() {
    	for(int i = 0; i < users.size(); i++)
    	{
    		String node = users.get(i);
    		if(userToAdd.getUsername().compareTo(node)==0)
    		{
    			try {
    				//System.out.println(contactList.get(i));
					output.writeObject((int) contactList.get(i).size());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			for(int j = 0; j < contactList.get(i).size(); j++)
    			{
    				try {
						output.writeObject((String) contactList.get(i).get(j));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}
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
 			String password = (String)input.readObject();  //read the object received through the stream and deserialize it
 			
 			loginInit();
 			
 			boolean matchCheck = match(username, password);
 			output.writeObject(matchCheck);		//serialize and write the Student object to the stream
 			int id = findID(username, password, users, passwords, IdUser);
 			userToAdd.setId(id);
 			userToAdd.setPassword(password);
 			userToAdd.setUsername(username);
 			output.writeObject((User) new User(username, password, id));
         } catch (IOException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();

 		} catch (ClassNotFoundException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();
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
 			String usr = (String)input.readObject();  //read the object received through the stream and deserialize it
 			String psd = (String)input.readObject();
 			userToAdd.setUsername(usr);
 			userToAdd.setPassword(psd);
 			for(int i=0; i<users.size(); i++){ //check that the account name is unique
				if(usr.compareTo(users.get(i)) == 0){
					accountExists=true;
					existingAccount=true;
					JOptionPane.showMessageDialog(null,"Account already exist", //if it isn't display an error message
							  "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
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
        		String node = users.get(i);
        		if(userToAdd.getUsername().compareTo(node)==0)
        		{
        			for(int j = 0; j < contactList.get(i).size(); j++)
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