// 12. 다음과 같은 Dictionary 클래스가 있다. 실행 결과와 같이 작동하도록 Dictionary 클래스에
//     kor2Eng() 메소드를 작성하고, 실행 결과와 같이 검색을 수행하는 DicApp 클래스를 작성하라.
/*
    한영 단어 검색 프로그램입니다.
    한글 단어?미래
    미래는 future
    한글 단어?아기
    아기는 baby
    한글 단어?돈
    돈은 money
    한글 단어?그만
*/

import java.util.Scanner;

class Dictionary {
    private static String[] kor = { "사랑", "아기", "돈", "미래", "희망" };
    private static String[] eng = { "love", "baby", "money", "future", "hope" };

    public static String kor2Eng(String word) {
        for (int i = 0; i < kor.length; i++) {
            if (word.equals(kor[i])) {
                return eng[i];
            }
        }
        return null;
    }
}

public class DicApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("한영 단어 검색 프로그램입니다.");

        while (true) {
            System.out.print("한글 단어?");
            String koreanWord = scanner.next();

            if (koreanWord.equals("그만")) {
                break;
            }

            String englishWord = Dictionary.kor2Eng(koreanWord);
            if (englishWord == null) {
                System.out.println(koreanWord + "는 사전에 없습니다.");
            } else {
                System.out.println(koreanWord + "는 " + englishWord);
            }
        }
        
        scanner.close();
    }
}
