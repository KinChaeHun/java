import java.util.HashMap;
import java.util.Scanner;
import java.util.*;

public class WalkingStepManager {
	// 이름과 걸음 수를 저장할 해시맵 생성
	private HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
	private Scanner scanner = new Scanner(System.in);

	public WalkingStepManager() { }
	
	private void read() {
		// '그만'이 입력될 때까지 이름과 걸음수 입력.
		// 새 이름의 경우 해시맵에 새 항목을 생성하고 걸음 수들을 저장하며,
		// 동일한 이름의 경우 기존 ArrayList에 누적하여 저장
		while(true) {
			System.out.print("이름과 걸음수>>");
			String line = scanner.nextLine(); // 한 라인 입력
			if(line.equals("그만"))
				break;	
			
			String [] tokens = line.split(" "); // 라인을 공백으로 분리
			String name = tokens[0]; // 첫번째 토큰은 이름
			ArrayList<Integer> list = map.get(name); // 해시맵에서 name의 키를 가진 ArrayList 읽기
			if(list == null) { // 이름이 없는 경우
				list = new ArrayList<Integer>(); // 빈 ArrayList 생성
				map.put(name, list); // 이름과 빈 ArrayList 삽입
			}
			// 이름이 해시맵에 이미 있는 경우, list 그대로 이용
			for(int i=0; i<tokens.length - 1; i++) 
				list.add(Integer.parseInt(tokens[i+1])); // 두번째 토큰부터는 점수들
		}	
	}
	
	private void decideBest() {
		Set<String> keys = map.keySet(); // 해시맵에 저장된 모든 키(이름)을 알아낸다.
		Iterator<String> it = keys.iterator();
		String bestName = ""; // 가장 많이 걸은 사람 이름
		int bestWalkSum = 0; // 가장 많이 걸은 걸음 수
		while(it.hasNext()) {
			String name = it.next();
			ArrayList<Integer> list = map.get(name); // 이름으로 해시맵 검색
			
			// name의 총 걸음 수 계산
			int sum = 0;
			for(int walk : list) 
				sum += walk;
			
			// 최고의 걸음수와 비교하여 최고의 사람 결정
			if(bestWalkSum < sum) {
				bestWalkSum = sum;
				bestName = name;
			}
		}		
		System.out.println("걸음수가 가장 많은 사람은 " + bestName + " " + bestWalkSum + "보");
	}

	private void search() {
		while(true) {
			System.out.print("검색할 이름>>");
			String name = scanner.next(); // 한 라인 입력
			if(name.equals("그만"))
				break;		
			
			ArrayList<Integer> list = map.get(name); // 이름으로 해시맵 검색. 벡터 알아냄
			if(list == null) { // 이름이 해시맵에 없는 경우
				System.out.println(name + "은 없는 학생입니다.");
				continue;
			}
			
			int sum = 0;
			for(int score : list) {
				System.out.print(score + " ");
				sum += score;
			}
			
			System.out.println("평균 " + sum/list.size());			
		}	
	}
	
	public void run() {
		read();
		decideBest();
		search();
	}
	
	public static void main(String[] args) {
		WalkingStepManager man = new WalkingStepManager();
		man.run();
	}
}
