
public class DataStructure implements DT {
	
	public final boolean X_AXIS = true, Y_AXIS = false;
	BiDirectionalLinkedList xList, yList;

	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public DataStructure()
	{
		xList = new BiDirectionalLinkedList();
		yList = new BiDirectionalLinkedList();
	}

	@Override
	public void addPoint(Point point) {
		//important note: the tempX and tempY MUST be different, because if you use the same objects in both lists you will ruin the different linking
		
		Link tempX = new Link(point);
		Link tempY = new Link(point);
		
		tempX.connectTwins(tempY);
		
		xList.add(tempX, X_AXIS);
		yList.add(tempY, Y_AXIS);
	}

	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		//determine the current axis
		BiDirectionalLinkedList currentAxis;
		if(axis)
			currentAxis = xList;
		else
			currentAxis = yList;
		
		int amount = this.getSize();
		
		//check that min < max
		if(min > max) {
			int temp = max;
			max = min;
			min = temp;
		}
		
		Link lowerBound = currentAxis.getHead();
		Link sentinel = new Link(new Point(min, min));
		
		while(lowerBound != null && lowerBound.compareTo(sentinel, axis) < 0) {
			amount -= 1;
			lowerBound = lowerBound.getNext();
		}
		
		//if there are no points in the given range
		if(lowerBound == null) {
			return new Point [0];
		}
		
		Link upperBound = currentAxis.getTail();
		sentinel = new Link(new Point(max, max));
		
		while(upperBound != null && upperBound.compareTo(sentinel, axis) > 0) {
			amount -= 1;
			upperBound = upperBound.getPrev();
		}
		
		if(upperBound == null) {
			return new Point [0];
		}
		
		//add all the point in the range to array
		Point [] array = new Point[amount];
		int index = 0;
		
		while(index < amount) {
			array[index] = lowerBound.getData();
			lowerBound = lowerBound.getNext();
			index += 1;
		}
		
		return array;
	}
	
	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		//determine the current axis
		BiDirectionalLinkedList currentAxis;
		if(!axis)
			currentAxis = xList;
		else
			currentAxis = yList;
		
		//check that min < max
		if(min > max) {
			int temp = max;
			max = min;
			min = temp;
		}
				
		BiDirectionalLinkedList points = new BiDirectionalLinkedList();
		
		Link current = currentAxis.getHead();
		
		while(current != null) {
			if(checkRange(min, max, axis, current))
				points.addTail(current);
			
			current = current.getNext();
		}
		
		//move the points in range into an array
		Point [] array = new Point[points.size()];
		current = points.getHead();
	
		for(int i=0; i<points.size(); i++) {
			array[i] = current.getData();
			current = current.getNext();
		}
		
		return array;
		
	}
	
	private boolean checkRange(int min, int max, boolean axis, Link current) {
		if(axis)
			return (current.getData().getX() >= min && current.getData().getX() <= max);
		else
			return (current.getData().getY() >= min && current.getData().getY() <= max);
	}

	@Override
	public double getDensity() {
		int n = this.getSize();
		int xDist = xList.getTail().getData().getX() - xList.getHead().getData().getX();
		int yDist = yList.getTail().getData().getY() - yList.getHead().getData().getY();
		
		return (double)n/(Math.abs(xDist * yDist));
	}

	@Override
	public void narrowRange(int min, int max, Boolean axis) {
		//determine the current axis
		BiDirectionalLinkedList currentAxis;
		if(axis) {
			currentAxis = xList;
			
			//go from the start to min
			Link current = currentAxis.getHead();
			while(current != null && current.getData().getX() < min) {
				this.removebyX(current);
				current = current.getNext();
			}
			
			//go from the end to max
			current = currentAxis.getTail();
			while(current != null && current.getData().getX() > max) {
				this.removebyX(current);
				current = current.getPrev();
			}
		}
		else {
			currentAxis = yList;
			
			//go from the start to min
			Link current = currentAxis.getHead();
			while(current != null && current.getData().getY() < min) {
				this.removebyY(current);
				current = current.getNext();
			}
			
			//go from the end to max
			current = currentAxis.getTail();
			while(current != null && current.getData().getY() > max) {
				this.removebyY(current);
				current = current.getPrev();
			}
		}
	}

	@Override
	public Boolean getLargestAxis() {
		int Xsize = Math.abs(xList.getTail().getData().getX() - xList.getHead().getData().getX());
		int Ysize = Math.abs(yList.getTail().getData().getY() - yList.getHead().getData().getY());
		
		return Xsize > Ysize;
	}

	@Override
	public Container getMedian(Boolean axis) {
		if(this.isEmpty())
			return null;
		
		int index = this.getSize()/2; //automathic floor because we convert from double to int
				
		if(axis) {
			Container cont = new Container(xList.get(index));
			return cont;
		}
		else {
			Container cont = new Container(yList.get(index));
			return cont;
		}
	}
	
	public Container getMedian(Link lower, Link upper, Boolean axis) {
		//finding the median of two arbitrary links in O(|B|), where B is the set of all the links between lower and upper bound
		//starting from the lower bound to make sure odd size will lend on the correct place
		
		//check if lower and upper should be switched
		if(axis) {
			if(lower.getData().getX() > upper.getData().getX()) {
				Link temp = lower;
				lower = upper;
				upper = temp;
			}
		} else {
			if(lower.getData().getY() > upper.getData().getY()) {
				Link temp = lower;
				lower = upper;
				upper = temp;
			}
		}
		
		while (true) {
			if(lower == upper)
				return new Container(lower);
			
			lower = lower.getNext();
			if(lower == upper)
				return new Container(lower);
			
			upper = upper.getPrev();
		}
	}

	@Override
	public Point[] nearestPairInStrip(Container container, double width, Boolean axis) {
		//get all the points in range Zp += width/2
		int Zp;
		if(axis)
			Zp = container.getData().getX();
		else
			Zp = container.getData().getY();
		
		//get all the points in the strip sorted by the other axis 
		Point[] pointsInRange = this.getPointsInRangeOppAxis((int)(Zp - (width/2)), (int)(Zp + (width/2)), axis);	
		
		//check edge cases 
		if(pointsInRange.length == 3)
			return findBruteForce(pointsInRange);
		if(pointsInRange.length == 2)
			return pointsInRange;
		if(pointsInRange.length < 2)
			return new Point[0];
		
		//if there are many points
		else {
			Point [] leftSide;
			Point [] rightSide;
			Point [] middle;
			
			//find the closest pair in the left side and right side
			if(axis) {
				leftSide = this.subArray(pointsInRange ,0, pointsInRange.length / 2);
				rightSide = this.subArray(pointsInRange, pointsInRange.length / 2, pointsInRange.length - 1);
			} else {
				leftSide = this.subArray(pointsInRange ,0, pointsInRange.length / 2);
				rightSide = this.subArray(pointsInRange, pointsInRange.length / 2, pointsInRange.length - 1);
			}
			
			Point [] closestPairLeft = findClosestPair(leftSide, axis);
			Point [] closestPairRight = findClosestPair(rightSide, axis);
			
			Point [] result = new Point[2];
			
			if(this.DistanceOfClosestPair(closestPairLeft) < this.DistanceOfClosestPair(closestPairRight)) {
				result[0] = closestPairLeft[0];
				result[1] = closestPairLeft[1];
			}			
			else {
				result[0] = closestPairRight[0];
				result[1] = closestPairRight[1];
			}
			
			
			//check the middle case (one point from each side)
			double delta = Math.min(this.getDistance(closestPairLeft[0], closestPairLeft[1]), this.getDistance(closestPairRight[0], closestPairRight[1]));
			middle = this.getPointsInRangeOppAxis((int)(Zp - delta), (int)(Zp + delta), axis);
			
			for(int index = 0; index < middle.length; index++) {
				for(int option = index + 1; option < Math.min(index + 7, middle.length); option++) {
					double dist = this.getDistance(middle[index], middle[option]);
					if(dist < delta) {
						result[0] = middle[index];
						result[1] = middle[option];
						delta = dist;
					}
				}
			}
			return result;
		}
	}
	
	private double DistanceOfClosestPair(Point [] arr) {
		if(arr.length < 2)
			return Integer.MAX_VALUE * 1.0;
		return this.getDistance(arr[0], arr[1]);
		
	}
	
	
	private Point[] findBruteForce(Point[] arr) {
		//will be called only for array in length 3
		double d01, d02, d12;
		Point [] result = new Point[2];
		
		d01 = this.getDistance(arr[0], arr[1]);
		d02 = this.getDistance(arr[0], arr[2]);
		d12 = this.getDistance(arr[1], arr[2]);
		
		if(d01 <= d02 && d01 <= d12) {
			result[0] = arr[0];
			result[1] = arr[1];
		} else if(d02 <= d01 && d02 <= d12) {
			result[0] = arr[0];
			result[1] = arr[2];
		} else {
			result[0] = arr[1];
			result[1] = arr[2];
		}
		
		return result;
	}
	
	
	private Point[] findClosestPair (Point[] pointsInRange, Boolean axis) {
		//check edge cases 
		if(pointsInRange.length == 3)
			return findBruteForce(pointsInRange);
		if(pointsInRange.length == 2)
			return pointsInRange;
		if(pointsInRange.length < 2)
			return new Point[0];
		
		Point [] leftSide;
		Point [] rightSide;
		
		//find the closest pair in the left side and right side
		if(axis) {
			leftSide = this.subArray(pointsInRange ,0, pointsInRange.length / 2);
			rightSide = this.subArray(pointsInRange, pointsInRange.length / 2, pointsInRange.length - 1);
		} else {
			leftSide = this.subArray(pointsInRange ,0, pointsInRange.length / 2);
			rightSide = this.subArray(pointsInRange, pointsInRange.length / 2, pointsInRange.length - 1);
		}
		
		Point [] closestPairLeft = findClosestPair(leftSide, axis);
		Point [] closestPairRight = findClosestPair(rightSide, axis);
		
		Point [] result = new Point[2];
		
		if(this.DistanceOfClosestPair(closestPairLeft) < this.DistanceOfClosestPair(closestPairRight)) {
			result[0] = closestPairLeft[0];
			result[1] = closestPairLeft[1];
		}			
		else {
			result[0] = closestPairRight[0];
			result[1] = closestPairRight[1];
		}
		
		
		//check the middle case (one point from each side)
		double delta = Math.min(this.getDistance(closestPairLeft[0], closestPairLeft[1]), this.getDistance(closestPairRight[0], closestPairRight[1]));
		
		int Zp;
		
		if(axis)
			Zp = pointsInRange[pointsInRange.length/2].getX();
		else
			Zp = pointsInRange[pointsInRange.length/2].getY();
			
		
		Point [] middle = this.getPointsInRangeOppAxis((int)(Zp - delta), (int)(Zp + delta), axis);
		
		for(int index = 0; index < middle.length; index++) {
			for(int option = index + 1; option < Math.min(index + 7, middle.length); option++) {
				double dist = this.getDistance(middle[index], middle[option]);
				if(dist < delta) {
					result[0] = middle[index];
					result[1] = middle[option];
					delta = dist;
				}
			}
		}
		return result;
	}

	
	private Point[] subArray (Point[] array, int start, int end) {
		//create a sub-array from array from index start to end inclusive (assumes valid input)
		Point[] sub = new Point[end - start + 1];
		int index = 0;
		
		while(start <= end) {
			sub[index] = array[start];
			index ++;
			start ++;
		}
		
		return sub;
	}

	@Override
	public Point[] nearestPair() {
		
		if( this.getLargestAxis()) {
			Container cont = this.getMedian(X_AXIS);
			return this.nearestPairInStrip(cont, this.xList.getTail().getData().getX() - this.xList.getHead().getData().getX(), X_AXIS);
		} else {
			Container cont = this.getMedian(Y_AXIS);
			return this.nearestPairInStrip(cont, this.yList.getTail().getData().getY() - this.yList.getHead().getData().getY(), Y_AXIS);
		}
	}

	//--------------------------------------------------------------------------
	
	public int getSize () {
		return xList.size();
	}
	
	
	public String toString () {
		String output = "";
		
		output += "X sorted list:\t"+ xList.toString() + "\n";
		output += "Y sorted list:\t"+yList.toString() + "\n";
		output += "Size: " + this.getSize();
		
		return output;
	}
	
	public boolean isEmpty() {
		return this.getSize() == 0;
	}
	
	
	public void removebyX(Link other) {
		xList.removeLink(other);
		yList.removeLink(other.getTwin());
	}
	
	public void removebyY(Link other) {
		yList.removeLink(other);
		xList.removeLink(other.getTwin());
	}
	
	public double getDistance(Point a, Point b) {
		double xDistance = Math.pow(a.getX() - b.getX(), 2);
		Double yDistance = Math.pow(a.getY() - b.getY(), 2);
				
		double distance = Math.sqrt(xDistance + yDistance);
		
		return distance;
	}
}

