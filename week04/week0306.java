// 6. 양의 정수 10개를 입력받아 일차원 배열에 저장하라. 그리고 일차원 배열에서 각 수를 분석하여
//    자리수의 합이 9인 것을 가려내어 출력하라. 예를 들어 27은 자리수의 합이 9(2+7)인 수이다.
/*
    양의 정수 10개 입력>>9 19 29 33 333 900 342 666 27 100
    자리수의 합이 9인 것은 ...9 333 900 342 27
*/

import java.util.Scanner;

public class week0306 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] intArray = new int[10];

        System.out.print("양의 정수 10개 입력>>");
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = scanner.nextInt();
        }

        System.out.print("자리수의 합이 9인 것은 ");
        for (int i = 0; i < intArray.length; i++) {
            int number = intArray[i];
            int temp = number;
            int sum = 0;

            while (temp > 0) {
                sum += temp % 10;
                temp /= 10;
            }

            if (sum == 9) {
                System.out.print(number + " ");
            }
        }

        scanner.close();
    }
}
