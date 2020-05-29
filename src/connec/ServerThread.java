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
        try {
			//create the streams that will handle the objects coming through the sockets
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
 
 			String text = (String)input.readObject();  //read the object received through the stream and deserialize it
			System.out.println("server received a text:" + text);
			
			Student student = new Student(1234, "john.doe");
			output.writeObject(student);		//serialize and write the Student object to the stream
 
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
    
    private void loginInit() 
    {
    	try {
			this.users = XMLUser.readXMLUser("UserName");
			this.passwords = XMLUser.readXMLUser("Password");
			this.IdUserString = XMLUser.readXMLUser("ID");
			
			//Since it was simpler to store the IDs in the XML files as string and then convert them, we use parseInt to do the conversion
			for(int i=0; i<this.IdUserString.size(); i++) {
				this.IdUser.add(Integer.parseInt(this.IdUserString.get(i)));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void loginCheck()
    {
    	loginInit();
    	 try {
 			//create the streams that will handle the objects coming through the sockets
 			input = new ObjectInputStream(socket.getInputStream());
 			output = new ObjectOutputStream(socket.getOutputStream());
  
  			String username = (String)input.readObject();  //read the object received through the stream and deserialize it
 			System.out.println("server received input:" + username);
 			String password = (String)input.readObject();  //read the object received through the stream and deserialize it
 			System.out.println("server received input:" + password);
 			
 			boolean matchCheck = match(username, password, users, passwords);
 			output.writeObject(matchCheck);		//serialize and write the Student object to the stream
  
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
 			//create the streams that will handle the objects coming through the sockets
 			input = new ObjectInputStream(socket.getInputStream());
 			output = new ObjectOutputStream(socket.getOutputStream());
  
 			String usr = (String)input.readObject();  //read the object received through the stream and deserialize it
 			
 			for(int i=0; i<users.size(); i++){ //check that the account name is unique
				if(usr.compareTo(users.get(i)) == 0){
					existingAccount=true;
					JOptionPane.showMessageDialog(null,"Account already exist", //if it isn't display an error message
							  "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
 			output.writeObject(existingAccount);
  
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
    	loginInit();
    	try {
 			//create the streams that will handle the objects coming through the sockets
 			input = new ObjectInputStream(socket.getInputStream());
 			output = new ObjectOutputStream(socket.getOutputStream());
  
 			int id= (int)(Math.random() * 999999); //generate a random account id 			
 			for(int i=0; i<IdUser.size();i++) { //check that it is unique
 				if(id==IdUser.get(i)) {
 					i=0;
 					id= (int)(Math.random() * 999999);
 				}
 			}
 			
 			output.writeObject(id);		//serialize and write the Student object to the stream
  
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
	static public boolean match(String name, String password, ArrayList<String> accounts, ArrayList<String> passwords) {
		for(int i=0; i<accounts.size();i++) {
			if(name.compareTo(accounts.get(i))==0 && password.compareTo(passwords.get(i))==0) {
				return true;
			}
		}
		return false;
	}
}