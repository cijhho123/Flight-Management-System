
//Don't change the class name
public class Container{
	private Point data;//Don't delete or change this field;
	private Link link;
	
	//constructor
	public Container(Point p) {
		this.data = p;
		this.link = null;
	}
	
	public Container(Link lnk) {
		this.link = lnk;
		this.data = lnk.getData();
		
	}
	
	
	//Don't delete or change this function
	public Point getData()
	{
		return data;
	}
	
	public String toString() {
		if(this.link != null)
			return this.link.toString();
		else
			return this.data.toString();
	}

}
