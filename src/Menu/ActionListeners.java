package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import Chatroom.Chatroom;
import connec.SimpleClient;
import loginFeat.User;
import loginFeat.XMLUser;

/**
 * class that handles action performed by buttons and comboboxes from the menu GUI
 * @author roman
 * @see MenuGUI
 *
 */
public class ActionListeners implements ActionListener{
	/**
	 * combobox from the GUI used to select either contacts or groups
	 * @see MenuGUI
	 */
	private JComboBox<String> contactsAndGroups;
	/**
	 * combobox from the GUI used to select a specific contact or group
	 * @see MenuGUI
	 */
	private JComboBox<String> contactsOrGroups;
	/**
	 * textfield from the GUI used to enter the account name of a user you wish to add as a contact
	 */
	private JTextField newContactName;
	/**
	 * user connected to the menu
	 */
	private User user;
	
	/**
	 * Frame variable
	 */
	private JFrame frame;
	
	/**
	 * Client connection variable
	 */
	private SimpleClient c1;
	
	/**
	 * first constructor, used for the action listener of the contactsOrGroups combobox
	 * @param contactsOrGroups
	 * @see MenuGUI
	 */
	public ActionListeners(JFrame frame,User user, JComboBox<String> contactsOrGroups, SimpleClient c1) {
		this.contactsOrGroups=contactsOrGroups;
		this.c1 =c1;
		this.user=user;
		this.frame = frame;
	}
	/**
	 * second constructor, used for the action listener of the contactAndGroups combobox
	 * @param contactsAndGroups
	 * @param contactsOrGroups
	 * @see MenuGUI
	 */
	public ActionListeners(JComboBox<String> contactsAndGroups, JComboBox<String> contactsOrGroups, User user, SimpleClient c1) {
		this.contactsAndGroups=contactsAndGroups;
		this.contactsOrGroups=contactsOrGroups;
		this.user=user;
		this.c1 =c1;
	}
	/**
	 * third constructor, used for the add button that adds a new contact
	 * @param newContactName
	 * @see MenuGUI
	 */
	public ActionListeners(JTextField newContactName, User user, SimpleClient c1, JComboBox<String> contactsOrGroups) {
		this.newContactName=newContactName;
		this.user=user;
		this.c1 =c1;
		this.contactsOrGroups = contactsOrGroups;
	}

	@Override
	/**
	 * method used to perform actions, we used a switch/case to separate the different actions used by different buttons/comboboxes
	 */
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "contactsAndGroups":
			String choice = contactsAndGroups.getSelectedItem().toString();
			switch(choice){
				case "Contacts":
					//String contacts[] = user.getContacts();
					String tester1[] = {"Jean", "Pol", "Pot"};
					DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<String>(tester1);
					contactsOrGroups.setModel(model1);
					break;
				case "Groups":
					String groups[] = {"Group1", "Group2"}; //TODO replace with function to fetch group list
					DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<String>(groups);
					contactsOrGroups.setModel(model2);
					break;
			}
			break;
		case "contactsOrGroups":
			String selectedContact = contactsOrGroups.getSelectedItem().toString();
			ArrayList<String> members = new ArrayList<String>();
			members.add(user.getUsername());
			members.add(selectedContact);
			c1.startChatroom();
			new Chatroom(frame, members, c1);
			break;
		case "createGroup":
			//TODO group creation
			break;
		case "add":
			String userToAdd = newContactName.getText();
			c1.addUser(userToAdd);
			String tester2[] = c1.getContactList();
			DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<String>(tester2);
			contactsOrGroups.setModel(model1);
			break;
		}
		
	}
	
	
}
