// 5. 양의 정수 10개를 입력받아 일차원 배열에 저장하고, 그리고 배열에 저장된 정수 중 3의 배수만 출력하는 프로그램을 작성하라.
/*
    양의 정수 10개 입력>>6 8 22 78 99 100 54 44 250 2346
    3의 배수는...6 78 99 54 2346
*/

import java.util.Scanner;

public class week0305 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] intArray = new int[10];

        System.out.print("양의 정수 10개 입력>>");
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = scanner.nextInt();
        }

        System.out.print("3의 배수는 ");
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i] % 3 == 0) {
                System.out.print(intArray[i] + " ");
            }
        }
        
        scanner.close();
    }
}
