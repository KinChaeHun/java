package hello;

import java.util.Scanner;

public class LeepYear {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		
		int year;
		
		System.out.println("년도 입력");
		
		year = scn.nextInt();
		
		if(year % 4 == 0 && year % 100 != 0 || year % 400 ==0) {
			System.out.println(year +"은 윤년(leep year)입니다.");
		}else {
			System.out.println(year +"은 평년(common year)입니다.");
		}
		scn.close();
	}
}
