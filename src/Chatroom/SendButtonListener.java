/**
 *Description: Class that lets us send the text we have written in the text box
 *
 *@version 1.0
 *
 *@see Chatroom
 *@see ActionListener
 *@author OnurCan, Aurélien
 */

package Chatroom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextPane;


public class SendButtonListener implements ActionListener {
	private JTextPane textPane;
	private JTextPane TypingText;

	public List<Messages> myList = new ArrayList<Messages>();
	
/**
 * Description: Constructor for the class SendButtonListener
 * 
 * @param textPane: The text area that we see our messages after we sent it
 * @param textPane2: The text area that we type our messages
 * @param sendList: List of messages that we have received or sent
 * 
 */
	public SendButtonListener(JTextPane textPane, JTextPane textPane2,List<Messages> sendList)
	{
		this.textPane = textPane;
		this.TypingText =textPane2;
		this.myList = sendList;
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
		myList.add(new Messages("user",a));
		if(!TypingText.getText().isEmpty()){
			if(textPane.getText().isEmpty()){
				textPane.setText("user : " +  a+b);
				}else{
					textPane.setText(b+"\nuser : "+a);
				}
			}
		TypingText.setText("");
	}
}
