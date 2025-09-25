// 11. 다음 퀴즈의 답을 검사하는 프로그램을 작성하라. 랜덤하게 구구단을 묻고 사용자의 답변이
//     정답인지 판단한다. 3번 틀리면 종료한다.
/*
    **** 구구단 퀴즈입니다. ****
    8x5=40
    정답입니다. 잘했습니다.
    3x1=4
    1번 틀렸습니다. 분발하세요.
    7x6=5
    2번 틀렸습니다.
    9x4=36
    정답입니다. 잘했습니다.
    8x4=6
    3번 틀렸습니다. 퀴즈 종료합니다.
*/

import java.util.Scanner;

public class week0311 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int wrongCount = 0;

        System.out.println("**** 구구단 퀴즈입니다. ****");

        while (true) {
            int num1 = (int)(Math.random() * 9) + 1;
            int num2 = (int)(Math.random() * 9) + 1;
            int correctAnswer = num1 * num2;

            System.out.print(num1 + "x" + num2 + "=");
            int userAnswer = scanner.nextInt();

            if (userAnswer == correctAnswer) {
                System.out.println("정답입니다. 잘했습니다.");
            } else {
                wrongCount++;
                if (wrongCount == 1) {
                    System.out.println("1번 틀렸습니다. 분발하세요.");
                } else if (wrongCount == 2) {
                    System.out.println("2번 틀렸습니다.");
                } else if (wrongCount == 3) {
                    System.out.println("3번 틀렸습니다. 퀴즈 종료합니다.");
                    break;
                }
            }
        }
        
        scanner.close();
    }
}
