package loginFeat;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class FieldMouseListener implements MouseListener{
	
	private JTextField textfield;
	
	public FieldMouseListener(JTextField textfield) {
		this.textfield = textfield;
	}

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
