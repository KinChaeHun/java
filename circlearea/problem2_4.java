package hello;

import java.util.Scanner;

public class problem2_4 {
    public static void main(String[] args) {
        int day, total, airfare, room, totalMoney;
        String travel;

        Scanner scn = new Scanner(System.in);

        System.out.print("여행지 >> ");
        travel = scn.next();

        System.out.print("인원수 >> ");
        total = scn.nextInt();

        room = (total + 1) / 2;

        System.out.print("숙박일 >> ");
        day = scn.nextInt();

        System.out.print("1인당 항공료 >> ");
        airfare = scn.nextInt();
        airfare *= total;

        System.out.print("1방당 숙박비 >> ");
        int roomCost = scn.nextInt();
        int stayCost = room * day * roomCost;

        totalMoney = stayCost + airfare;

        System.out.println(total + "명의 " + travel + " " + day + "박 " + (day + 1) +
                "일 여행에는 방이 " + room + "개 필요하며 경비는 " + totalMoney + "원입니다.");

        scn.close();
    }
}
