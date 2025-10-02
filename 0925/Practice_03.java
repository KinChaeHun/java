// 3. 다음은 한 학생의 점수를 나타내는 클래스이다. 이름과 3개의 과목 점수를 각각 입력받아
//    grade 객체를 생성하고 성적 평균을 출력하는 main()과 실행 예시는 다음과 같다.
//    main()을 포함하는 Grade 클래스를 작성하라.
/*
    이름, 자바, 웹프로그래밍, 운영체제 순으로 점수 입력>>박채원 99 85 95
    박채원의 평균은 93
*/

import java.util.Scanner;

public class Grade {
    private String name;
    private int java;
    private int web;
    private int os;

    public Grade(String name, int java, int web, int os) {
        this.name = name;
        this.java = java;
        this.web = web;
        this.os = os;
    }

    public String getName() {
        return name;
    }

    public int getAverage() {
        return (this.java + this.web + this.os) / 3;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("이름, 자바, 웹프로그래밍, 운영체제 순으로 점수 입력>>");
        String name = scanner.next();
        int java = scanner.nextInt();
        int web = scanner.nextInt();
        int os = scanner.nextInt();

        Grade student = new Grade(name, java, web, os);
        System.out.println(student.getName() + "의 평균은 " + student.getAverage());

        scanner.close();
    }
}
