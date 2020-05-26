package Chatroom;

/**
 *Description: Class that represents messages in a chat room
 *It links a user with the messages that he sent
 *
 *@version 1.0
 *
 *@author OnurCan, Aurélien
 */


public class Messages {
    String username;
    String message;
    
    /**
     * Description: Constructor of the class messages
     * 
     * @param username: the name of the user
     * @param message: the user's message
     */
    public Messages (String username , String message) {
		this.username = username;
		this.message = message;
	}
    
    /**
     * Description: Fetches the user's name
     * 
     * @return the user's name
     */
	public String getUsername() {
		return username;
	}
	
	/** 
	 * Description: Fetches the user's message
	 * 
	 * @return the user's message
	 */
	public String getMessage() {
		return message;
	}		
}

