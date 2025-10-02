// 묵찌빠 게임을 만들어본다. 사람과 컴퓨터가 게임을 하며, 게임의 원칙은 다음과 같다.
// - 사람이 먼저 오너가 되어 시작한다.
// - 사람이 묵, 찌, 빠 중 하나를 입력하고, 컴퓨터도 묵, 찌, 빠 중 하나를 선택하고 비교한다.
// - 사람이 이기면 사람이 계속 오너가 되고, 지면 오너가 컴퓨터로 바뀐다.
// - 오너인 경우에 이기면 게임이 끝나고 승자가 된다.
// - 비기는 경우는 오너가 바뀌지 않는다.
// - 잘못된 입력이 들어오면 다시 입력받는다.
/*
    ***** 묵찌빠 게임을 시작합니다. *****
    선수이름을 입력 >>황기태
    컴퓨터이름을 입력 >>AI
    2명의 선수 생성을 완료하였습니다.
    황기태>>묵
    AI>>결정하였습니다.
    황기태 : 묵, AI : 빠
    오너가 AI로 변경되었습니다.
    황기태>>빠
    AI>>결정하였습니다.
    황기태 : 빠, AI : 찌
    AI가 이겼습니다. 게임을 종료합니다.
*/

import java.util.Scanner;

abstract class Player {
    protected String name;
    protected Scanner scanner;

    public Player(String name) {
        this.name = name;
        this.scanner = new Scanner(System.in);
    }

    public String getName() {
        return name;
    }

    abstract public String turn();
}

class Human extends Player {
    public Human(String name) {
        super(name);
    }

    @Override
    public String turn() {
        while (true) {
            System.out.print(this.name + ">>");
            String move = scanner.next();
            if (move.equals("묵") || move.equals("찌") || move.equals("빠")) {
                return move;
            } else {
                System.out.println("묵 찌 빠 중에서 다시 입력하세요.");
            }
        }
    }
}

class Computer extends Player {
    private String[] moves = { "묵", "찌", "빠" };

    public Computer(String name) {
        super(name);
    }

    @Override
    public String turn() {
        System.out.println(this.name + ">>결정하였습니다.");
        int index = (int) (Math.random() * 3);
        return moves[index];
    }
}

public class MukJjiPpaGame {
    private Player[] players;
    private int ownerIndex;

    public MukJjiPpaGame() {
        players = new Player[2];
        ownerIndex = 0;
    }

    private int getRoundWinnerIndex(String move1, String move2) {
        if (move1.equals(move2)) {
            return -1; // Draw
        }
        if ((move1.equals("묵") && move2.equals("찌")) ||
            (move1.equals("찌") && move2.equals("빠")) ||
            (move1.equals("빠") && move2.equals("묵"))) {
            return 0; // Player 1 wins
        }
        return 1; // Player 2 wins
    }

    public void run() {
        System.out.println("***** 묵찌빠 게임을 시작합니다. *****");
        Scanner sc = new Scanner(System.in);
        System.out.print("선수이름을 입력 >>");
        String humanName = sc.next();
        System.out.print("컴퓨터이름을 입력 >>");
        String comName = sc.next();
        players[0] = new Human(humanName);
        players[1] = new Computer(comName);
        System.out.println("2명의 선수 생성을 완료하였습니다.");

        while (true) {
            String humanMove = players[0].turn();
            String comMove = players[1].turn();

            System.out.println(players[0].getName() + " : " + humanMove + ", " + players[1].getName() + " : " + comMove);

            int roundWinnerIndex = getRoundWinnerIndex(humanMove, comMove);

            if (roundWinnerIndex == -1) {
                System.out.println("비겼습니다.");
                continue;
            }

            if (roundWinnerIndex == ownerIndex) {
                System.out.println(players[ownerIndex].getName() + "가 이겼습니다. 게임을 종료합니다.");
                break;
            } else {
                ownerIndex = roundWinnerIndex;
                System.out.println("오너가 " + players[ownerIndex].getName() + "로 변경되었습니다.");
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        MukJjiPpaGame game = new MukJjiPpaGame();
        game.run();
    }
}
