package loginFeat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JTextField;

import connec.SimpleClient;

/**
 * Class that handles the actionevent for the "register" button of the logger and oversees acount creation
 * @author roman
 * @version 1.0
 * @see Logger
 * @see LoggerGUI
 * 
 */
public class RegisterActionListener implements ActionListener{
	
	/**
	 * username textfield from the GUI
	 * @see LoggerGUI()
	 */
	private JTextField username;
	/**
	 * password textfield from the GUI
	 * @see LoggerGUI()
	 */
	private JTextField password;
	
	private SimpleClient c1;
	
	
	public RegisterActionListener(JTextField username, JTextField password, SimpleClient c1) {
		this.username=username;
		this.password=password;
		this.c1=c1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String usr = username.getText();
		String pwd = password.getText();
		boolean existingAccount=false;
		
		try{
			existingAccount =  c1.usrCheck("localhost", usr);

			if(!existingAccount){ //if the account doesn't already exist, create it
				int id = c1.createId();
				System.out.println(id);
				User user = new User(usr, pwd, id);
				XMLUser.addToXML(user); //update the logger XML
				XMLUser.addContactToUserXML(id, 52); 
				XMLUser.addContactToUserXML(id, 53);
				XMLUser.addContactToUserXML(id, 55);
				XMLUser.removeContactFromUserXML(id, 52);
				LoggerGUI.updateLogger();
				LoggerGUI.idConnectedUser = id;
			}
		}catch(Exception err){
			err.printStackTrace();
		} 
		username.setText("Account Name");
		password.setText("Password");
	}
	
}
