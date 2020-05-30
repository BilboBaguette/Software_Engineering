package Chatroom;

import java.util.ArrayList;

public class testchatroom {

	public static void main(String[] args) throws Exception {
		//XMLLog.createLogXML();
		ArrayList<String> members = new ArrayList<String>();
		ArrayList<String> Users = new ArrayList<String>();
		ArrayList<String> mess = new ArrayList<String>();
		members.add("John");
		members.add("Sera");
		members.add("Paul");
		/*Messages message = new Messages("Jonh", "Hello");
		Messages message2 = new Messages("Sare", "HI!!!!!!!!!");
		members.add("John");
		members.add("Sera");
		
		XMLLog.createChatRoom(members);
		XMLLog.addToXML(message2, members);
		members.add("Paul");
		XMLLog.createChatRoom(members);
		XMLLog.addToXML(message, members);
		XMLLog.addToXML(message2, members);*/
		Users = XMLLog.readXMLLog("UserName", members);
		mess = XMLLog.readXMLLog("MessageContent", members);
		for(int i = 0; i < mess.size(); i++)
		{
			System.out.println(Users.get(i));
			System.out.println(mess.get(i));
		}
	}

}
