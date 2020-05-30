package Chatroom;

import Chatroom.Project;
import connec.SimpleClient;

/*
 * Class name : Project
 * Description : This is a class that serves as a main for the client
 * 
 * Version : 1.5
 * 
 * Date : 27/05/2020
 * 
 */

public class Project {
	/**
	* Launch the application.
	 */
	public static void main(String[] args) {
		new Project();
	}
	/**
	 * Create the application.
	 */
	public Project() {
		new Chatroom();
	}
}
