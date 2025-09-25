// 17. 다음과 같이 커피와 가격을 각각 배열로 만들고, 이를 활용하여 실행 사례와 같이 주문을 받아
//     가격을 계산하는 프로그램을 작성하라.
/*
    핫아메리카노, 아이스아메리카노, 카페라떼, 라떼 있습니다.
    주문>>핫아메리카노 2
    가격은 6000원입니다.
    주문>>아이스아메리카노 2
    가격은 7000원입니다.
    주문>>카페라떼 3
    가격은 12000원입니다.
    주문>>라떼 2잔
    수량은 정수로 입력해주세요!
    주문>>라떼 3
    가격은 15000원입니다.
    주문>>그만
*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class week0317 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String coffee[] = {"핫아메리카노", "아이스아메리카노", "카페라떼", "라떼"};
        int price[] = {3000, 3500, 4000, 5000};

        for (int i = 0; i < coffee.length; i++) {
            System.out.print(coffee[i]);
            if (i < coffee.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" 있습니다.");

        while (true) {
            System.out.print("주문>>");
            String orderName = scanner.next();

            if (orderName.equals("그만")) {
                break;
            }

            try {
                int quantity = scanner.nextInt();
                boolean found = false;

                for (int i = 0; i < coffee.length; i++) {
                    if (orderName.equals(coffee[i])) {
                        System.out.println("가격은 " + (price[i] * quantity) + "원입니다.");
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println(orderName + "은(는) 없는 메뉴입니다.");
                }

            } catch (InputMismatchException e) {
                System.out.println("수량은 정수로 입력해주세요!");
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}
