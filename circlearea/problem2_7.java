package hello;

import java.util.Scanner;

public class problem2_7 {
	public static void main(String[] args) {
		
		int season;
		Scanner scn = new Scanner(System.in);
		
		System.out.println("월을 입력하세요(1~12) >> ");
		season = scn.nextInt();
		
		if(season >= 3 && season <= 5) {
			System.out.println("따뜻한 봄 ");
		}else if(season >= 6 && season <= 8) {
			System.out.println("바다가 즐거운 여름");
		}else if(season ==1 || season == 2 || season ==12) {
			System.out.println("눈이 내리는 햐얀 겨울");
		}else if(season >= 9 && season <= 11) {
			System.out.println("낙엽이 지는 아름다운 가을");
		}else{
			System.out.println("1 ~ 12만 입력하세요.");
		}
		scn.close();
	}
}
