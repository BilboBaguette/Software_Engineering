package loginFeat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Class that handles the actionevent for the "login" button of the logger, checks if the entered credentials are correct
 * @author roman
 * @version 1.0
 * @see Logger
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
	
	public LogActionListener(JTextField username, JTextField password, JFrame frame) {
		this.username=username;
		this.password=password;
		this.frame=frame;
	}

	public void actionPerformed(ActionEvent e) {
		String usr = username.getText();
		String pwd = password.getText();
		if(Logger.match(usr, pwd, LoggerGUI.getData().users, LoggerGUI.getData().passwords)){ //check if the credentials correspond to an existing account
			LoggerGUI.idConnectedUser = Logger.findID(usr, pwd, LoggerGUI.getData().users, LoggerGUI.getData().passwords, LoggerGUI.getData().IdUser);
			frame.setVisible(false); //remove the logger
			frame.dispose();
			//new AppGUI(); //display the store GUI
		}
		else
		{
			JOptionPane.showMessageDialog(null,"User name or password don't match", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

}
