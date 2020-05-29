package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

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
	 * first contructor, used for the action listener of the contactsOrGroups combobox
	 * @param contactsOrGroups
	 * @see MenuGUI
	 */
	public ActionListeners(JComboBox<String> contactsOrGroups) {
		this.contactsOrGroups=contactsOrGroups;
	}
	/**
	 * second constructor, used for the action listener of the contactAndGroups combobox
	 * @param contactsAndGroups
	 * @param contactsOrGroups
	 * @see MenuGUI
	 */
	public ActionListeners(JComboBox<String> contactsAndGroups, JComboBox<String> contactsOrGroups, User user) {
		this.contactsAndGroups=contactsAndGroups;
		this.contactsOrGroups=contactsOrGroups;
		this.user=user;
	}
	/**
	 * third constructor, used for the add button that adds a new contact
	 * @param newContactName
	 * @see MenuGUI
	 */
	public ActionListeners(JTextField newContactName, User user) {
		this.newContactName=newContactName;
		this.user=user;
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
			String selected = contactsOrGroups.getSelectedItem().toString();
			System.out.println("Starting convo with "+selected); //TODO replace with chatroom startup
			break;
		case "createGroup":
			//TODO group creation
			break;
		case "add":
			String userToAdd = newContactName.getText();
			//XMLUser.addContactToUserXML(user.getUsername(),userToAdd);
			System.out.println("Adding contact "+userToAdd); //TODO replace with method to add contact
			break;
		}
		
	}
	
	
}
