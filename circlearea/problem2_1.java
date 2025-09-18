package hello;

import java.util.Scanner;

public class problem2_1 {
	public static void main(String[] args) {
		int dollar;
		int all;
		final int money = 1200;
		
		Scanner scn = new Scanner(System.in);
		
		System.out.print("$1 =1200원입니다. 달러를 입력하세요 : ");
		dollar=  scn.nextInt();
		all = money * dollar;
		
		System.out.println("$" + dollar + "는 " + all + "입니다.");
		
		scn.close();
	}
}
