package groupCreation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;

import connec.SimpleClient;

/**
 * Class name : crtGrpBtnListener
 * This Class create the listener of the button "group"
 * 
 * Version : 1.0
 * 
 * @author Aurélien
 * 
 * Date : 31/05/2020
 */

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
	 * Frame variable
	 */
	private JFrame frameMenu;
	
	/**
	 * JList variable containing the list of an user's contact
	 */
	private JList<String> contactList;
	

	/**
	 * Constructor of the crtGrpBtnListener
	 * 
	 * @param frameMenu The frame of the Menu's GUI
	 * @param frame the Frame of the GUI
	 * @param sc The client connection variable
	 * @param contactList List of an user's contact
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
