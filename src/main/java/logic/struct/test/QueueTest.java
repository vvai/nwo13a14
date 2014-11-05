package logic.struct.test;

import org.junit.Assert;
import org.junit.Test;

import logic.struct.*;
import logic.struct.exceptions.EmptyQueueException;

public class QueueTest {

	@Test
	public void testConstructorAndIsEmpty() {
		Queue queue = new Queue();
		Assert.assertEquals(true, queue.isEmpty());
	}
	
	@Test
	public void testIsEmpty() throws EmptyQueueException {
		Queue queue = new Queue();
		queue.add(new Point(1, 1));
		queue.add(new Point(1, 1));
		queue.add(new Point(1, 1));
		queue.pop();
		queue.pop();
		queue.pop();
		Assert.assertEquals(true, queue.isEmpty());
		queue.add(new Point(0, 0));
		Assert.assertEquals(false, queue.isEmpty());
		queue.pop();
		Assert.assertEquals(true, queue.isEmpty());
	}

	@Test
	public void testGetCount() throws EmptyQueueException {
		Queue queue = new Queue();
		Point point1 = new Point(1, 2);
		Point point2 = new Point(2, 1);
		Point point3 = new Point(0, -1);
		queue.add(point1);
		queue.add(point2);
		queue.add(point3);
		Assert.assertEquals(3, queue.getCount());
		queue.pop();
		queue.pop();
		queue.pop();
		Assert.assertEquals(0, queue.getCount());
		queue.add(new Point(1, 1));
		Assert.assertEquals(1, queue.getCount());
	}
	
	@Test
	public void testGetAndPop() throws EmptyQueueException {
		Queue queue = new Queue();
		Point point1 = new Point(1, 2);
		Point point2 = new Point(2, 1);
		Point point3 = new Point(0, -1);
		queue.add(point1);
		queue.add(point2);
		queue.add(point3);
		Assert.assertEquals(point1, queue.pop());
		Assert.assertEquals(point2, queue.pop());
		Assert.assertEquals(point3, queue.pop());
		Assert.assertEquals(true, checkEmptyQueueOnGet(queue));
		queue.add(point2);
		Assert.assertEquals(point2, queue.get());
	}
	
	private boolean checkEmptyQueueOnGet(Queue queue) {
		try {
			// Must have an exception with an empty queue
			Point point = queue.get();
			// Just to delete unuse warning
			point.getX();
		} catch (Exception e) {
			return true;
		}
		return false;
	}
}
