package loginFeat;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;
/**
 * Class name : FieldMouseListener
 * 
 * Description : This is a class gets the user's mouse actions
 * 
 * Version : 1.0
 * 
 * @author Roman
 * 
 * Date : 26/05/2020
 * 
 */

public class FieldMouseListener implements MouseListener{
	
	/**
	 * This variable contains text 
	 */
	private JTextField textfield;
	
	/**
	 * This is the constructor of the class
	 * @param textfield Field where text can be entered
	 */
	public FieldMouseListener(JTextField textfield) {
		this.textfield = textfield;
	}

	/**
	 * This function set the text of a text box when click by the mouse
	 * @param e This variable is the action of the mouse
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		textfield.setText("");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
