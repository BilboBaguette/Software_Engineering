package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ActionListeners implements ActionListener{
	
	private JComboBox<String> contactsAndGroups;
	
	private JComboBox<String> contactsOrGroups;
	
	private JTextField newContactName;
	
	public ActionListeners(JComboBox<String> contactsOrGroups) {
		this.contactsOrGroups=contactsOrGroups;
	}
	
	public ActionListeners(JComboBox<String> contactsAndGroups, JComboBox<String> contactsOrGroups) {
		this.contactsAndGroups=contactsAndGroups;
		this.contactsOrGroups=contactsOrGroups;
	}
	
	public ActionListeners(JTextField newContactName) {
		this.newContactName=newContactName;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "contactsAndGroups":
			String choice = contactsAndGroups.getSelectedItem().toString();
			switch(choice){
				case "Contacts":
					String tester1[] = {"Jean", "Pol", "Kim"};
					DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<String>(tester1);
					contactsOrGroups.setModel(model1);
					break;
				case "Groups":
					String tester2[] = {"Group1", "Group2"};
					DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<String>(tester2);
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
			System.out.println("Adding contact "+userToAdd); //TODO replace with method to add contact
			break;
		}
		
	}
	
	
}
