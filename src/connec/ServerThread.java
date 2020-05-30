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
 * Version : 1.0
 * 
 * Date : 27/05/2020
 */

public class ServerThread extends Thread {
	
	/**
	 * This variable countains the server port 
	 *
	 * Arraylist used to store account names
	 * @see Logger()
	 * @see match()
	 * @see findID()
	 */
	public ArrayList<String> users = new ArrayList<String>();
	/**
	 * Arraylist used to store passwords
	 * @see Logger()
	 * @see match()
	 * @see findID()
	 */
	public ArrayList<String> passwords = new ArrayList<String>();
	/**
	 * Arraylist used to store client IDs as Strings
	 * @see Logger()
	 */
	public ArrayList<String> IdUserString = new ArrayList<String>();
	/**
	 * Arraylist used to store client IDs as integers
	 * @see Logger()
	 * @see findID()
	 */
	public ArrayList<Integer> IdUser = new ArrayList<Integer>();

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
 
	/**
	 * This is the constructor of the class
	 * 
	 * @param socket Is the socket of the server 
	 * 
	 * @author Nils Chol; Jean-Louis Cheng
	 *
	 */
	private User userToAdd = new User("name", "password", 0);
	private boolean accountExists=false;
	String contactUsername;
	
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
	/**
	 * This function will run the client connection and executes the task leading to the exchange with the server
	 * 
	 * @see java Class Thread; java Class Socket; java Class ObjectInputStream; java Class ObjectOutputStream
	 * @author Nils Chol; Jean-Louis Cheng
	 *
	 */
    public void run() {
    	String choice = "";
        try {
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
        	
        	//envoyer infos combobox contacts pour menu
        	sendContactList();
        	
        	while(true) {
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
	        		System.out.println("test Chatroom");
	        		break;
	        	}
        	}
        	
			//create the streams that will handle the objects coming through the sockets
			/*ArrayList<String> messageContent = XMLLog.readXMLLog("MessageContent");
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
			}*/
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
    
    private void loginCheck()
    {
    	 try {
  			String username = (String)input.readObject();  //read the object received through the stream and deserialize it
 			String password = (String)input.readObject();  //read the object received through the stream and deserialize it
 			
 			loginInit();
 			
 			boolean matchCheck = match(username, password);
 			output.writeObject(matchCheck);		//serialize and write the Student object to the stream
 			int id = Logger.findID(username, password, users, passwords, IdUser);
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
    
    private boolean checkContact()
    {
    	try {
    		contactUsername = (String)input.readObject();
        	for(int i = 0; i < users.size(); i++)
        	{
        		String node = users.get(i);
        		if(userToAdd.getUsername().compareTo(node)==0)
        		{
        			for(int j = 0; j < contactList.get(i).size(); j++)
        			{
        				if(contactUsername.equals(contactList.get(i).get(j)))
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
	 * @param accounts
	 * @param passwords
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