package loginFeat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import Menu.MenuGUI;
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
	/**
	 * frame from the GUI
	 * @see LoggerGUI()
	 */
	private JFrame frame;
	/**
	 * This variable is a client 
	 */
	private SimpleClient c1;
	
	/**
	 * This is the constructor
	 * @param username
	 * @param password
	 * @param frame
	 * @param c1
	 */
	public RegisterActionListener(JTextField username, JTextField password,JFrame frame, SimpleClient c1) {
		this.username=username;
		this.password=password;
		this.c1=c1;
		this.frame = frame;
	}

	/**
	 * This class register the user's credentials and link the results to the GUI
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String usr = username.getText();
		String pwd = password.getText();
		boolean existingAccount=false;
		
		try{
			existingAccount =  c1.usrCheck(usr,pwd);

			if(!existingAccount){ //if the account doesn't already exist, create it
				int id = c1.createId();
				//System.out.println(id);
				User test=c1.connectedAccount;
				frame.setVisible(false); //remove the logger
				frame.dispose();
				new MenuGUI(test, c1);
				LoggerGUI.idConnectedUser = id;
			}
		}catch(Exception err){
			err.printStackTrace();
		} 
		username.setText("Account Name");
		password.setText("Password");
	}
}
