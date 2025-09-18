package hello;

import java.util.Scanner;

public class problem2_5 {
	public static void main(String[] args) {
		
		Scanner scn = new Scanner(System.in);
		
		int deduction1,deduction2 ,perception, absence, std1 = 100, std2= 100;
		String name1, name2;
		
		System.out.print("학생1 >> ");
		name1 = scn.next();
		perception = scn.nextInt();
		absence = scn.nextInt();
		
		std1 = std1 - (perception* 3 ) -(absence * 8);
		deduction1 = 100 -std1;
		
		
		System.out.print("학생2 >> ");
		name2 = scn.next();
		perception = scn.nextInt();
		absence = scn.nextInt();
		
		std2 = std2 -( perception * 3) -(absence * 8 );
		deduction2 = 100 -std2;
		
		System.out.println(name1 +"의 감점은" + deduction1 + ", " + name2 +"의 감점은" +  deduction2);
		
		if(std1 >std2) {
			System.out.println( name1 +"의 출석점수가 더 높음. " + name1 + "출석 점수는 "+ std1);
		}else {
			System.out.print( name2 +"의 출석점수가 더 높음. " + name2 + "의 출석 점수는 "+ std2);
		}
		
		 scn.close();
		
		
		
		
	}
}
