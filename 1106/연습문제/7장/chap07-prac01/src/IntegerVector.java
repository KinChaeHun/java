import java.util.Scanner;
import java.util.Vector;

public class IntegerVector {
	private Vector<Integer> v = new Vector<Integer>();

	public IntegerVector() { }
	private void read() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("정수 입력(-1이면 입력 끝)>>");
		while(true) {
			int n = scanner.nextInt();
			if(n == -1)
				break;
			v.add(n);
		}
		scanner.close();
	}
	
	private int findLeastNumber() {
		int least = v.get(0); // 0번째 항목을 현재 제일 작은 수로 설정
		for(int i=0; i<v.size(); i++) {
			if(least > v.get(i))
				least = v.get(i);
		}
		return least;
	}
	
	public void run() {
		read(); // -1 앞까지 정수들을 벡터에 읽어들이기
		int least = findLeastNumber();
		System.out.println("제일 작은 수는 " + least);
	}
	
	public static void main(String[] args) {
		IntegerVector app = new IntegerVector();
		app.run();
	}

}
