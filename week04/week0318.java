// 18. 1차원 정수 배열 2개를 생성하고 10명 학생의 학번과 점수를 입력받아 각각 저장하라.
//     그리고 다음의 같이 학번을 입력하면 점수를 출력하라.
/*
    10명 학생의 학번과 점수 입력
    1>>1 10
    2>>2 20
    3>>3 30
    ... (10명 입력) ...
    10>>100 90

    학번으로 검색: 1, 점수로 검색: 2, 끝내려면 3>>1
    학번>>황기태
    경고!! 정수를 입력하세요.
    학번>>3
    점수>>30점
    학번으로 검색: 1, 점수로 검색: 2, 끝내려면 3>>1
    학번>>15
    15의 학생은 없습니다.
    학번으로 검색: 1, 점수로 검색: 2, 끝내려면 3>>2
    점수>>80
    점수가 80인 학생은 6 7 8 입니다.
    학번으로 검색: 1, 점수로 검색: 2, 끝내려면 3>>3
    프로그램을 종료합니다.
*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class week0318 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] studentIds = new int[10];
        int[] scores = new int[10];

        System.out.println("10명 학생의 학번과 점수 입력");
        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + ">>");
            studentIds[i] = scanner.nextInt();
            scores[i] = scanner.nextInt();
        }

        while (true) {
            System.out.print("학번으로 검색: 1, 점수로 검색: 2, 끝내려면 3>>");
            try {
                int choice = scanner.nextInt();
                if (choice == 3) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                }

                if (choice == 1) {
                    System.out.print("학번>>");
                    try {
                        int searchId = scanner.nextInt();
                        boolean found = false;
                        for (int i = 0; i < studentIds.length; i++) {
                            if (studentIds[i] == searchId) {
                                System.out.println("점수>>" + scores[i] + "점");
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println(searchId + "의 학생은 없습니다.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("경고!! 정수를 입력하세요.");
                        scanner.nextLine();
                    }
                } else if (choice == 2) {
                    System.out.print("점수>>");
                    try {
                        int searchScore = scanner.nextInt();
                        String resultIds = "";
                        boolean found = false;
                        for (int i = 0; i < scores.length; i++) {
                            if (scores[i] == searchScore) {
                                resultIds += studentIds[i] + " ";
                                found = true;
                            }
                        }
                        if (found) {
                            System.out.println("점수가 " + searchScore + "인 학생은 " + resultIds + "입니다.");
                        } else {
                            System.out.println("점수가 " + searchScore + "인 학생은 없습니다.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("경고!! 정수를 입력하세요.");
                        scanner.nextLine();
                    }
                } else {
                    System.out.println("1~3 사이의 숫자를 입력하세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("경고!! 정수를 입력하세요.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}
