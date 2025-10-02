// 10. 한 달의 다이어리를 관리하는 프로그램을 작성하라. 한 달은 30일로 정한다. 이 프로그램은
//     사용자로부터 날짜와 기억할 일을 간단히 저장하거나, 검색하는 등의 기능을 수행하며, 실행
//     예시는 다음과 같다. 기억할 일은 빈칸 없이 4글자 이하의 문자열만 다룬다.
/*
    **** 2024년 10월 다이어리 ****
    기록:1, 보기:2, 종료:3>>1
    날짜(1-30)와 텍스트(빈칸없이 4글자이하)>>3 영어시험
    기록:1, 보기:2, 종료:3>>1
    날짜(1-30)와 텍스트(빈칸없이 4글자이하)>>8 기태점심
    기록:1, 보기:2, 종료:3>>1
    날짜(1-30)와 텍스트(빈칸없이 4글자이하)>>17 아빠생일
    기록:1, 보기:2, 종료:3>>1
    날짜(1-30)와 텍스트(빈칸없이 4글자이하)>>29 축제
    기록:1, 보기:2, 종료:3>>2
    3일의 일정은 영어시험입니다.
    기록:1, 보기:2, 종료:3>>1
    날짜(1-30)와 텍스트(빈칸없이 4글자이하)>>3 자바시험
    기록:1, 보기:2, 종료:3>>2
    3일의 일정은 자바시험입니다.
    기록:1, 보기:2, 종료:3>>3
    프로그램을 종료합니다.
*/

import java.util.Scanner;

class DayDiary {
    private String task;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}

public class MonthDiary {
    private DayDiary[] days;
    private Scanner scanner;
    private int year;
    private int month;

    public MonthDiary(int year, int month) {
        this.year = year;
        this.month = month;
        this.scanner = new Scanner(System.in);
        this.days = new DayDiary[30];
        for (int i = 0; i < days.length; i++) {
            days[i] = new DayDiary();
        }
    }

    private void write() {
        System.out.print("날짜(1-30)와 텍스트(빈칸없이 4글자이하)>>");
        int day = scanner.nextInt();
        String task = scanner.next();
        if (day < 1 || day > 30) {
            System.out.println("날짜를 잘못 입력하였습니다.");
            return;
        }
        days[day - 1].setTask(task);
    }

    private void show() {
        System.out.print("날짜(1-30)>>");
        int day = scanner.nextInt();
        if (day < 1 || day > 30) {
            System.out.println("날짜를 잘못 입력하였습니다.");
            return;
        }
        String task = days[day - 1].getTask();
        if (task == null) {
            System.out.println(day + "일에는 일정이 없습니다.");
        } else {
            System.out.println(day + "일의 일정은 " + task + "입니다.");
        }
    }

    private void finish() {
        System.out.println("프로그램을 종료합니다.");
        scanner.close();
    }

    public void run() {
        System.out.println("**** " + this.year + "년 " + this.month + "월 다이어리 ****");
        while (true) {
            System.out.print("기록:1, 보기:2, 종료:3>>");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    write();
                    break;
                case 2:
                    show();
                    break;
                case 3:
                    finish();
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public static void main(String[] args) {
        MonthDiary monthDiary = new MonthDiary(2024, 10);
        monthDiary.run();
    }
}
