package connec;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Chatroom.XMLLog;
 
/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
    private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
 
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
			//create the streams that will handle the objects coming through the sockets
			ArrayList<String> messageContent = XMLLog.readXMLLog("MessageContent");
			ArrayList<String> messageUsername = XMLLog.readXMLLog("UserName");
			String wholeText = "";
			for(int i=0;i<messageContent.size()-1;i++) {
				wholeText += messageUsername.get(i) + ": " + messageContent.get(i) + "\n";
			}
			wholeText += messageUsername.get(messageUsername.size()-1) + ": " + messageContent.get(messageContent.size()-1);
						
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject((String) wholeText);

			
			while(true) {

				input = new ObjectInputStream(socket.getInputStream());
				String helo = (String) input.readObject();
				System.out.println("The server received: " + helo);
			}
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

		} catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } catch(Exception e){ 
        	e.printStackTrace();
        }finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
    }
}