package hello;

import java.util.Scanner;

public class problem2_10 {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		
		System.out.print("(x1, y1), (x2, y2)의 좌표 입력>>");
        int x1 = scn.nextInt();
        int y1 = scn.nextInt();
        int x2 = scn.nextInt();
        int y2 = scn.nextInt();
        
        int left = x1;
        int right = x2;
        if (x1 > x2) {
            left = x2;
            right = x1;
        }

        int bottom = y1;
        int top = y2;
        if (y1 > y2) {
            bottom = y2;
            top = y1;
        }

        int baseLeft = 10, baseBottom = 10;
        int baseRight = 200, baseTop = 300;

        if (left >= baseLeft && right <= baseRight &&
            bottom >= baseBottom && top <= baseTop) {
            System.out.println("(" + x1 + y1 + ") (" + x2 + "," + y2 + ")" +"사각형은 (" + baseLeft+ ","+ baseBottom +") (" + baseRight +","+  baseTop +") 사각형에 포함된다.");
        } else {
        	System.out.println("(" + x1 + y1 + ") (" + x2 + "," + y2 + ")" +"사각형은 (" + baseLeft+ ","+ baseBottom +") (" + baseRight +","+  baseTop +") 사각형에 포함되지 않는다.");
        }
        scn.close();
	}
}
