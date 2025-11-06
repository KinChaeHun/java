import java.util.Scanner;
import java.util.Vector;

abstract class Shape {
	private Shape next;
	public Shape() { next = null;}
	public void setNext(Shape obj) {next = obj;} // 링크 연결
	public Shape getNext() {return next;}
	public abstract void draw(); // 추상 메소드. Shape을 상속받는 클래스는 draw()를 반드시 구현해야 함
}

class Circle extends Shape {
	@Override
	public void draw() {
		 System.out.println("Circle");
	}
}

class Rect extends Shape {
	@Override
	public void draw() {
		 System.out.println("Rect");
	}
}

class Line extends Shape {
	@Override
	public void draw() {
		 System.out.println("Line");
	}
}

public class GraphicEditor {
	private String editorName;
	private Scanner scanner = new Scanner(System.in);
	private Vector<Shape> v = new Vector<Shape>(); // Shape을 상속받은 어떤 객체들 저장하는 벡터
	
	public GraphicEditor(String editorName) {
		this.editorName = editorName;
	}
	
	public void run() {
		System.out.println("그래픽 에디터 " + editorName + "를 실행합니다.");
		int menu = 0;
		while (menu != 4) { 
			int shape, index;
			System.out.print("삽입(1), 삭제(2), 모두 보기(3), 종료(4)>>");
			menu = scanner.nextInt();
			switch (menu) {
				case 1:	// 삽입
					System.out.print("Line(1), Rect(2), Circle(3)>>");
					shape = scanner.nextInt();
					if (shape < 1 || shape > 3) {
						System.out.println("잘못 선택하셨습니다.");
						break;
					}
					insert(shape); // 도형 삽입
					break;
				case 2:	// 삭제
					System.out.print("삭제할 도형의 위치>>");
					index = scanner.nextInt();
					if (!delete(index)) { // 도형 삭제. delete()는 삭제할 수 없으면 false 리턴  
						System.out.println("삭제할 수 없습니다.");
					}
					break;
				case 3:	// 모두 보기
					view();
					break;
				case 4:	// 끝내기
					break;
				default:
					System.out.println("잘못 입력하셨습니다.");
			}
		}
		System.out.println(editorName + "를 종료합니다.");	
	}

	// 벡터에 저장된 모든 도형 그리기
	private void view() {
		for(int i=0; i<v.size(); i++) 
			v.get(i).draw(); // 각 도형의 오버라이딩된 draw() 호출
	}
	
	// 벡터 내 index 위치의 도형 삭제
	private boolean delete(int index) {
		if (v.size() == 0 || index >= v.size()) // 벡터가 비거나, 인덱스에 도형이 없는 경우
			return false;
		v.remove(index);
		return true;
	}

	private void insert(int choice) {
		Shape shape=null;
		switch (choice) {
			case 1: // Line
				shape = new Line();
				 break;
			case 2: // Rect
				shape = new Rect();
				break;
			case 3: // Circle
				shape = new Circle();
		}
		v.add(shape);
	}
	
	public static void main(String [] args) {
		GraphicEditor ge = new GraphicEditor("Beauty Graphic Editor");
		ge.run();
	}
}
