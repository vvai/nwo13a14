package logic.struct;

import java.util.ArrayList;
import java.util.List;

import logic.struct.exceptions.EmptyQueueException;

public class Queue {

	private List<Point> points;
	
	public Queue() {
		points = new ArrayList<Point>();
	}
	
	public void add(Point point) {
		points.add(point);
	}
	
	public Point pop() throws EmptyQueueException {
		if (points.size() > 0) {
			Point point = points.get(0);
			points.remove(0);
			return point;
		} else
			throw new EmptyQueueException();		
	}
	
	public Point get() throws EmptyQueueException {
		if (points.size() > 0)
			return points.get(0);
		else
			throw new EmptyQueueException();
	}
	
	public boolean isEmpty() {
		return points.size() == 0;
	}
	
	public int getCount() {
		return points.size();
	}
}
