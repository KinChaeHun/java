// 8. 몇 개의 정수를 입력할 것인지 정수 개수를 입력받고, 그 만큼의 임의의 정수를 입력받아 배열에 저장한다.
//    단, 정수가 1~100 범위의 정수가 아니면 다시 입력받도록 하고, 배열에 저장된 정수들과 평균을 출력하라.
/*
    정수 몇 개>>7
    67 69 31 48 27 20 92 55 50 47 93 75
    67 69 31 48 27 20 92
    평균은 54.857142857142854
*/

import java.util.Scanner;

public class week0308 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("정수 몇 개>>");
        int count = scanner.nextInt();
        int[] intArray = new int[count];
        double sum = 0.0;

        for (int i = 0; i < intArray.length; i++) {
            int num = scanner.nextInt();

            if (num >= 1 && num <= 100) {
                intArray[i] = num;
                sum += num;
            } else {
                i--;
            }
        }

        for (int i = 0; i < intArray.length; i++) {
            System.out.print(intArray[i] + " ");
        }
        System.out.println();

        System.out.println("평균은 " + (sum / intArray.length));

        scanner.close();
    }
}
