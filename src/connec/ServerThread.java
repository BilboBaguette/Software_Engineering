package connec;
import java.io.*;
import java.net.*;
 
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
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
 
 			/*String text = (String)input.readObject();  //read the object received through the stream and deserialize it
			System.out.println("server received a text:" + text);
			
			Student student = new Student(1234, "john.doe");
			output.writeObject(student);		//serialize and write the Student object to the stream
			*/
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
        } finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
    }
}