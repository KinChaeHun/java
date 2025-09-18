package hello;

import java.util.Scanner;

public class Rectangle {
//	사각형의 height 와 width를 입력받아 면적을  출력하는 프로그램 작성
	public static void main(String[] args) {
		System.out.println("사각형의 height 와 width를 계산 프로그램");
		Scanner scanner = new Scanner(System.in);
		System.out.println("height 입력:");
		int height = scanner.nextInt();
		System.out.println("width 입력:");
		int width = scanner.nextInt();
		
		int Rectangle1 = height * width;
		System.out.println("높이 " + height +"너비 " + width +"인 면적은 " + Rectangle1 + "입니다.");
		scanner.close();
	}
}
