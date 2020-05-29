package connec;
import java.io.*;  
import java.net.*; 

public class SimpleClient {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	public String Senda;
	public volatile boolean messageSent = false;
	
	public void connect(String ip)
	{
		int port = 6666;
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			//socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets            
			String logs = (String) input.readObject();
			
			System.out.println(logs);

            
			while(true){
				if(messageSent==true) {
					output = new ObjectOutputStream(socket.getOutputStream());
					output.writeObject((String) Senda);
					messageSent =false;
				}
			}
		} catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	public String getLogs(String ip) {
		int port = 6666;
		String logs ="";
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			input = new ObjectInputStream(socket.getInputStream());
			
			logs = (String) input.readObject();

		} catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        
        return logs;
        
	}
}