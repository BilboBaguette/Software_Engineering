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
 *
 * @author Roman DIDELET
 * @author Jason KHAOU
 */

public class XMLUser {
	

	/**
	 * Method that creates the XML file
	 * 
	 * @throws Exception
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
	 * @param userToAdd User that will be added
	 * 
	 * @throws Exception
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
    	
    	Element groups = document.createElement("Groups");
    	element.appendChild(groups);

        TransformerFactory factory = TransformerFactory.newInstance(); //Rebuilding the XML file and replace the old one
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./User.xml"));
        transformer.transform(domSource, streamResult);
	}
	
	/**
	 * Method that adds contact to an user's contact list
	 * 
	 * @param idUser id of a User
	 * @param usernameToAdd Username of a user to add 
	 * 
	 * @throws Exception
	 */
	public static void addContactToUserXML(Integer idUser, String usernameToAdd) throws Exception 
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
	                	
	                	NodeList subSubUserList = element.getChildNodes();
	                	boolean check = false;
	                	for (int n = 0; n < subSubUserList.getLength(); n++) 
	    				{
	    	                Node subSubElement = subSubUserList.item(n);  				
	    	    	        if (usernameToAdd.equals(subSubElement.getTextContent())) //If we find the ID then we delete it.
	    	    	        {
	    	    	        	check = true;
	    	    	        }
	    	            }
	                	if(!check)
	                	{
	                		Element contact = document.createElement("Contact");
	                		contact.appendChild(document.createTextNode(usernameToAdd));
	                		((Node) subElement).appendChild(contact);
	                	}
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
	 * Function to add a group to the XML containing Chatroom info
	 * 
	 * @param idUser ID of a user
	 * @param groupMembers List of a all the group member 
	 * 
	 * @throws Exception
	 */
	public static void addGroupToUserXML(Integer idUser, ArrayList<String> groupMembers) throws Exception 
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
	                if ("Groups".equals(subElement.getNodeName())) { //If found then add an ID to the contact list
	                	boolean check = true;
	                	NodeList subSubUserList = subElement.getChildNodes();
	                	for (int n = 0; n < subSubUserList.getLength(); n++) 
	    				{
	                		System.out.println("test");
	                		Node group = subSubUserList.item(n);
	                		if("Group".equals(group.getNodeName())) {
		    	                NodeList groupMember = group.getChildNodes();
			                	check=false;
		    	                for(int z=0;z<groupMember.getLength();z++) {
		    	                	Node member = groupMember.item(z);
		    	                	for(int w=0;w<groupMembers.size();w++) {
				    	    	        if (!groupMembers.get(w).equals(member.getTextContent())) //If we find the ID then we delete it.
				    	    	        {
				    	    	        	check = true;
				    	    	        }
		    	                	}
		    	                }
		    	                if(groupMember.getLength()==0) {
		    	                	check = true;
		    	                }
	                		}

	    	            }
	                	if(check)
	                	{
	                		Element contact = document.createElement("Group");
	                		for(int m=0; m<groupMembers.size();m++) {
	                			Element member = document.createElement("Member");
		                		member.appendChild(document.createTextNode(groupMembers.get(m)));
		                		((Node) contact).appendChild(member);
	                		}
		                	//contact.appendChild(document.createTextNode(groupMembers.get(m)));
		                	((Node) subSubUserList).appendChild(contact);
	                	}
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
	 * @param idUser id of a User
	 * @param usernameToRemove User name of a user that will be removed
	 * 
	 * @throws Exception
	 */
	public static void removeContactFromUserXML(Integer idUser, String usernameToRemove) throws Exception 
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
	                	NodeList subSubUserList = subElement.getChildNodes();
	    				for (int n = 0; n < subSubUserList.getLength(); n++) 
	    				{
	    	                Node subSubElement = subSubUserList.item(n); 
	    	    	        if (usernameToRemove.equals(subSubElement.getTextContent())) //If we find the ID then we delete it.
	    	    	        {
	    	    	        	((Node) subSubUserList).removeChild(subSubElement);
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
	 * @param choice the choice 
	 *
	 * @return ArrayList
	 * 
	 * @throws Exception
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
	 * @return ArrayList
	 * 
	 * @throws Exception
	 */
	public static ArrayList<ArrayList<String>> readContactXMLUser() throws Exception
    {
        File xmlFile = new File("./User.xml");
        ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();  //Creation of elements to include in the XML file
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        Node contacts = document.getFirstChild();
        NodeList userList = contacts.getChildNodes();

        for (int i = 0; i < userList.getLength(); i++) //Going through the list of user
        {
            Node element =  userList.item(i);
                NodeList subUserList = element.getChildNodes();
                for (int j = 0; j < subUserList.getLength(); j++) //Looking for the sub Node "Contacts"
                {
                    Node subElement = subUserList.item(j);
                    if ("Contacts".equals(subElement.getNodeName())) //If found then go through the list of contact
                    {
                        NodeList subSubUserList = subElement.getChildNodes();
                        ArrayList<String> contactsList = new ArrayList<String>();
                        for (int n = 0; n < subSubUserList.getLength(); n++) 
                        {
                            Node subSubElement = subSubUserList.item(n);
                            contactsList.add(subSubElement.getTextContent());
                        }
                        toReturn.add(contactsList);
                    }
                }
            }
        return toReturn;
        }
	/**
	 * This function reads all the groups of all users in the XML file 
	 * 
	 * @return The arraylist
	 * @throws Exception
	 */
	public static ArrayList<ArrayList<ArrayList<String>>> readGroupXMLUser() throws Exception
    {
        File xmlFile = new File("./User.xml");
        ArrayList<ArrayList<ArrayList<String>>> toReturn = new ArrayList<ArrayList<ArrayList<String>>>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();  //Creation of elements to include in the XML file
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        Node contacts = document.getFirstChild();
        NodeList userList = contacts.getChildNodes();

        for (int i = 0; i < userList.getLength(); i++) //Going through the list of user
        {
            Node element =  userList.item(i);
                NodeList subUserList = element.getChildNodes();
                for (int j = 0; j < subUserList.getLength(); j++) //Looking for the sub Node "Contacts"
                {
                    Node subElement = subUserList.item(j);
                    if ("Groups".equals(subElement.getNodeName())) //If found then go through the list of contact
                    {
                        NodeList subSubUserList = subElement.getChildNodes();//Here, we get the nodes name "Group"
                        ArrayList<ArrayList<String>> groupList = new ArrayList<ArrayList<String>>();
                        for(int k=0; k<subSubUserList.getLength();k++) {
                        	Node member = subSubUserList.item(k);//Here we have a group
                        	NodeList memberList = member.getChildNodes();
                        	ArrayList<String> members = new ArrayList<String>();
                        	for(int l=0;l<memberList.getLength();l++) {
                        		Node singleMember = memberList.item(l);
                        		members.add(singleMember.getTextContent());
                        	}
                        	groupList.add(members);
                        }
                        toReturn.add(groupList);
                    }
                }
            }
        return toReturn;
        }
}