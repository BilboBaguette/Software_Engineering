package loginFeat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import connec.SimpleClient;

/**
 * Class that handles GUI creation and setup for the logger
 * @author roman
 * @version 1.O
 * @see Logger
 * @see XMLUser
 * @see LogActionListener
 * @see RegisterActionListener
 *
 */
public class LoggerGUI{
	
	/**
	 * the frame of our logger
	 * @see LoggerGUI()
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */
	private JFrame frame = new JFrame("Login/Register");
	/**
	 * panel where the "login part" of the logger is
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */
	private JPanel loginpanel;
	/**
	 * panel where the "register part" of the logger is
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */
	private JPanel registerpanel;
	/**
	 * button to confirm login credentials
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */
	private JButton loginbtn;
	/**
	 * button to finalize account creation
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */
	private JButton registerbtn;
	/**
	 * user name field for the login part
	 * @see LogGUI()
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */
	private JTextField userNameFieldLogin;
	/**
	 * password field for the login part
	 * @see LoggerGUI()
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */
	private JTextField passwordFieldLogin;
	/**
	 * user name field for the registering process
	 * @see LoggerGUI()
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */
	private JTextField userNameFieldRegister;
	/**
	 * password field for the registering process
	 * @see LoggerGUI()
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */
	private JTextField passwordFieldRegister;
	/**
	 * id of the connected client, initally a negative value for easier error prevention
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */
	static public int idConnectedUser = -1;
	/**
	 * creation of a logger instance from the logger class
	 * @see LogActionListener.actionPerformed(ActionEvent)
	 * @see RegisterActionLister.actionPerformed(ActionEvent)
	 */

	
	public LoggerGUI() {
		//we setup the panels and boxes for the logger, and we setup our frame
		frame.setMinimumSize(new Dimension(640,480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginpanel = new JPanel();
		registerpanel = new JPanel();
		BoxLayout loginlayout = new BoxLayout(loginpanel,BoxLayout.Y_AXIS);
		BoxLayout registerlayout = new BoxLayout(registerpanel,BoxLayout.Y_AXIS);
		JLabel loginlabel = new JLabel("LOG IN");
		JLabel registerlabel = new JLabel("REGISTER");

		//We setup the buttons
		JPanel btnpanel1 = new JPanel();
		btnpanel1.setLayout(new BoxLayout(btnpanel1, BoxLayout.LINE_AXIS));
		JPanel btnpanel2 = new JPanel();
		btnpanel2.setLayout(new BoxLayout(btnpanel2, BoxLayout.LINE_AXIS));
		
		//borders for the panels
		loginpanel.setBorder(BorderFactory.createTitledBorder("Log In"));
		loginpanel.setLayout(loginlayout);
		loginpanel.setBorder(new EmptyBorder(new Insets(200,50,10,50)));
		btnpanel1.setBorder(BorderFactory.createEmptyBorder(0, 15, 200, 10));		
		registerpanel.setBorder(BorderFactory.createTitledBorder("Register"));
		registerpanel.setLayout(registerlayout);
		registerpanel.setBorder(new EmptyBorder(new Insets(150,50,10,50)));
		btnpanel2.setBorder(BorderFactory.createEmptyBorder(0, 15, 150, 10));
		
		//Buttons and textFields setup
		loginbtn = new JButton("Log In");
		registerbtn = new JButton("Register");
		userNameFieldLogin = new JTextField("Account Name");
		userNameFieldLogin.setColumns(25);
		passwordFieldLogin = new JTextField("Password");
		passwordFieldLogin.setColumns(25);
		userNameFieldRegister = new JTextField("Account Name");
		userNameFieldRegister.setColumns(25);
		passwordFieldRegister = new JTextField("Password");
		passwordFieldRegister.setColumns(25);
				
		//adding panels, fields and buttons to the frame
		frame.add(loginpanel, BorderLayout.WEST);
		loginpanel.add(loginlabel);
		loginpanel.add(userNameFieldLogin);
		loginpanel.add(passwordFieldLogin);
		loginpanel.add(btnpanel1, BorderLayout.CENTER);
		btnpanel1.add(loginbtn);
		frame.add(registerpanel, BorderLayout.EAST);
		registerpanel.add(registerlabel);
		registerpanel.add(userNameFieldRegister);
		registerpanel.add(passwordFieldRegister);
		registerpanel.add(btnpanel2, BorderLayout.CENTER);
		btnpanel2.add(registerbtn);

		frame.pack();

		//Adding mouse listeners to the textfields, to clear them when the user clicks on them.
		MouseListener userNameFieldLoginListener = new FieldMouseListener(userNameFieldLogin);
		userNameFieldLogin.addMouseListener(userNameFieldLoginListener);
		MouseListener passwordFieldLoginListener = new FieldMouseListener(passwordFieldLogin);
		passwordFieldLogin.addMouseListener(passwordFieldLoginListener);
		MouseListener userNameFieldRegisterListener = new FieldMouseListener(userNameFieldRegister);
		userNameFieldRegister.addMouseListener(userNameFieldRegisterListener);
		MouseListener passwordFieldRegisterListener = new FieldMouseListener(passwordFieldRegister);
		passwordFieldRegister.addMouseListener(passwordFieldRegisterListener);
		SimpleClient c1 = new SimpleClient();

		c1.connect("localhost");

		//Adding action listeners to the buttons
		ActionListener loginListener = new LogActionListener(userNameFieldLogin, passwordFieldLogin, frame, c1);
		loginbtn.addActionListener(loginListener);
		ActionListener registerListener = new RegisterActionListener(userNameFieldRegister, passwordFieldRegister,frame, c1);
		registerbtn.addActionListener(registerListener);
		frame.setVisible(true);
	}
	
}
