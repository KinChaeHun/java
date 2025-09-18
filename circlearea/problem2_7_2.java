package hello;

import java.util.Scanner;

public class problem2_7_2 {
	public static void main(String[] args) {
		int season;
		Scanner scn = new Scanner(System.in);
		
		System.out.println("월을 입력하세요(1~12) >> ");
		season = scn.nextInt();
		
		switch(season){
			case 3 :
			case 4 :
			case 5 :
				System.out.println("따뜻한 봄 ");
				break;
			case 6 :
			case 7 :
			case 8 :
				System.out.println("바다가 즐거운 여름 ");
				break;
			case 9 :
			case 10 :
			case 11 :
				System.out.println("낙엽이 지는 아름다운 가을");
				break;
			case 12 :
			case 1 :
			case 2 :
				System.out.println("눈이 내리는 햐얀 겨울");
				break;
			default:
				System.out.println("1 ~ 12만 입력하세요.");
		}
		scn.close();
	}
}
