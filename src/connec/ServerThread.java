package connec;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import loginFeat.XMLUser;
 
/**
 * This thread is responsible to handle client connection.
 */

public class ServerThread extends Thread {
	
	/**
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

    private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
    	String choice = "";
        try {
        	choice = clickState();
        	loginInit();
        	System.out.println("Here");
        	switch(choice) {
        	case "register":
        		System.out.println("Here2");
        		registerCheck();
        		System.out.println("Here3");
        		idCheck();
        		System.out.println("Here4");
        		break;
        		
        	case "login":
        		System.out.println("Here5");
        		loginCheck();
        		System.out.println("Here6");
        		break;
        	}
        	      		
        } finally {
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
    		System.out.println("BO");
    		input = new ObjectInputStream(socket.getInputStream());
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
    
    private void loginInit() 
    {
    	try {
			users = XMLUser.readXMLUser("UserName");
			passwords = XMLUser.readXMLUser("Password");
			IdUserString = XMLUser.readXMLUser("ID");
			//Since it was simpler to store the IDs in the XML files as string and then convert them, we use parseInt to do the conversion
			for(int i=0; i<IdUserString.size(); i++) {
				IdUser.add(Integer.parseInt(IdUserString.get(i)));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void loginCheck()
    {
    	 try {
    		input = new ObjectInputStream(socket.getInputStream());
  			String username = (String)input.readObject();  //read the object received through the stream and deserialize it
 			System.out.println("server received input:" + username);
 			String password = (String)input.readObject();  //read the object received through the stream and deserialize it
 			System.out.println("server received input:" + password);
 			
 			loginInit();
 			
 			boolean matchCheck = match(username, password);
 			output.writeObject(matchCheck);		//serialize and write the Student object to the stream
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
    
    public void registerCheck()
    {
    	loginInit();
    	try {
    		boolean existingAccount = false;
 			output = new ObjectOutputStream(socket.getOutputStream());
  
 			String usr = (String)input.readObject();  //read the object received through the stream and deserialize it
 			
 			for(int i=0; i<users.size(); i++){ //check that the account name is unique
				if(usr.compareTo(users.get(i)) == 0){
					existingAccount=true;
					System.out.println("ji");
					JOptionPane.showMessageDialog(null,"Account already exist", //if it isn't display an error message
							  "Warning", JOptionPane.WARNING_MESSAGE);
					System.out.println("ju ");
				}
			}
 			output.writeObject((boolean)existingAccount);
  
         } catch (IOException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();

 		} catch (ClassNotFoundException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();
         } 
    }
    
    
    public void idCheck()
    {
    	try {
 			int id= (int)(Math.random() * 999999); //generate a random account id 			
 			for(int i=0; i<IdUser.size();i++) { //check that it is unique
 				if(id==IdUser.get(i)) {
 					i=0;
 					id= (int)(Math.random() * 999999);
 				}
 			}
 			
 			output.writeObject((int)id);		//serialize and write the Student object to the stream
 			
         } catch (IOException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             ex.printStackTrace();

 		} 
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