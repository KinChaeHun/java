package hello;

import java.util.Scanner;

public class problem2_9 {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);

        System.out.print("점 (x, y)의 좌표 입력>>");
        int x = scn.nextInt();
        int y = scn.nextInt();

        int x1 = 10, y1 = 10;
        int x2 = 200, y2 = 300;

        if (x > x1 && x < x2 && y > y1 && y < y2) {
            System.out.println("(" + x +"," + y +")는 사각형 안에 있습니다.");
        }
        else if ((x == x1 && y >= y1 && y <= y2) ||
                 (x == x2 && y >= y1 && y <= y2) ||
                 (y == y1 && x >= x1 && x <= x2) ||
                 (y == y2 && x >= x1 && x <= x2)) {
            System.out.println("(" + x +"," + y +")는 사각형 선 상에 있습니다.");
        }
        else {
            System.out.println("(" + x +"," + y +")는 사각형 밖에 있습니다.");
        }

        scn.close();
	}
}
