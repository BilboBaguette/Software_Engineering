package Chatroom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class that allows to manipulate the xml file of login informations.
 * It allows to create the file, update it, and some more useful things.
 *
 * @version 1.0
 *
 * @author Jason Khaou
 */

public class XMLLog {
		
	/**
	 * Function that creates an XML file
	 * 
	 * @throws Exception
	 */
	public static void createLogXML() throws Exception
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		Document document = documentBuilder.newDocument();
		
		Element element = document.createElement("ChatRooms");
		document.appendChild(element);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		
		StreamResult streamResult = new StreamResult(new File("./Messages.xml"));
		
		transformer.transform(source, streamResult);
	}
	
	/**
	 * This function creates a chatroom in the XML doc
	 * 
	 * @param members List of members to be added 
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static void createChatRoom(ArrayList<String> members) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		File xmlFile = new File("./Messages.xml");
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		Element root = document.getDocumentElement();
        Element element = document.createElement("ChatRoom");
        root.appendChild(element);
        
        for(int i = 0; i< members.size(); i++)
        {
        	Attr attr = document.createAttribute("Member" + i); //Giving an ID to the newly added User as an attribute
            attr.setValue(members.get(i));
            element.setAttributeNode(attr);
        }
        
        TransformerFactory factory = TransformerFactory.newInstance(); //Rebuilding the XML file and replace the old one
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./Messages.xml"));
        transformer.transform(domSource, streamResult);
	}
	

	/**
	 * This function checks if the list of members are the same that in the classroom
	 * 
	 * @param members List of members
	 * @param attrList List of a node's attributes in a chatroom 
	 * @return
	 */
	private static boolean checkAttrMembers(ArrayList<String> members, ArrayList<String> attrList)
	{
		boolean check = true;
        for(int k = 0; k < members.size(); k++)
        {
            if(!members.get(k).equals(attrList.get(k))) {
                check = false;
                k = members.size();
            }
        }
        return check;
	}
	
	/**
	 * This function deletes a chatroom in the XML doc
	 * 
	 * @param members List of members 
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static void deleteChatRoom(ArrayList<String> members) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		File xmlFile = new File("./Messages.xml");
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); //Creation of elements to include in the XML file
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		Node contacts = document.getFirstChild();
		NodeList chatList = contacts.getChildNodes();
		
		for (int i = 0; i < chatList.getLength(); i++) 
		{		
			Element node = (Element) chatList.item(i);
            ArrayList<String> attrList = listAllAttributes(node);
            if(attrList.size() == members.size())
            {
                if(checkAttrMembers(members, attrList))
                {
                	((Node) chatList).removeChild(node);
                }
            }
		}
		TransformerFactory factory = TransformerFactory.newInstance();  //Rebuilding the XML file and replace the old one
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./Messages.xml"));
        transformer.transform(domSource, streamResult);	 
	}
	
	/**
	 * This function adds a message from a user to the XML doc
	 * 
	 * @param messageToAdd The user's message
	 * @param members List of members
	 * 
	 * @throws Exception
	 */
	public static void addToXML(Messages messageToAdd, ArrayList<String> members) throws Exception
    {
        File xmlFile = new File("./Messages.xml");
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        
        Node contacts = document.getFirstChild();
        NodeList chatList = contacts.getChildNodes();
        
        for(int i = 0; i < chatList.getLength(); i++)
        {
            Node element = chatList.item(i);
            Element node = (Element) chatList.item(i);
            ArrayList<String> attrList = listAllAttributes(node);
            if(attrList.size() == members.size())
            {
                if(checkAttrMembers(members, attrList))
                {
                    Element messages = document.createElement("Message"); //Sub Node of the User Node containing the username
                    element.appendChild(messages);
                    
                    Element userName = document.createElement("UserName");
                    userName.appendChild(document.createTextNode(messageToAdd.getUsername()));
                    messages.appendChild(userName);
                    
                    Element password = document.createElement("MessageContent");
                    password.appendChild(document.createTextNode(messageToAdd.getMessage()));
                    messages.appendChild(password);
                }
            }
        }
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./Messages.xml"));
        transformer.transform(domSource, streamResult);    
    }
	
	/**
	 * This function lists all the attributes from the XML doc
	 * 
	 * @param element
	 * @return
	 */
	private static ArrayList<String> listAllAttributes(Element element) {
        
        ArrayList<String> attrList = new ArrayList<String>();
         
        // get a map containing the attributes of this node 
        NamedNodeMap attributes = element.getAttributes();
 
        // get the number of nodes in this map
        int numAttrs = attributes.getLength();
 
        for (int i = 0; i < numAttrs; i++) {
            Attr attr = (Attr) attributes.item(i);
            attrList.add(attr.getNodeValue()); 
        }
        return attrList;
    }
	
	/**
	 * This function checks if a chatroom exists
	 * 
	 * @param members List of members
	 * 
	 * @return Result of the check
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static boolean chatRoomExist(ArrayList<String> members) throws ParserConfigurationException, SAXException, IOException
	{
		File xmlFile = new File("./Messages.xml");
		boolean check = false;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        
        Node chat = document.getFirstChild();
        NodeList chatList = chat.getChildNodes();
        for (int i = 0; i < chatList.getLength(); i++) 
        {
        	
	        Element node = (Element) chatList.item(i);
	        ArrayList<String> attrList = listAllAttributes(node);
	        if(attrList.size() == members.size())
	        {
	            if(checkAttrMembers(members, attrList))
	            {
	            	check = true;
	            }
	        }
        }
        return check;
	}
	
	/**
	 * This function reads the contents of the XML doc
	 * 
	 * @param choice variable used in the switch
	 * @param members List of members
	 * 
	 * @return An arraylist containing the results
	 * @throws Exception
	 */
	public static ArrayList<String> readXMLLog(String choice, ArrayList<String> members) throws Exception
    {
        File xmlFile = new File("./Messages.xml");
        
        ArrayList<String> toReturn = new ArrayList<String>();
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        
        Node chat = document.getFirstChild();
        NodeList chatList = chat.getChildNodes();
        for (int i = 0; i < chatList.getLength(); i++) 
        {
            Node element = chatList.item(i);
            NodeList subUserList = element.getChildNodes();
            Element node = (Element) chatList.item(i);
            ArrayList<String> attrList = listAllAttributes(node);
            if(attrList.size() == members.size())
            {
                if(checkAttrMembers(members, attrList))
                {
                    Element content = (Element) node;
                    for (int n = 0; n < subUserList.getLength(); n++) 
                    {   
                        switch(choice) {
                        case "UserName":
                            toReturn.add(content.getElementsByTagName("UserName").item(n).getTextContent());
                            break;
                        case "MessageContent":  
                            toReturn.add(content.getElementsByTagName("MessageContent").item(n).getTextContent());
                            break;
                        }
                    }
                }     
            }    
        }
        return toReturn;
    }
}