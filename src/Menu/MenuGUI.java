package Menu;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connec.SimpleClient;
import loginFeat.User;
/**
 * Class used to create the Menu GUI, allowing the user to select which contact or group he wants to chat with, add new contacts and create groups
 * @author roman
 * @see ActionListeners
 */
public class MenuGUI {
	/**
	 * frame of the Menu
	 */
	private JFrame frame;
	/**
	 * top panel used to display the name fo the current connected user
	 */
	private JPanel currentUserPanel;
	/**
	 * center panel used to contain two comboboxes to select contacts or groups and the button to create groups
	 */
	private JPanel selectContactsAndGroups;
	/**
	 * bottom panel used to contain the textfield and button to select and add a user as a new contact
	 */
	private JPanel addNewContact;
	/**
	 * combobox used to select either the contacts list or the groups list
	 */
	private JComboBox<String> contactsAndGroups;
	/**
	 * combobox that displays either the contacts list or the groups list
	 */
	private JComboBox<String> contactsOrGroups;
	/**
	 * textfield used to enter the name of the user you wish to add as a new contact
	 */
	private JTextField newContact;
	/**
	 * button used to add a new contact
	 */
	private JButton addNewContactbtn;
	/**
	 * button used to create a new group
	 */
	private JButton newGroupbtn;
	
	private User currentUser;
	
	private SimpleClient c1;
	
	/**
	 * Create the application.
	 */
	public MenuGUI(User user, SimpleClient sc) {
		this.currentUser=user;
		this.c1 = sc;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 100, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//panels, labels, comboboxes and buttons setup
		currentUserPanel = new JPanel();
		selectContactsAndGroups = new JPanel();
		addNewContact = new JPanel();
		JPanel newContactbtnPane = new JPanel();
		JPanel newGroupbtnPane = new JPanel();
		JLabel currentUserLabel = new JLabel("Current User: "+currentUser.getUsername()+" ID: "+currentUser.getId());
		addNewContactbtn = new JButton("Add");
		newGroupbtn = new JButton("Create Group");
		newContact = new JTextField("Account Name");
		newContact.setColumns(25);
		String contactsGroups[] = {"Contacts", "Groups"};
		contactsAndGroups = new JComboBox<String>(contactsGroups);
		contactsOrGroups = new JComboBox<String>();
		//layout and borders setup
		BoxLayout userLayout = new BoxLayout(currentUserPanel, BoxLayout.X_AXIS);
		BoxLayout contactsAndGroupsLayout = new BoxLayout(selectContactsAndGroups, BoxLayout.X_AXIS);
		BoxLayout newContactLayout = new BoxLayout(addNewContact, BoxLayout.X_AXIS);
		currentUserPanel.setBorder(BorderFactory.createTitledBorder(""));
		currentUserPanel.setLayout(userLayout);
		selectContactsAndGroups.setBorder(BorderFactory.createTitledBorder("Contacts and Groups"));
		selectContactsAndGroups.setLayout(contactsAndGroupsLayout);
		addNewContact.setBorder(BorderFactory.createTitledBorder("Add new Contact"));
		addNewContact.setLayout(newContactLayout);
		newContactbtnPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 100, 10));
		newGroupbtnPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 100, 10));
		//adding elements to the frame
		frame.add(currentUserPanel, BorderLayout.NORTH);
		currentUserPanel.add(currentUserLabel);
		frame.add(selectContactsAndGroups, BorderLayout.CENTER);
		selectContactsAndGroups.add(contactsAndGroups);
		selectContactsAndGroups.add(contactsOrGroups);
		selectContactsAndGroups.add(newGroupbtn);
		frame.add(addNewContact, BorderLayout.SOUTH);
		addNewContact.add(newContact);
		addNewContact.add(addNewContactbtn);
		//Adding MouseListener and ActionListeners
		MouseListener newContactFieldMouseListener = new loginFeat.FieldMouseListener(newContact);
		newContact.addMouseListener(newContactFieldMouseListener);
		
		String tester2[] = c1.getContactList();
		DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<String>(tester2);
		contactsOrGroups.setModel(model1);
		contactsAndGroups.addActionListener(new ActionListeners(contactsAndGroups, contactsOrGroups, currentUser, c1));
		contactsAndGroups.setActionCommand("contactsAndGroups");
		contactsOrGroups.addActionListener(new ActionListeners(frame, currentUser, contactsOrGroups, c1));
		contactsOrGroups.setActionCommand("contactsOrGroups");
		addNewContactbtn.addActionListener(new ActionListeners(newContact, currentUser, c1, contactsOrGroups));
		addNewContactbtn.setActionCommand("add");
		//Display the frame
		frame.pack();
		frame.setVisible(true);
	}

}
