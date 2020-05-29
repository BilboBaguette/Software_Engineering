package loginFeat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

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
	
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	int port = 6666;
	
	public RegisterActionListener(JTextField username, JTextField password) {
		this.username=username;
		this.password=password;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String usr = username.getText();
		String pwd = password.getText();
		boolean existingAccount=false;
		
		try{
			existingAccount =  usrCheck("localhost", usr);

			if(!existingAccount){ //if the account doesn't already exist, create it
				int id = createId();
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
	
	public boolean usrCheck(String ip, String usrname)
	{
		boolean usr = false;
        try {
        	socket = new Socket(ip, port);
    		//create the streams that will handle the objects coming and going through the sockets
    		output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());	
            
            output.writeObject((String)usrname);
            
            usr = (boolean)input.readObject();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

		} catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } 
        return usr;
	}
	
	public int createId()
	{
		int id= 0;
        try  {
        	System.out.println("ok");
			id = (int) input.readObject();	//deserialize and read the Student object from the stream
			System.out.println("ok2");
	    } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
		catch  (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
        return id;
	}

}
