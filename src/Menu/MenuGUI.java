package Menu;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuGUI {

	private JFrame frame;
	
	private JPanel currentUserPanel;
	
	private JPanel selectContactsAndGroups;
	
	private JPanel addNewContact;
	
	private JComboBox<String> contactsAndGroups;
	
	private JComboBox<String> contactsOrGroups;
	
	private JTextField newContact;
	
	private JButton addNewContactbtn;
	
	private JButton newGroupbtn;
	
	/**
	 * Create the application.
	 */
	public MenuGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 100, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		currentUserPanel = new JPanel();
		selectContactsAndGroups = new JPanel();
		addNewContact = new JPanel();
		JPanel newContactbtnPane = new JPanel();
		JPanel newGroupbtnPane = new JPanel();
		JLabel currentUserLabel = new JLabel("Current User:"); //TODO fetch and display current user name
		addNewContactbtn = new JButton("Add");
		newGroupbtn = new JButton("Create Group");
		newContact = new JTextField("Account Name");
		newContact.setColumns(25);
		String contactsGroups[] = {"Contacts", "Groups"};
		contactsAndGroups = new JComboBox<String>(contactsGroups);
		contactsOrGroups = new JComboBox<String>();
		
		BoxLayout userLayout = new BoxLayout(currentUserPanel, BoxLayout.X_AXIS);
		BoxLayout contactsAndGroupsLayout = new BoxLayout(selectContactsAndGroups, BoxLayout.X_AXIS);
		BoxLayout newContactLayout = new BoxLayout(addNewContact, BoxLayout.X_AXIS);
		
		currentUserPanel.setBorder(BorderFactory.createTitledBorder(""));
		currentUserPanel.setLayout(userLayout);
		//currentUserPanel.setBorder(new EmptyBorder());
		selectContactsAndGroups.setBorder(BorderFactory.createTitledBorder("Contacts and Groups"));
		selectContactsAndGroups.setLayout(contactsAndGroupsLayout);
		//currentUserPanel.setBorder(new EmptyBorder());
		addNewContact.setBorder(BorderFactory.createTitledBorder("Add new Contact"));
		addNewContact.setLayout(newContactLayout);
		newContactbtnPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 100, 10));
		newGroupbtnPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 100, 10));
		
		frame.add(currentUserPanel, BorderLayout.NORTH);
		currentUserPanel.add(currentUserLabel);
		frame.add(selectContactsAndGroups, BorderLayout.CENTER);
		selectContactsAndGroups.add(contactsAndGroups);
		selectContactsAndGroups.add(contactsOrGroups);
		selectContactsAndGroups.add(newGroupbtn);
		frame.add(addNewContact, BorderLayout.SOUTH);
		addNewContact.add(newContact);
		addNewContact.add(addNewContactbtn);
		
		MouseListener newContactFieldMouseListener = new loginFeat.FieldMouseListener(newContact);
		newContact.addMouseListener(newContactFieldMouseListener);
		contactsAndGroups.addActionListener(new ActionListeners(contactsAndGroups, contactsOrGroups));
		contactsAndGroups.setActionCommand("contactsAndGroups");
		contactsOrGroups.addActionListener(new ActionListeners(contactsOrGroups));
		contactsOrGroups.setActionCommand("contactsOrGroups");
		addNewContactbtn.addActionListener(new ActionListeners(newContact));
		addNewContactbtn.setActionCommand("add");
		
		frame.pack();
		frame.setVisible(true);
	}

}
