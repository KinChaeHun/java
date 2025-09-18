package hello;

import java.util.Scanner;

public class problem2_8_1 {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		
		System.out.println("연산 입력 >> ");
		double x = scn.nextDouble();
		String op = scn.next();
		double y = scn.nextDouble();
		double total;
		if(op.equals("곱하기") || op.equals("*")){
			total = x * y;
			System.out.println( x + op + y + "의 계산 결과는 " + total);
		}else if(op.equals("나누기" ) || op.equals("/")){
			total = x / y;
			System.out.println( x + op + y + "의 계산 결과는 " + total);
		}else if(op.equals("더하기") || op.equals("+")){
			total = x + y;
			System.out.println( x + op + y + "의 계산 결과는 " + total);
		}else if(op.equals("빼기")|| op.equals("-")){
			total = x - y;
			System.out.println( x +" "+ op +" "+ y + "의 계산 결과는 " + total);
		}else {
			System.out.println("연산자를 잘못 입력했습니다. 더하기, 빼기, 곱하기, 나누기 중 입력하세요.");
		}
		
		scn.close();
		
	}
}
