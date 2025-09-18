/*

두 사람이하는 가위바위보 게임을 만들어보자 두 사람의 이름은 '철수'와 '영희'이다.
먼저 " 철수 >> "를 출력하고 "가위", "바위", "보" 중 하나를 문자열로 입력받는다.
그리고 " 영희 >> "를 출력하고 마찬가지로 입력받는다.
입력받은 문자열을 비교하여 누가 이겼는지 판별하여 승자를 출력한다.

*/


import java.util.Scanner;

public class RockPaperScissors {
    public static void main(String[] args) {
        System.out.println("가위바위보 게임입니다. 가위, 바위, 보 중에서 입력하세요");
        Scanner scn = new Scanner(System.in);
        String varC;
        String varY;

        System.out.print("철수 >> ");
        varC = scn.next();

        System.out.print("영희 >> ");
        varY = scn.next();

        if(varC.equals("가위")){
            if(varY.equals("가위")) {
                System.out.print("비겼습니다.");
            }
            else if(varY.equals("바위")) {
                System.out.print("영희가 이겼습니다.");
            }
            else {
                System.out.print("철수가 이겼습니다.");
            }
        }

        if(varC.equals("바위")){
            if(varY.equals("바위")) {
                System.out.print("비겼습니다.");
            }
            else if(varY.equals("보")) {
                System.out.print("영희가 이겼습니다.");
            }
            else {
                System.out.print("철수가 이겼습니다.");
            }
        }

        if(varC.equals("보")){
            if(varY.equals("보")) {
                System.out.print("비겼습니다.");
            }
            else if(varY.equals("가위")) {
                System.out.print("영희가 이겼습니다.");
            }
            else {
                System.out.print("철수가 이겼습니다.");
            }
        }
        scn.close();
    }
}

