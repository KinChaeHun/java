// 10. (실행 사례 기반) 4x4 배열을 0~255 범위의 랜덤 정수로 초기화 후 출력하고,
//     사용자에게 정수를 입력받아 배열의 값이 입력값보다 크면 255, 작거나 같으면 0으로 변경하여 다시 출력하라.
/*
    4x4 배열에 랜덤한 값을 저장한 후 출력합니다.
    239 0   184 166
    192 165 129 194
    59  110 255 168
    97  7   157 65

    임의의 값>>100

    0   0   255 255
    255 255 255 255
    0   255 255 255
    0   0   255 0
*/


import java.util.Scanner;

public class week0310 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] intArray = new int[4][4];

        for (int i = 0; i < intArray.length; i++) {
            for (int j = 0; j < intArray[i].length; j++) {
                intArray[i][j] = (int) (Math.random() * 256);
            }
        }

        System.out.println("4x4 배열에 랜덤한 값을 저장한 후 출력합니다.");
        for (int i = 0; i < intArray.length; i++) {
            for (int j = 0; j < intArray[i].length; j++) {
                System.out.print(intArray[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();

        System.out.print("임의의 값>>");
        int threshold = scanner.nextInt();
        System.out.println();

        for (int i = 0; i < intArray.length; i++) {
            for (int j = 0; j < intArray[i].length; j++) {
                if (intArray[i][j] > threshold) {
                    intArray[i][j] = 255;
                } else {
                    intArray[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < intArray.length; i++) {
            for (int j = 0; j < intArray[i].length; j++) {
                System.out.print(intArray[i][j] + "\t");
            }
            System.out.println();
        }

        scanner.close();
    }
}
