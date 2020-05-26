package connec;
import java.io.Serializable;

public class Student implements Serializable		//must implement Serializable in order to be sent over a Socket
{
    private int id;
    private String name;
   
    public Student (int id, String name) {
		this.id=id;
		this.name=name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}		
}