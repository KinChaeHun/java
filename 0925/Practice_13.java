// 13. 간단한 콘서트 예약 시스템을 만들어보자. 다수의 클래스를 다루고 객체의 배열을 다루는
//     프로그램이다. 예약 시스템의 기능은 다음과 같다.
//     - 공연은 하루에 한 번.
//     - 좌석은 S석, A석, B석으로 나뉘며, 각각 10개의 좌석이 있다.
//     - 메뉴는 "예약", "조회", "취소", "끝내기"가 있다.
//     - 예약은 한 자리만 가능하고, 좌석 타입, 예약자 이름, 좌석 번호를 입력받아 예약한다.
//     - 조회는 모든 좌석을 출력한다.
//     - 취소는 예약자의 이름을 입력받아 취소한다.
//     - 없는 이름, 없는 번호, 없는 메뉴 등에 대해서 오류 메시지를 출력하고 다시 시도하도록 한다.
/*
    명품콘서트홀 예약 시스템입니다.
    예약:1, 조회:2, 취소:3, 끝내기:4>>1
    좌석구분 S(1), A(2), B(3)>>1
    S>> --- --- --- --- --- --- --- --- --- ---
    이름>>황기태
    번호>>5
    예약:1, 조회:2, 취소:3, 끝내기:4>>2
    S>> --- --- --- --- 황기태 --- --- --- --- ---
    A>> --- --- --- --- --- --- --- --- --- ---
    B>> --- --- --- --- --- --- --- --- --- ---
    <<<조회를 완료하였습니다.>>>
    예약:1, 조회:2, 취소:3, 끝내기:4>>3
    좌석 S:1, A:2, B:3>>1
    S>> --- --- --- --- 황기태 --- --- --- --- ---
    이름>>황기태
    <<<취소를 완료하였습니다.>>>
    예약:1, 조회:2, 취소:3, 끝내기:4>>4
*/

import java.util.Scanner;

class Seat {
    private String name;

    public Seat() {
        this.name = "---";
    }

    public String getName() {
        return name;
    }

    public void reserve(String name) {
        this.name = name;
    }

    public void cancel() {
        this.name = "---";
    }

    public boolean isReserved() {
        return !name.equals("---");
    }
}

class SeatGroup {
    private String type;
    private Seat[] seats;

    public SeatGroup(String type, int num) {
        this.type = type;
        this.seats = new Seat[num];
        for (int i = 0; i < seats.length; i++) {
            seats[i] = new Seat();
        }
    }

    public boolean reserve(String name, int seatNum) {
        if (seatNum < 1 || seatNum > seats.length) {
            System.out.println("잘못된 좌석 번호입니다.");
            return false;
        }
        if (seats[seatNum - 1].isReserved()) {
            System.out.println("이미 예약된 좌석입니다.");
            return false;
        }
        seats[seatNum - 1].reserve(name);
        return true;
    }

    public boolean cancel(String name) {
        for (int i = 0; i < seats.length; i++) {
            if (name.equals(seats[i].getName())) {
                seats[i].cancel();
                return true;
            }
        }
        System.out.println("예약된 사람을 찾을 수 없습니다.");
        return false;
    }

    public void show() {
        System.out.print(this.type + ">> ");
        for (int i = 0; i < seats.length; i++) {
            System.out.print(seats[i].getName() + " ");
        }
        System.out.println();
    }
}

public class ConcertApp {
    private Scanner scanner;
    private SeatGroup[] seatGroups;

    public ConcertApp() {
        this.scanner = new Scanner(System.in);
        this.seatGroups = new SeatGroup[3];
        seatGroups[0] = new SeatGroup("S", 10);
        seatGroups[1] = new SeatGroup("A", 10);
        seatGroups[2] = new SeatGroup("B", 10);
    }

    private void reserve() {
        System.out.print("좌석구분 S(1), A(2), B(3)>>");
        int type = scanner.nextInt();
        if (type < 1 || type > 3) {
            System.out.println("잘못된 좌석 타입입니다.");
            return;
        }
        seatGroups[type - 1].show();
        System.out.print("이름>>");
        String name = scanner.next();
        System.out.print("번호>>");
        int seatNum = scanner.nextInt();
        seatGroups[type - 1].reserve(name, seatNum);
    }

    private void view() {
        for (int i = 0; i < seatGroups.length; i++) {
            seatGroups[i].show();
        }
        System.out.println("<<<조회를 완료하였습니다.>>>");
    }

    private void cancel() {
        System.out.print("좌석 S:1, A:2, B:3>>");
        int type = scanner.nextInt();
        if (type < 1 || type > 3) {
            System.out.println("잘못된 좌석 타입입니다.");
            return;
        }
        seatGroups[type - 1].show();
        System.out.print("이름>>");
        String name = scanner.next();
        if (seatGroups[type - 1].cancel(name)) {
            System.out.println("<<<취소를 완료하였습니다.>>>");
        }
    }

    public void run() {
        System.out.println("명품콘서트홀 예약 시스템입니다.");
        while (true) {
            System.out.print("예약:1, 조회:2, 취소:3, 끝내기:4>>");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    reserve();
                    break;
                case 2:
                    view();
                    break;
                case 3:
                    cancel();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public static void main(String[] args) {
        ConcertApp app = new ConcertApp();
        app.run();
    }
}
