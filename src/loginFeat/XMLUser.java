package loginFeat;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class that allows to manipulate the xml file of login informations.
 * It allows to create the file, update it, and some more useful things.
 *
 * @version 1.0
 *
 * @see logger
 *
 * @author Roman DIDELET
 * @author Jason KHAOU
 */

public class XMLUser {
	
	/**
	 * Method that creates the XML file
	 */
	public static void createUserXML() throws Exception 
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); //Creation of elements to include in the XML file
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		Document document = documentBuilder.newDocument();
		
		Element element = document.createElement("Accounts"); // Creating the "root" node
		document.appendChild(element);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance(); // Creation of the file
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		
		StreamResult streamResult = new StreamResult(new File("./User.xml"));
		
		transformer.transform(source, streamResult);
	}
	
	/**
	 * Method that adds an User to the XML file
	 * 
	 * @param userToAdd
	 */
	public static void addToXML(User userToAdd) throws Exception 
	{
		File xmlFile = new File("./User.xml");
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); //Creation of elements to include in the XML file
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		Element element = document.createElement("User"); //Sub Node of the root "account"
		Element root = document.getDocumentElement();
        root.appendChild(element);
		
		Attr attr = document.createAttribute("id"); //Giving an ID to the newly added User as an attribute
        attr.setValue(Integer.toString(userToAdd.getId()));
        element.setAttributeNode(attr);
        
        Element userName = document.createElement("UserName"); //Sub Node of the User Node containing the username
    	userName.appendChild(document.createTextNode(userToAdd.getUsername()));
    	element.appendChild(userName);
    	
    	Element password = document.createElement("Password"); //Sub Node of the User Node containing the password
    	password.appendChild(document.createTextNode(userToAdd.getPassword()));
    	element.appendChild(password);
    		
    	Element contacts = document.createElement("Contacts"); //Sub Node of the User Node which will contain the contact list
    	element.appendChild(contacts);

        TransformerFactory factory = TransformerFactory.newInstance(); //Rebuilding the XML file and replace the old one
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./User.xml"));
        transformer.transform(domSource, streamResult);
	}
	
	/**
	 * Method that adds contact to an user's contact list
	 * 
	 * @param idUser
	 * @param idToAdd
	 */
	public static void addContactToUserXML(Integer idUser, Integer idToAdd) throws Exception 
	{
		File xmlFile = new File("./User.xml");
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); //Creation of elements to include in the XML file
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		Node contacts = document.getFirstChild();
		NodeList userList = contacts.getChildNodes();
		
		for (int i = 0; i < userList.getLength(); i++) //Going through the list of user
		{
			Node element =  userList.item(i);
			
			String attr = element.getAttributes().getNamedItem("id").getNodeValue();
			if(Integer.toString(idUser).equals(attr)) //If it is the user we are looking for
			{
				NodeList subUserList = element.getChildNodes();
				for (int j = 0; j < subUserList.getLength(); j++) { //Looking for the sub Node "Contacts"
					
	                Node subElement = subUserList.item(j);
	                if ("Contacts".equals(subElement.getNodeName())) { //If found then add an ID to the contact list
	                	Element contact = document.createElement("Contact");
	                	contact.appendChild(document.createTextNode(Integer.toString(idToAdd)));
	                	((Node) subUserList).appendChild(contact);
	                }
	            }
			}
		}
		TransformerFactory factory = TransformerFactory.newInstance(); //Rebuilding the XML file and replace the old one
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./User.xml"));
        transformer.transform(domSource, streamResult);	 
	}
	
	/**
	 * Method that removes contact from an user's contact list
	 * 
	 * @param idUser
	 * @param idToRemove
	 */
	public static void removeContactFromUserXML(Integer idUser, Integer idToRemove) throws Exception 
	{
		File xmlFile = new File("./User.xml");
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); //Creation of elements to include in the XML file
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		Node contacts = document.getFirstChild();
		NodeList userList = contacts.getChildNodes();
		
		for (int i = 0; i < userList.getLength(); i++) //Going through the list of user
		{
			Node element =  userList.item(i);
			
			String attr = element.getAttributes().getNamedItem("id").getNodeValue();
			if(Integer.toString(idUser).equals(attr)) //If it is the user we are looking for
			{
				NodeList subUserList = element.getChildNodes();
				for (int j = 0; j < subUserList.getLength(); j++) //Looking for the sub Node "Contacts"
				{
	                Node subElement = subUserList.item(j);
	                if ("Contacts".equals(subElement.getNodeName())) //If found then go through the list of contact
	                {
	                	NodeList subSubUserList = element.getChildNodes();
	    				for (int n = 0; n < subSubUserList.getLength(); n++) 
	    				{
	    	                Node subSubElement = subSubUserList.item(n);  				
	    	    	        if (Integer.toString(idToRemove).equals(subSubElement.getTextContent())) //If we find the ID then we delete it.
	    	    	        {
	    	    	        	((Node) subUserList).removeChild(subSubElement);
	    	    	        }
	    	            }
	                }
	            }
			}
		}
		TransformerFactory factory = TransformerFactory.newInstance();  //Rebuilding the XML file and replace the old one
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./User.xml"));
        transformer.transform(domSource, streamResult);	 
	}
	
	/**
	 * Method that gives a list of either username, password or id
	 * 
	 * @param choice
	 *
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> readXMLUser(String choice) throws Exception 
	{
		File xmlFile = new File("./User.xml");
		ArrayList<String> toReturn = new ArrayList<String>();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); //Creation of elements to include in the XML file
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		NodeList list = document.getElementsByTagName("User");
		
		for(int i = 0; i < list.getLength(); i++) //Going through the list of users
		{
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) node; 
				switch(choice) { //Either add username, password or id to the ArrayList depending on the choice parameter
				case "UserName": 
					toReturn.add(element.getElementsByTagName("UserName").item(0).getTextContent());
					break;
				case "Password":
					toReturn.add(element.getElementsByTagName("Password").item(0).getTextContent());
					break;
				case "ID":
					toReturn.add(element.getAttribute("id"));
					break;
				}
			}
		}
		return toReturn;
	}
	
	
	/**
	 * Method that gives a list of lists of contacts
	 *
	 * @return  ArrayList<ArrayList<String>>
	 */
	public static ArrayList<ArrayList<String>> readContactXMLUser() throws Exception
	{
		File xmlFile = new File("./User.xml");
		ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();  //Creation of elements to include in the XML file
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		NodeList list = document.getElementsByTagName("User");
		
		for(int i = 0; i < list.getLength(); i++) //Going through the list of users
		{
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) node;
				NodeList subUserList = element.getChildNodes();
				ArrayList<String> contactsList = new ArrayList<String>();
				for (int j = 0; j < subUserList.getLength(); j++) { //Add all the contacts of an user to a ArrayList
		            Node subElement = subUserList.item(j);
		            contactsList.add(subElement.getTextContent());	                
		        }
				toReturn.add(contactsList); //Add the previously created list to another ArrayList.
			}
		}
		return toReturn;
	}
}