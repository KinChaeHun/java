// 3. Scanner를 이용하여 양의 정수를 입력받고 다음과 같이 *를 출력하는 프로그램을 작성하라.
//    양의 정수가 입력될 때까지 계속 입력을 받도록 하라. 다음 실행 사례는 5를 입력받은 경우이다.
/*
    양의 정수 입력>>-2
    양의 정수 입력>>5
    *****
    ****
    ***
    **
    *
*/

import java.util.Scanner;

public class week0303 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;

        do {
            System.out.print("양의 정수 입력>>");
            n = scanner.nextInt();
        } while (n <= 0);

        for (int i = n; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

        scanner.close();
    }
}
