//this class implements the sorted bi-directional linked list ADT, It will also include a pointer to end head and tail of the list
//I've taken the core of the code from the "into to CS" course and changed it to meet my specific needs


public class BiDirectionalLinkedList  {
	
	// ---------------------- fields ---------------------- 
	private Link  head;
	private Link  tail;
	private int size;
	
	// ---------------------- constructors ----------------------
	public BiDirectionalLinkedList(){		
		head = null;
		tail = null;
		size = 0;
	}

	// ---------------------- methods ----------------------
	
	public Link getHead() {
		return this.head;
	}
	
	public Link getTail() {
		return this.tail;
	}

	//Returns the number of elements in this list
	public int size() {
		return this.size;
	}
	
	//Returns true if this list contains no elements.
	public boolean isEmpty() {
		return head == null;
	}
	
	
	//Returns the link at the specified position in this list.
	public Link  get(int index) {
	    if (!rangeCheck(index))
	    	throw new IllegalArgumentException("Index: " + index + ", Size: " + size());
	    Link  current = head;
	    while (index > 0) {
	        index = index - 1;
	        current = current.getNext();
	    }
	    return current;
	}
	
	public void removeLink (Link deleteMe) {		
		//check if this link have been deleted already
		if(deleteMe == null)
			return;
		
		//delte from the link from the current axis
		if(head == tail) {
			head = null;
			tail = null;
		} else if(deleteMe == head) {
			head = head.getNext();
			head.setPrev(null);
		} else if(deleteMe == tail) {
			tail = tail.getPrev();
			tail.setNext(null);
			
		} else {
			deleteMe.getPrev().setNext(deleteMe.getNext());
			deleteMe.getNext().setPrev(deleteMe.getPrev());
		}
		
		size --; //decreese the size of the list, only once for each pair of deleted items
	}
	
	//Returns a String representing this object
	public String toString() {
	    String output = "<";
	    Link  current = head;
	    while (current != null) {
	        output = output + current.toString();
	        current = current.getNext();
	        if(current != null)
	        	output = output + ", ";
	    }
	    output = output + ">";
	    return output;
	}
	
	//Remove the first Link which contains toRemove, if such one exists
	public boolean remove(Object toRemove) {
		if (toRemove == null)
			throw new IllegalArgumentException("input argument is null");
		Link  current = head;
		boolean removed = false;
		
		while (current != null & !removed) {
			if ((current.getData()).equals(toRemove)) {
				// if the first link should be removed
				if (current == head) {
					head = head.getNext();
				}
				else {
					current.getPrev().setNext(current.getNext());					
				}
				removed = true;
				size -= 1;
			}
			else {
				current = current.getNext();
			}
		}
		size -= 1;
		return removed;
	}
	
	//Returns true if this list contains the specified element
	public boolean contains(Object element){
		if (element == null)
			throw new IllegalArgumentException("input argument is null");
		
		boolean output = false;
		for(Link  curr = head; curr != null & !output; curr = curr.getNext())
			output = element.equals(curr.getData());
		return output;		
	}


	//Replaces the element at the specified position in this list with the specified element
	public Point set(int index, Point element){
		if(!rangeCheck(index))
			throw new IllegalArgumentException("Index: " + index + ", Size: " + size());
		if (element == null)
			throw new IllegalArgumentException("input argument is null");
		Link  current = head;
		while (index > 0) {
				index = index - 1;
				current = current.getNext();
		}
		Point prev = current.getData();
		current.setData(element);
		return prev;
	}
	
    public Point remove(int index) {
		if(!rangeCheck(index))
			throw new IllegalArgumentException("Index: " + index + ", Size: " + size());
		
        Link  current = head;
        
        while (index > 0) {
            index = index - 1;
            current = current.getNext();
        }
        
        Point ans = (Point) current.getData();
        
        if(size == 1) {
        	head = null;
        	tail = null;
        }
        else if (head == current) {
            head = head.getNext();
            head.setPrev(null);
        }
        else if(tail == current) {
        	current.getPrev().setNext(null);
        }
        else {
            current.getNext().setPrev(current.getPrev());
            current.getPrev().setNext(current.getNext());
        }
        
        size -= 1;
        return ans;
    }
	
	
	//Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
	public int indexOf(Point element){
		int output = -1;
		int index = 0;
		for(Link  curr = head; curr != null & output == -1; curr = curr.getNext())
			if( curr.getData().equals(element) )
				output = index;
			else
				index = index + 1;
		return output;
	}
	

	// returns true iff the given index is in range
	private boolean rangeCheck(int index) {
		if(index >= 0 && index < size())
			return true;
		return false;	        
	}

	//add a new element to the correct place in the list
	public void add(Link  element, boolean axis) {
		if (element == null)
			throw new IllegalArgumentException("input argument is null");
		
		if(size == 0) {
			head = element;
			tail = element;
			size = 1;
			
			return;
		}
		
		Link  current = head;
		
		boolean flag = true;
		 while (current != null && flag) {
			 if(element.compareTo(current, axis) > 0)
	            current = current.getNext();
			 else
				 flag = false;
	        }
		
		if(current == head) {
			element.setNext(head);
			head.setPrev(element);
			head = head.getPrev();
		} else {
			if(current != null) {
				element.setNext(current);
				element.setPrev(current.getPrev());
				current.getPrev().setNext(element);
				current.setPrev(element);
				
			} else {
				tail.setNext(element);
				element.setPrev(tail);
				tail = tail.getNext();
			}
		}
		
		size ++;
	}
	
	//add an item in the end of the list, to use only when the list is already sorted adding new max as tail
	public void addTail (Link  element) {
		Link temp = new Link(element.getData());
		
		if(this.tail == null) {
			head = temp;
			tail = temp;
		} else {
			this.tail.setNext(temp);
			temp.setPrev(tail);
			this.tail = temp;
		}
		size += 1;
	}
	
}

