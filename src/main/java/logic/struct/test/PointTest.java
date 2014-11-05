package logic.struct.test;

import junit.framework.Assert;

import org.junit.Test;

import logic.struct.Point;

public class PointTest {

	@Test
	public void testConstructor() {
		Point point = new Point(1, 2);
		Assert.assertEquals(2, point.getY());
		Assert.assertEquals(1, point.getX());
	}
	
	@Test
	public void testEquals() {
		Point point1 = new Point(1, 2);
		Point point2 = new Point(2, 2);
		Point point3 = new Point(1, 2);
		Assert.assertEquals(true, point1.equals(point3));
		Assert.assertEquals(false, point1.equals(point2));
		Assert.assertEquals(false, point3.equals(point2));
	}
	
}
