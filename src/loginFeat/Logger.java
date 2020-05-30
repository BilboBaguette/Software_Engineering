package loginFeat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import connec.SimpleClient;


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
	 * @see xmlLogger#readXMLUser(String)
	 */
	public Logger() {

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