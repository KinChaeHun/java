// 12. 사용자로부터 "남" 혹은 "여"를 입력받아, 남자 또는 여자의 이름을 작성하는 프로그램을 작성하라.
//     다음 실행 예시와 같이 "그만"을 입력하면 프로그램은 종료한다.
//     (힌트: 이름 생성을 위한 배열들이 주어짐)
/*
    ***** 작명 프로그램이 실행됩니다. *****

    남/여 선택>>남
    성 입력>>김
    추천 이름: 김하린
    남/여 선택>>여
    성 입력>>이
    추천 이름: 이하랑
    남/여 선택>>남
    성 입력>>김
    추천 이름: 김진우
    남/여 선택>>홍
    추천 이름: 홍민우
    남/여 선택>>이아아
    남/여 그만 중에서 입력하세요!
    남/여 선택>>그만
*/

import java.util.Scanner;

public class week0312 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String boyMiddleList[] = {"기", "민", "우", "재", "준", "진", "현", "승", "찬", "성"};
        String boyLastList[] = {"준", "민", "성", "찬", "현", "진", "재", "우", "기", "승"};
        String girlMiddleList[] = {"서", "연", "지", "수", "민", "현", "진", "은", "유", "아"};
        String girlLastList[] = {"연", "서", "민", "수", "진", "현", "은", "아", "유", "지"};

        System.out.println("***** 작명 프로그램이 실행됩니다. *****");
        System.out.println();

        while (true) {
            System.out.print("남/여 선택>>");
            String choice = scanner.next();

            if (choice.equals("그만")) {
                break;
            } else if (choice.equals("남")) {
                System.out.print("성 입력>>");
                String lastName = scanner.next();
                int midIndex = (int)(Math.random() * boyMiddleList.length);
                int lastIndex = (int)(Math.random() * boyLastList.length);
                String name = lastName + boyMiddleList[midIndex] + boyLastList[lastIndex];
                System.out.println("추천 이름: " + name);

            } else if (choice.equals("여")) {
                System.out.print("성 입력>>");
                String lastName = scanner.next();
                int midIndex = (int)(Math.random() * girlMiddleList.length);
                int lastIndex = (int)(Math.random() * girlLastList.length);
                String name = lastName + girlMiddleList[midIndex] + girlLastList[lastIndex];
                System.out.println("추천 이름: " + name);

            } else {
                System.out.println("남/여 그만 중에서 입력하세요!");
            }
        }

        scanner.close();
    }
}
