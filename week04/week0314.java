// 14. 갬블링 게임 프로그램을 작성해보자. 사용자가 엔터키를 입력하면 0, 1, 2 중에서 랜덤하게
//     3개의 수를 생성하고 출력한다. 3개가 모두 같은 값일 때 '성공, 대박났어요!'를 출력한다.
//     실행 예시를 참고하여 프로그램을 작성하라.
/*
    ***** 갬블링 게임을 시작합니다. *****
    엔터키 입력>>
    0 2 1
    엔터키 입력>>
    1 0 2
    엔터키 입력>>
    2 2 2
    성공! 대박났어요!
    계속하시겠습니까?(yes/no)>>yes
    엔터키 입력>>
    2 1 2
    엔터키 입력>>
    2 2 2
    성공! 대박났어요!
    계속하시겠습니까?(yes/no)>>no
    게임을 종료합니다.
*/

import java.util.Scanner;

public class week0314 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("***** 갬블링 게임을 시작합니다. *****");

        while (true) {
            System.out.print("엔터키 입력>>");
            scanner.nextLine();

            int num1 = (int)(Math.random() * 3);
            int num2 = (int)(Math.random() * 3);
            int num3 = (int)(Math.random() * 3);

            System.out.println(num1 + " " + num2 + " " + num3);

            if (num1 == num2 && num2 == num3) {
                System.out.println("성공! 대박났어요!");
                System.out.print("계속하시겠습니까?(yes/no)>>");
                String response = scanner.next();
                
                if (response.equals("no")) {
                    break;
                }
                scanner.nextLine();
            }
        }

        System.out.println("게임을 종료합니다.");
        scanner.close();
    }
}
