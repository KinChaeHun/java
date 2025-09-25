// 16. 양수들을 한 줄에 입력받고 평균을 구하는 프로그램을 작성하라. 입력에서 양의 정수가
//     아닌 것들은 모두 빼고 평균을 구하라. 다음은 실행 사례이다.
/*
    양의 정수를 입력하세요. -1은 입력 끝>>10 hello 20 3.14 30 -2 40 -1
    hello 제외
    3.14 제외
    -2 제외
    평균은 25
*/

import java.util.Scanner;

public class week0316 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int count = 0;

        System.out.print("양의 정수를 입력하세요. -1은 입력 끝>>");

        while (scanner.hasNext()) {
            String token = scanner.next();
            try {
                int n = Integer.parseInt(token);

                if (n == -1) {
                    break;
                }

                if (n > 0) {
                    sum += n;
                    count++;
                } else {
                    System.out.println(n + " 제외");
                }
            } catch (NumberFormatException e) {
                System.out.println(token + " 제외");
            }
        }

        if (count > 0) {
            System.out.println("평균은 " + (sum / count));
        } else {
            System.out.println("평균은 0");
        }
        
        scanner.close();
    }
}
