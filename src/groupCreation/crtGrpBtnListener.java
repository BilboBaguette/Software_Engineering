package groupCreation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;

import connec.SimpleClient;

public class crtGrpBtnListener implements ActionListener{
	

	/**
	 * SimpleClient variable that will allow us to send a message to the server
	 */
	private SimpleClient simpleClient;
	/**
	 * Frame variable
	 */
	private JFrame frame;
	/**
	 * FRame variable
	 */
	private JFrame frameMenu;
	
	private JList<String> contactList;
	
	
	/**
	 * Description: Constructor for the class SendButtonListener
	 * 
	 * @param textPane: The text area that we see our messages after we sent it
	 * @param textPane2: The text area that we type our messages
	 * @param sendList: List of messages that we have received or sent
	 * @param 
	 */
	/**
	 * 
	 * @param frameMenu The frame of the Menu's GUI
	 * @param frame the Frame of the GUI
	 * @param textPane The text area that we see our messages after we sent it
	 * @param textPane2 The text area that we type our messages
	 * @param sendList List of messages that we have received or sent
	 * @param sc The client connection variable
	 */
	public crtGrpBtnListener(JFrame frameMenu, JFrame frame, SimpleClient sc, JList<String> contactList)
	{
		this.frameMenu = frameMenu;
		this.frame = frame;
		this.simpleClient = sc;
		this.contactList=contactList;
	}

/**
 * Description: The action of displaying all messages sent
 * You cannot send a message if the typing area is empty
 * 
 * @param e: An action that occurs to the item when we perform an act
 * @see ActionListener
 */
	public void actionPerformed(ActionEvent e){
		switch(e.getActionCommand()) {
		case "send":

			simpleClient.sendNewGroupMembers( "send", contactList.getSelectedValuesList());
			
			frameMenu.setVisible(true);
			frame.setVisible(false);
			frame.dispose();
			break;
		case "Cancel":
			simpleClient.sendNewGroupMembers("cancel", contactList.getSelectedValuesList());

			break;
		}
	}
}
