package loginFeat;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;

import connec.SimpleClient;

/**
 * User class to represent a user of the program, defined by his/her username, password and Id
 * @author roman
 * @version 1.0
 * @see LogActionListener
 * @see RegisterActionListener
 *
 */
public class User implements Serializable {
	
	/**
	 * Username of the user
	 * @see LoggerGUI
	 * @see LogActionListener#LogActionListener(JTextField username, JTextField password, JFrame frame, SimpleClient c1)
	 * @see RegisterActionListener#RegisterActionListener(JTextField username, JTextField password, JFrame frame, SimpleClient c1)
	 */
	private String username;
	/**
	 * Password of the user
	 * @see LoggerGUI
	 * @see LogActionListener#LogActionListener(JTextField username, JTextField password, JFrame frame, SimpleClient c1)
	 * @see RegisterActionListener#RegisterActionListener(JTextField username, JTextField password, JFrame frame, SimpleClient c1)
	 */
	private String password;
	/**
	 * Id of the user, not entered by the user but generated by the program
	 * @see LoggerGUI
	 * @see LogActionListener#LogActionListener(JTextField username, JTextField password, JFrame frame, SimpleClient c1)
	 * @see RegisterActionListener#RegisterActionListener(JTextField username, JTextField password,JFrame frame, SimpleClient c1)
	 */
	private int id;
	
	/**
	 * List of Contacts attached to the User's account
	 */
	public ArrayList<Integer> Contacts = new ArrayList<Integer>();
	
public User(String username, String password, int id) {
		setUsername(username);
		setPassword(password);
		setId(id);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}
	
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public ArrayList<Integer> getContacts(){
		return Contacts;
	}
	
	public void AddContact(ArrayList<Integer> Contacts, Integer idToAdd) {
		Contacts.add(idToAdd);
	}
	
	public void removeContact(ArrayList<Integer> Contacts, Integer idToRemove) {
		Contacts.remove(idToRemove);
	}
}
