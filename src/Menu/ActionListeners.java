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
import groupCreation.groupMenu;
import loginFeat.User;

/**
 * class that handles action performed by buttons and comboboxes from the menu GUI
 * 
 * @author roman
 * 
 * @see MenuGUI
 *
 */
public class ActionListeners implements ActionListener{
	/**
	 * combobox from the GUI used to select either contacts or groups
	 * 
	 * @see MenuGUI
	 */
	private JComboBox<String> contactsAndGroups;
	/**
	 * combobox from the GUI used to select a specific contact or group
	 * 
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
	 * 
	 * @param frame frame variable
	 * @param user User variable
	 * @param contactsOrGroups variable displaying either contacts or groups 
	 * @param c1 Client variable
	 * @param contactsAndGroups variable displaying contacts and groups 
	 * 
	 * @see MenuGUI
	 */
	public ActionListeners(JFrame frame,User user, JComboBox<String> contactsOrGroups, SimpleClient c1, JComboBox<String> contactsAndGroups) {
		this.contactsOrGroups=contactsOrGroups;
		this.c1 =c1;
		this.user=user;
		this.frame = frame;
		this.contactsAndGroups = contactsAndGroups;
	}

	/**
	 * second constructor, used for the action listener of the contactAndGroups combobox
	 * 
	 * @param contactsAndGroups variable displaying contacts and groups 
	 * @param contactsOrGroups variable displaying either contacts or groups 
	 * @param user User variable
	 * @param c1 Client variable
	 * 
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
	 * 
	 * @param newContactName Contact name variable
	 * @param user user variable
	 * @param c1 Client variable
	 * @param contactsOrGroups variable displaying either contacts or groups 
	 * 
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
	 * 
	 * @param e actionevent variable
	 */
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "contactsAndGroups":
			String choice = contactsAndGroups.getSelectedItem().toString();
			c1.sendMessage(choice);
			switch(choice){
				case "Contacts":
					String tester2[] = c1.getContactList();
					DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<String>(tester2);
					contactsOrGroups.setModel(model1);
					break;
				case "Groups":
					String test4[] = c1.getContactList();
					DefaultComboBoxModel<String> model7 = new DefaultComboBoxModel<String>(test4);
					contactsOrGroups.setModel(model7);
					break;
			}
			break;
		case "contactsOrGroups":
			String choice2 = contactsAndGroups.getSelectedItem().toString();
			switch(choice2) {
			case "Contacts":
				//c1.sendMessage(choice2);
				String selectedContact = contactsOrGroups.getSelectedItem().toString();
				ArrayList<String> members = new ArrayList<String>();
				members.add(user.getUsername());
				members.add(selectedContact);
				c1.startChatroom(members, choice2);
				new Chatroom(frame, members, c1);
				break;
			case "Groups":
				break;
			}

			break;
		case "createGroup":
			c1.sendMessage("createGroup");
			String tester1[] = c1.getContactList();
			new groupMenu(frame, tester1, c1);
			break;
		case "add":
			String userToAdd = newContactName.getText();
			c1.addUser(userToAdd);
			String tester2[] = c1.getContactList();
			DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<String>(tester2);
			contactsOrGroups.setModel(model1);
			break;
		case "delete":
			String userToDelete = newContactName.getText();
			c1.deleteUser(userToDelete);
			String tester3[] = c1.getContactList();
			DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<String>(tester3);
			contactsOrGroups.setModel(model2);
			break;
		}
		
	}
	
	
}
