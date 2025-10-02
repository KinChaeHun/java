package basic;
public class PointTest {
	public static void main(String[] args) {
		Point p1 = new Point(5, 6);
		p1.showPoint();
		ColorPoint cp1 = new ColorPoint("red");
		cp1.showColorPoint();
	}
}
class Point{
	int x, y;
	public Point() {}
	public Point(int x, int y) {
		this.x = x; this.y = y;
	}
	public void set(int x, int y) {
		this.x = x; this.y = y;
	}
	public void showPoint() {
		System.out.println("("+x+", "+y+")");
	}
}
class ColorPoint extends Point{
	String color;
	public ColorPoint() {}
	public ColorPoint(String color) {
		this.color = color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void showColorPoint() {
		System.out.print(color);
		super.showPoint();
	}
}