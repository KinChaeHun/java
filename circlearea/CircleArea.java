package hello;

public class CircleArea {
	public static void main(String[] args) {
		final double PI = 3.14;
		double radius =10.2;
		double circleArea = radius * radius * PI;
		
		System.out.print("Radius =" + radius+",");
		System.out.println("Area =" + circleArea);
	}
}
