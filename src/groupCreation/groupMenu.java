package groupCreation;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import connec.SimpleClient;

public class groupMenu {

	public groupMenu(JFrame frameMenu, String[] contacts, SimpleClient c1) {
		JFrame frame;	
		frame = new JFrame();
		frame.setBounds(100, 100, 706, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel instructions = new JLabel("CTRL + Right click to select members");
		instructions.setBounds(123, 11, 444, 35);
        frame.getContentPane().add(instructions);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JList<String> contactList = new JList<String>(contacts);
		contactList.setBounds(123, 57, 444, 338);
		contactList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.getViewport().add(contactList);
		frame.add(contactList);
		
		JButton btnSend = new JButton("Create Group");
		frameMenu.setVisible(false);
		btnSend.addActionListener(new crtGrpBtnListener(frameMenu, frame, c1, contactList));
		btnSend.setActionCommand("send");
		btnSend.setBounds(486, 406, 89, 23);
		frame.getContentPane().add(btnSend);
		
		/*JButton btnCancel = new JButton("Cancel");
		frameMenu.setVisible(false);
		btnCancel.addActionListener(new crtGrpBtnListener(frameMenu, frame, c1, contactList));
		btnCancel.setActionCommand("cancel");
		btnSend.setBounds(406, 326, 89, 23);
		frame.getContentPane().add(btnSend);*/
		
		frame.setVisible(true);			
	}
}
