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
 * @author Jean-Louis CHENG
 * @author Nils CHOL
 * @author Aur�lien ANDRIEUX
 *
 */

public class XMLUser {
		
	public static void createUserXML() throws Exception
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		Document document = documentBuilder.newDocument();
		
		Element element = document.createElement("Accounts");
		document.appendChild(element);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		
		StreamResult streamResult = new StreamResult(new File("./User.xml"));
		
		transformer.transform(source, streamResult);
	}
	
	public static void addToXML(User UserToAdd) throws Exception
	{
		File xmlFile = new File("./User.xml");
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		Element element = document.createElement("User");
		
		Element root = document.getDocumentElement();
        root.appendChild(element);
		
		Attr attr = document.createAttribute("id");
        attr.setValue(Integer.toString(UserToAdd.getId()));
        element.setAttributeNode(attr);
        
        Element userName = document.createElement("UserName");
    	userName.appendChild(document.createTextNode(UserToAdd.getUsername()));
    	element.appendChild(userName);
    	
    	Element password = document.createElement("Password");
    	password.appendChild(document.createTextNode(UserToAdd.getPassword()));
    	element.appendChild(password);
    		
    	Element contacts = document.createElement("Contacts");
    	element.appendChild(contacts);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./User.xml"));
        transformer.transform(domSource, streamResult);
        
	}
	
	public static void addContactToUserXML(Integer idUser, Integer idToAdd) throws Exception
	{
		File xmlFile = new File("./User.xml");
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		Node contacts = document.getFirstChild();
		NodeList userList = contacts.getChildNodes();
		
		for (int i = 0; i < userList.getLength(); i++) 
		{
			Node element =  userList.item(i);
			
			String attr = element.getAttributes().getNamedItem("id").getNodeValue();
			if(Integer.toString(idUser).equals(attr))
			{
				NodeList subUserList = element.getChildNodes();
				for (int j = 0; j < subUserList.getLength(); j++) {
					
	                Node subElement = subUserList.item(j);
	                if ("Contacts".equals(subElement.getNodeName())) {
	                	Element contact = document.createElement("Contact");
	                	contact.appendChild(document.createTextNode(Integer.toString(idToAdd)));
	                	((Node) subUserList).appendChild(contact);
	                }
	            }
			}
		}
		TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./User.xml"));
        transformer.transform(domSource, streamResult);
			 
	}
	
	public static ArrayList<String> readXMLUser(String choice) throws Exception
	{
		File xmlFile = new File("./User.xml");
		
		ArrayList<String> toReturn = new ArrayList<String>();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		NodeList list = document.getElementsByTagName("User");
		
		for(int i = 0; i < list.getLength(); i++)
		{
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) node;
				switch(choice) {
				case "UserName": 
					toReturn.add(element.getElementsByTagName("UserName").item(0).getTextContent());
					break;
				case "Password":
					toReturn.add(element.getElementsByTagName("Password").item(0).getTextContent());
					break;
				case "Contacts":
					toReturn.add(element.getElementsByTagName("Contacts").item(0).getTextContent());
					break;
				}
			}
		}
		return toReturn;
	}
}