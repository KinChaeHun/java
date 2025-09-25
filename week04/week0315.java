// 15. 다음 코드는 2개의 정수를 입력받아 곱을 구하는 프로그램이다. 이 프로그램은 실행 중 정수가
//     아닌 것이 입력되면 라인 3이나 4에서 InputMismatchException 예외가 발생하고 종료되어 버린다.
//     이 프로그램을 수정하여 실행 사례와 같이 정수가 입력될 때까지 입력받고 곱을 출력하도록 하라.
/*
    곱하고자 하는 정수 2개 입력>>5 13.7
    정수를 입력하세요!
    곱하고자 하는 정수 2개 입력>>hello 365
    정수를 입력하세요!
    곱하고자 하는 정수 2개 입력>>4 20
    4x20=80
*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class week0315 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("곱하고자 하는 정수 2개 입력>>");
            try {
                int n = scanner.nextInt();
                int m = scanner.nextInt();
                System.out.println(n + "x" + m + "=" + n * m);
                break;

            } catch (InputMismatchException e) {
                System.out.println("정수를 입력하세요!");
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}
