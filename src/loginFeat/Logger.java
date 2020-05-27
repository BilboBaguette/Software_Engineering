package loginFeat;

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

	/**
	 * Constructor for the logger class
	 * @see LoggerGUI
	 * @see xmlLogger#readXMLUser(String)
	 */
	public Logger() {

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