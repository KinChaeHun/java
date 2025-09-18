package hello;

import java.util.Scanner;

public class problem2_3 {
	public static void main(String[] args) {
		int manu1 = 2000, manu2 = 1000, manu3 =3000;
		int total;
		System.out.println("****자바분식입니다. 주문하면 금액을 알려드립니다.**** ");
		Scanner scn = new Scanner(System.in);
		
		System.out.print("떡볶이 몇인분 >> ");
		total = scn.nextInt();
		manu1 =	manu1 * total;
		
		System.out.print("김말이 몇인분 >> ");
		total = scn.nextInt();
		manu2 =	manu2 * total;
		
		System.out.print("쫄면 몇인분 >> ");
		total = scn.nextInt();
		manu3 =	manu3 * total;
		
		total = manu1 + manu2 + manu3;
		
		System.out.println("전체 금액은 "+ total + "원입니다.");
		
		scn.close();
	}
}
