package connec;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Chatroom.Messages;
import Chatroom.XMLLog;
 
/**
 * Class name : ServerThread 
 * This thread is responsible to handle client connection From the server side.
 * 
 * Version : 1.0
 * 
 * Date : 27/05/2020
 */
public class ServerThread extends Thread {
	
	/**
	 * This variable countains the server port 
	 */
    private Socket socket;
    /**
     * This stream will handle the objects going through the sockets
     */
	private ObjectInputStream input;
	/**
	 * This stream will handle the objects coming through the sockets
	 */
	private ObjectOutputStream output;
 
	/**
	 * This is the constructor of the class
	 * 
	 * @param socket Is the socket of the server 
	 * 
	 * @author Nils Chol; Jean-Louis Cheng
	 *
	 */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
	/**
	 * This function will run the client connection and executes the task leading to the exchange with the server
	 * 
	 * @see java Class Thread; java Class Socket; java Class ObjectInputStream; java Class ObjectOutputStream
	 * @author Nils Chol; Jean-Louis Cheng
	 *
	 */
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
				XMLLog.addToXML(new Messages("user", helo));
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