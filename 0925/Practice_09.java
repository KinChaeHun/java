// 9. 숨겨진 숫자에 가장 가까운 수를 제시하는 사람이 이기는 예측 게임을 작성해보자. 1~100 범위의
//    정수를 랜덤하게 생성하고, 게임에 참여한 선수들에게 수를 맞추게 한 후 숨겨진 정답에 가장 가까운
//    선수에게 1점을 부여한다. 게임이 여러 라운드로 만들어지고 이전에 선수 이름과 누적 점수를 저장한다.
//    main()을 포함하는 게임 프로그램의 클래스는 GuessGame으로 한다.
/*
    *** 예측 게임을 시작합니다. ***
    게임에 참여할 선수 수>>3
    선수 이름>>황기태
    선수 이름>>이재문
    선수 이름>>정인환
    1~100사이의 숫자가 결정되었습니다. 선수들은 맞추어 보세요.
    황기태>>80
    이재문>>50
    정인환>>70
    정답은 28. 정인환이 이겼습니다. 승점 1점 확보!
    계속하려면 yes 입력>>yes
    1~100사이의 숫자가 결정되었습니다. 선수들은 맞추어 보세요.
    황기태>>30
    이재문>>70
    정인환>>50
    정답은 66. 정인환이 이겼습니다. 승점 1점 확보!
    계속하려면 yes 입력>>no
    황기태:0 이재문:0 정인환:2
    정인환이 최종 승리하였습니다.
*/

import java.util.Scanner;

class Player {
    private String name;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void win() {
        this.score++;
    }
}

public class GuessGame {
    private Player[] players;
    private Scanner scanner;

    public GuessGame() {
        scanner = new Scanner(System.in);
    }

    private void registerPlayers() {
        System.out.print("게임에 참여할 선수 수>>");
        int n = scanner.nextInt();
        players = new Player[n];
        for (int i = 0; i < n; i++) {
            System.out.print("선수 이름>>");
            String name = scanner.next();
            players[i] = new Player(name);
        }
    }

    private void runRound() {
        int answer = (int) (Math.random() * 100) + 1;
        System.out.println("1~100사이의 숫자가 결정되었습니다. 선수들은 맞추어 보세요.");

        int[] guesses = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            System.out.print(players[i].getName() + ">>");
            guesses[i] = scanner.nextInt();
        }

        int minDiff = 101;
        int winnerIndex = -1;
        for (int i = 0; i < guesses.length; i++) {
            int diff = Math.abs(answer - guesses[i]);
            if (diff < minDiff) {
                minDiff = diff;
                winnerIndex = i;
            }

        }

        System.out.println("정답은 " + answer + ". " + players[winnerIndex].getName() + "이 이겼습니다. 승점 1점 확보!");
        players[winnerIndex].win();
    }

    private void finish() {
        int maxScore = -1;
        int finalWinnerIndex = -1;

        for (int i = 0; i < players.length; i++) {
            System.out.print(players[i].getName() + ":" + players[i].getScore() + " ");
            if (players[i].getScore() > maxScore) {
                maxScore = players[i].getScore();
                finalWinnerIndex = i;
            }
        }
        System.out.println();
        System.out.println(players[finalWinnerIndex].getName() + "이 최종 승리하였습니다.");
    }

    public void run() {
        System.out.println("*** 예측 게임을 시작합니다. ***");
        registerPlayers();

        while (true) {
            runRound();
            System.out.print("계속하려면 yes 입력>>");
            String choice = scanner.next();
            if (!choice.equals("yes")) {
                break;
            }
        }

        finish();
        scanner.close();
    }

    public static void main(String[] args) {
        GuessGame game = new GuessGame();
        game.run();
    }
}
