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
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import connec.SimpleClient;


public class Chatroom {


	/**
	 * Description: Constructor of the class chat room
     *
	 * @param frame The frame of the GUI
	 * @param members The list of members 
	 * @param c1 The client connection variable
	 */
	public Chatroom(JFrame frame, ArrayList<String> members, SimpleClient c1){
		
		initialize(frame, members, c1);
		
	}
	
    /**
     * Description: Creates the GUI
     * 2 text ares(one for writing and the other one for seeing the chat box) and a button to send a text.
     * 
     * @param frameMenu
     * @param members
     * @param c1
     * @see SendButtonListener#SendButtonListener
     */
	private void initialize(JFrame frameMenu, ArrayList<String> members, SimpleClient c1) {
		
		try {
			JScrollPane scrollPane;
			JFrame frame;		 
			 
			frame = new JFrame();
			frame.setBounds(100, 100, 706, 600);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			
			final JTextField Typingtext = new JTextField();
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
						


			String logs = c1.getLogs();
			textPane.setText(logs);

			Typingtext.addActionListener(new SendButtonListener(frameMenu, frame, textPane,Typingtext, c1));
			
			JButton btnSend = new JButton("Send");
			frameMenu.setVisible(false);
			btnSend.addActionListener(new SendButtonListener(frameMenu, frame, textPane,Typingtext, c1));
			btnSend.setBounds(486, 406, 89, 23);
			frame.getContentPane().add(btnSend);
			
			JLabel MemberList = new JLabel("");
	        MemberList.setBounds(123, 11, 444, 35);
	        frame.getContentPane().add(MemberList);
	        
	        MemberList.setText(c1.getMemberList());

	        JLabel HelpLabel = new JLabel("/quit to leave the chatroom");
	        HelpLabel.setEnabled(false);
	        HelpLabel.setBounds(123, 440, 131, 23);
	        frame.getContentPane().add(HelpLabel);
			
			frame.setVisible(true);			

		}catch(Exception e){
			e.getStackTrace();
		}
		



		
	}
}

