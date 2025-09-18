package hello;

import java.util.Scanner;

public class problem2_06 {
	public static void main(String[] args) {
		int red, blue = 5, all;
		int old, redNum, blueNum, yellowNum;
		
		Scanner scn = new Scanner(System.in);
		
		System.out.print("나이를 입력하세요 : ");
		
		old = scn.nextInt();
		
		if(old>=1) {
		
		redNum = old / 10;
		red = old%10;
		
		blueNum = red /5;
		blue = red%5;
		
		yellowNum = blue/1;
		
		all = redNum + blueNum + yellowNum;
		
		System.out.println("빨간색 초" + redNum + "개, 파란색 초 " + blueNum +"개, 노란색 초 " + yellowNum + "개, 총 " + all + "개가 필요합니다.");
		}else {
			System.out.println("나이는 양수로만 입력하세요!!");
		}
		scn.close();
	}
}
