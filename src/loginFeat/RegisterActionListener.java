package loginFeat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
	
	public RegisterActionListener(JTextField username, JTextField password) {
		this.username=username;
		this.password=password;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String usr = username.getText();
		String pwd = password.getText();
		boolean existingAccount=false;
		
		int id= (int)(Math.random() * 999999); //generate a random account id

		for(int i=0; i<LoggerGUI.getData().IdUser.size();i++) { //check that it is unique
			if(id==LoggerGUI.getData().IdUser.get(i)) {
				i=0;
				id= (int)(Math.random() * 999999);
			}
		}
		try{
			for(int i=0; i<LoggerGUI.getData().users.size(); i++){ //check that the account name is unique
				if(usr.compareTo(LoggerGUI.getData().users.get(i)) == 0){
					existingAccount=true;
					JOptionPane.showMessageDialog(null,"Account already exist", //if it isn't display an error message
							  "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
			if(!existingAccount){ //if the account doesn't already exist, create it
				User user = new User(usr, pwd, id);
				XMLUser.addToXML(user); //update the logger XML
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
