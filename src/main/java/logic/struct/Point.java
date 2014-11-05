package logic.struct;

public class Point {
	
	private int x = 0;
	private int y = 0;
	
	public Point(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equals(Point point) {
		return point.getX() == x && point.getY() == y;
	}
	
	public Point clone() {
		return new Point(x, y);
	}
	
}
