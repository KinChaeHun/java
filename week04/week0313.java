// 13. 과목과 학점이 들어 있는 다음의 2개 배열을 활용하여, 과목명을 입력받아 학점을 출력하는
//     프로그램을 작성하라. "그만"이 입력되면 프로그램을 종료한다.
/*
    과목>>Java
    Java 학점은 A+
    과목>>C++
    C++ 학점은 B+
    과목>>c++
    없는 과목입니다.
    과목>>Python
    Python 학점은 B
    과목>>그만
*/

import java.util.Scanner;

public class week0313 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String course[] = {"C", "C++", "Python", "Java", "HTML5"};
        String grade[] = {"A", "B+", "B", "A+", "C"};

        while (true) {
            System.out.print("과목>>");
            String inputCourse = scanner.next();

            if (inputCourse.equals("그만")) {
                break;
            }

            boolean found = false;
            for (int i = 0; i < course.length; i++) {
                if (inputCourse.equals(course[i])) {
                    System.out.println(inputCourse + " 학점은 " + grade[i]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("없는 과목입니다.");
            }
        }

        scanner.close();
    }
}
