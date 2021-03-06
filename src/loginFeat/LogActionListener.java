package loginFeat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import connec.SimpleClient;

/**
 * Class that handles the action event for the "login" button of the logger, checks if the entered credentials are correct
 * 
 * @author roman
 * @version 1.0
 * @see LoggerGUI
 *
 */
public class LogActionListener implements ActionListener{
	
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
	 * 
	 * @param username Name of the user
	 * @param password password of the user
	 * @param frame frame variable
	 * @param c1 SimpleClient variable
	 */
	public LogActionListener(JTextField username, JTextField password, JFrame frame, SimpleClient c1) {
		this.username=username;
		this.password=password;
		this.frame=frame;
		this.c1=c1;
	}

	/**
	 * This class check if the user has correct credentials and link the results to the GUI
	 * @param e actionevent variable
	 */
	public void actionPerformed(ActionEvent e) {
		String usr = username.getText();
		String pwd = password.getText();
		if(c1.loginCheck("localhost", usr, pwd)){ //check if the credentials correspond to an existing account
			User test = c1.connectedAccount;
			frame.setVisible(false); //remove the logger
			frame.dispose();
			new Menu.MenuGUI(test, c1); //display the Menu GUI
		}
		else
		{
			JOptionPane.showMessageDialog(null,"User name or password don't match", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

}
