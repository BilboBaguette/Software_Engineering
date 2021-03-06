/**
 *Description: Class that lets us send the text we have written in the text box
 *
 *@version 1.0
 *
 *@see Chatroom
 *@see ActionListener
 *@author OnurCan, Aur�lien
 */

package Chatroom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import connec.SimpleClient;


public class SendButtonListener implements ActionListener {
	
	/**
	 * Variable containing text
	 */
	private JTextPane textPane;
	/**
	 * Variable containing space to write text
	 */
	private JTextField TypingText;
	/**
	 * SimpleClient variable that will allow us to send a message to the server
	 */
	private SimpleClient simpleClient;
	/**
	 * Frame variable
	 */
	private JFrame frame;
	/**
	 * FRame variable
	 */
	private JFrame frameMenu;
	

	
	/**
	 * Description: Constructor for the class SendButtonListener
	 * 
	 * @param frameMenu The frame of the Menu's GUI
	 * @param frame the Frame of the GUI
	 * @param textPane The text area that we see our messages after we sent it
	 * @param textPane2 The text area that we type our messages
	 * @param sc The client connection variable
	 */
	public SendButtonListener(JFrame frameMenu, JFrame frame,JTextPane textPane, JTextField textPane2, SimpleClient sc)
	{
		this.frameMenu = frameMenu;
		this.frame = frame;
		this.textPane = textPane;
		this.TypingText =textPane2;
		this.simpleClient = sc;
	}
	
	/**
	 * Description: The action of displaying all messages sent
	 * You cannot send a message if the typing area is empty
	 * 
	 * @param e: An action that occurs to the item when we perform an act
	 * @see ActionListener
	 */
	public void actionPerformed(ActionEvent e){
		String a,b;
		a = TypingText.getText();
		b = textPane.getText();
		if(a.compareTo("/quit")==0) {
			frameMenu.setVisible(true);
			frame.setVisible(false);
			frame.dispose();
		} 
		if(!TypingText.getText().isEmpty()){
			//myList.add(new Messages("user",a));
			simpleClient.sendMessage(a);
			if(a.compareTo("/quit")==0) {
				frameMenu.setVisible(true);
				frame.setVisible(false);
				frame.dispose();
			}else {
				textPane.setText(simpleClient.getLogs());
			}
		}
		TypingText.setText("");
	}
}
