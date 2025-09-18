package hello;

import java.util.Scanner;

public class problem2_2 {
	public static void main(String[] args) {
		
		int birthday,year,month,day;
		System.out.println("생일입력:");
		Scanner scn = new Scanner(System.in);
		birthday = scn.nextInt();
		
		year = birthday/10000;
		day = birthday%10000;
		
		month = day / 100;
		day = day % 100;
		
		System.out.println( year + "년 " + month + "월"+ day + "일 입니다.");
		
		scn.close();
	}
	
}
