//this class implements the bi-directional Link
//I've taken the core of the code from the "into to CS" course and changed it to meet my specific needs

public class Link  {
	// ---------------------- fields ---------------------- 
	private Point data;
	private Link  next;
	private Link  prev;
	private Link twin;

	// ---------------------- constructors ----------------------
	public Link(Point data, Link  next, Link  prev, Link twin) {
		this.data = data;
		this.next = next;
		this.prev = prev;
		this.twin = twin;
	}
	
	public Link(Point data, Link  next, Link  prev) {
		this.data = data;
		this.next = next;
		this.prev = prev;
		this.twin = null;
	}
	
	public Link(Point data) {
		this(data, null, null, null);
	}

	// ---------------------- Methods ----------------------
	public void connectTwins (Link other) {
		this.twin = other;
		other.twin = this;
	}
	
	public Link getTwin() {
		return this.twin;
	}	
	
	public void setTwin(Link other) {
		this.twin = other;
	}
	
	public Link  getNext() { 
		return next;
	}
	
	public void setNext(Link  next){
		this.next = next;
	}
	
	public Link  getPrev() { 
		return prev;
	}
	
	public void setPrev(Link  prev){
		this.prev = prev;
	}
	
	public Point getData() {
	    return data;
	}
	
	public Point setData(Point data) {
		Point tmp = this.data;
	    this.data = data;
		return tmp;
	}
	
	public String toString() {
	    return data.toString();
	}

	public int compareTo(Link  other, boolean axis) {
		if(axis) {
			return this.getData().getX() - other.getData().getX();
		} else {
			return this.getData().getY() - other.getData().getY();
		}
	}



}