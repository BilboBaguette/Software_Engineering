package loginFeat;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
 * @author Aurélien ANDRIEUX
 *
 */

public class XMLUser {
		
	public static void createUserXML() throws Exception
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		Document document = documentBuilder.newDocument();
		
		Element element = document.createElement("Logins");
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
		
        Element root = document.getDocumentElement();
        Element element = document.createElement("Login");
        root.appendChild(element);
        
        Element userName = document.createElement("UserName");
    	userName.appendChild(document.createTextNode(UserToAdd.getUsername()));
    	element.appendChild(userName);
    	
    	Element password = document.createElement("Password");
    	password.appendChild(document.createTextNode(UserToAdd.getPassword()));
    	element.appendChild(password);
    		
    	Element id = document.createElement("ID");
    	id.appendChild(document.createTextNode(Integer.toString(UserToAdd.getId())));
    	element.appendChild(id);


        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./User.xml"));
        transformer.transform(domSource, streamResult);
        
        
		
	}
	
	public static ArrayList<String> readXMLLogger(String choice) throws Exception
	{
		File xmlFile = new File("./User.xml");
		
		ArrayList<String> toReturn = new ArrayList<String>();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		NodeList list = document.getElementsByTagName("Login");
		
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
				case "ID":
					toReturn.add(element.getElementsByTagName("ID").item(0).getTextContent());
					break;
				}
			}
		}
		return toReturn;
	}
}