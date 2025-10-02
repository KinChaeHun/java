package basic;
abstract class Calculator{
	public abstract int add(int a, int b);
	public abstract int subtract(int a, int b);
	public abstract double average(int[] a);
}

public class GoodCalc extends Calculator{

	@Override
	public int add(int a, int b) {
		return a + b;
	}

	@Override
	public int subtract(int a, int b) {
		// TODO Auto-generated method stub
		return a-b;
	}

	@Override
	public double average(int[] a) {
		double sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}
		return sum/a.length;
	}
	public static void main(String[] args) {
		GoodCalc cal1 = new GoodCalc();
		System.out.println(cal1.add(1, 6));
		System.out.println(cal1.subtract(6, 1));
		System.out.println(cal1.average(new int[] {1, 2, 3, 4}));  //int[] a = {1, 2, 3, 4}
	}
}
