package Menu;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
		JLabel currentUserLabel = new JLabel("Current User:");
		addNewContactbtn = new JButton("Add");
		newGroupbtn = new JButton("Create Group");
		newContact = new JTextField("Account Name");
		newContact.setColumns(25);
		String contactsGroups[] = {"Contacts", "Groups"};
		contactsAndGroups = new JComboBox<String>(contactsGroups);
		String tester1[] = {"Jean", "Pol", "Kim"};
		contactsOrGroups = new JComboBox<String>(tester1);
		
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
		
		frame.pack();
		frame.setVisible(true);
	}

}
