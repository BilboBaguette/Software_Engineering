package connec;
import java.io.*;  
import java.net.*; 

/*
 * Class name : FirstServer
 * 
 * Description : This is a class for setting up a server 
 * 
 * Version : 1.0
 * 
 * Date : 27/05/2020
 * 
 */



public class FirstServer extends AbstractServer
{
	/**
	 * The base ip address
	 */
	private String ip = "localhost";
	
	/**
	 * This the variable where we put the port for connecting 
	 */
	private ServerSocket ss;
	
	/**
	 * This function will allow us to connect to a server
	 * 
	 * @param ip Is the ip address of the network where we want to open a server 
	 * @see java Socket class
	 * @author Nils Chol; Jean-Louis Cheng
	 *
	 */
	public void connect(String ip) {
		try {
			//the server socket is defined only by a port (its IP is localhost)
			ss = new ServerSocket (6666);  
			System.out.println("Server waiting for connection...");
			while(true) {
				Socket socket = ss.accept();//establishes connection 
				System.out.println("Connected as " + ip);	
				// create a new thread to handle client socket	
				new ServerThread(socket).start();				
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			//if IOException close the server socket
			if (ss != null && !ss.isClosed()) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
}