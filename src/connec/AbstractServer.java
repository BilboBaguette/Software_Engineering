package connec;

/*
 * Class name : AbstractServer
 * 
 * Description : This is an abstract class for setting up a server 
 * 
 * Version : 1.0
 * 
 * Date : 27/05/2020
 * 
 */

public abstract class AbstractServer
{
	
	/**
	 * This function will allow us to connect to a server
	 * 
	 * @param ip Is the ip address of the network where we want to open a server 
	 * 
	 * @author Nils Chol; Jean-Louis Cheng
	 *
	 */
	public abstract void connect(String ip);
} 