package connec;


/*
 * Class name : ServerConnection
 * 
 * Description : This is a class that serves as a main for launching the server 
 * 
 * Version : 1.0
 * 
 * Date : 27/05/2020
 * 
 */

public class ServerConnection
{
	/**
	* This is the main,
	* Launch the application.
	* 
	* @param args basic main parameter
	*/
	public static void main (String[] args) {
		AbstractServer as = new FirstServer();
		String ip = "localhost";
		as.connect(ip);
		
	}
}