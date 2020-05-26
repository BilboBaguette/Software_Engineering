package Chatroom;

/**
 *Description: Class that creates a GUI of a chat room 
 *The users can see the messages in the chat
 *
 *@version 1.0
 *
 *@see SendButtonListener
 *@author OnurCan, Aurélien
 */



import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;


public class Chatroom {

	public List<Messages> myList = new ArrayList<Messages>();
	
    /**
     * Description: Constructor of the class chat room
     */
	public Chatroom(){
		
		initialize();
		
	}
	
    /**
     * Description: Creates the GUI
     * 2 text ares(one for writing and the other one for seeing the chat box) and a button to send a text.
     * 
     * @see SendButtonListener#SendButtonListener
     */
	private void initialize() {

		JScrollPane scrollPane;
		JFrame frame;		 
		 
		frame = new JFrame();
		frame.setBounds(100, 100, 706, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JTextPane Typingtext = new JTextPane();
		Typingtext.setBounds(123, 406, 272, 23);
		frame.getContentPane().add(Typingtext);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		final JTextPane textPane = new JTextPane();
		textPane.setEnabled(false);
		textPane.setBounds(123, 57, 444, 338);
		
		scrollPane.setBounds(123, 57, 444, 338);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.getViewport().add(textPane);
		frame.add(scrollPane);
		
		JButton btnSend = new JButton("Send");
		/*uses Class ActionIsComming*/
		btnSend.addActionListener(new SendButtonListener(textPane,Typingtext,myList));
		btnSend.setBounds(486, 406, 89, 23);
		frame.getContentPane().add(btnSend);
		
		frame.setVisible(true);
		
	}
}

