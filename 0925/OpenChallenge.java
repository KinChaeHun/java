/*오픈챌린지: 끝말잇기 게임 만들기
n명이 참가하는 끝말잇기 게임을 만들어보자. 처음 단어는 "아버지"이다. n명의 참가자들은 순서대로 자신의 단어를 입력하면 된다. 끝말잇기에서 끝말이 틀린 경우 게임을 끝내고 게임에서 진 참가자를 화면에 출력한다. 
프로그래밍에서는 시간 지연을 구현하지 않아도 된다. 그렇지만 참가자들이 스스로 시간을 제어보는 것도 좋겠다. 
이 문제의 핵심은 여러 개의 객체와 배열 사용을 연습하기 위한 것으로, main()을 포함하는 WordGameApp 클래스와 각 선수를 나타내는 Player 클래스를 작성하고, 실행 중에는 WordGameApp 객체 하나와 선수 숫자만큼의 Player 객체를 생성하는데 있다. 
문제에 충실하게 프로그램을 작성하여야 실력이 늘게 됨을 알기 바란다.

끝말잇기 게임을 시작합니다...
게임에 참가하는 인원은 몇명입니까>>3
참가자의 이름을 입력하세요>>황기태
참가자의 이름을 입력하세요>>이재문
참가자의 이름을 입력하세요>>한원선
시작하는 단어는 아버지입니다
황기태>>지우개
이재문>>개나리
한원선>>리본
황기태>>분죽
이재문>>죽마
이재문이 졌습니다.
*/
import java.util.Scanner;

public class WordGameApp {
	private int numOfPlayer;  // 전체 플레이어 수
	private Player[] players;
	private String newWord, beforeWord;
	private Scanner scanner;

	public WordGameApp() {
		beforeWord = "아버지";  // 시작 단어 설정
		scanner = new Scanner(System.in);
	}
	
	// 플레이어 설정
	private void setPlayers() {
		// 플레이어 수 입력
		System.out.print("게임에 참가하는 인원은 몇명입니까>>");
		numOfPlayer = scanner.nextInt();
		players = new Player[numOfPlayer];
		
		// 이름 입력
		for(int i=0; i<numOfPlayer; i++) {
			System.out.print("참가자의 이름을 입력하세요>>");
			String name = scanner.next();
            players[i] = new Player(name);
		}
	}
	
	public void run() {
		System.out.println("끝말잇기 게임을 시작합니다...");
		setPlayers();  // 플레이어 설정
		System.out.println("시작하는 단어는 " + beforeWord + "입니다");

		int currentIndex = 0;
		while(true) {
			newWord = players[currentIndex].getWordFromUser();
			
			if(!checkSuccess(beforeWord, newWord)) {	// 진 경우
				System.out.print(players[currentIndex].getName()+"이 졌습니다.");
				break;
			}
			
			beforeWord = newWord;
			currentIndex = (currentIndex + 1) % numOfPlayer;
		}
		scanner.close();
	}
	
	public boolean checkSuccess(String beforeWord, String newWord) {
		char lastChar = beforeWord.charAt(beforeWord.length() - 1);  // 마지막 문자
		char firstChar = newWord.charAt(0);	// 첫 번째 문자
		
		if(lastChar == firstChar) return true;
		else return false;
	}
	
	public static void main(String[] args) {
		WordGameApp game = new WordGameApp();
		game.run();
	}

}

import java.util.Scanner;

public class Player {
	private String name;
	private String word;
	private Scanner scanner = new Scanner(System.in);
	
	public Player(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getWordFromUser() {
		System.out.print(name + ">>");
		word = scanner.next();
		return word;
	}
	
}
