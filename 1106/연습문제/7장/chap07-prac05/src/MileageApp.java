import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class MileageApp {
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	private Scanner scanner = new Scanner(System.in);
	
	public MileageApp() { }
	
	private void read() {
		while(true) {
			System.out.print("이름과 마일리지>>");
			String name = scanner.next();
			if(name.equals("그만"))
				break;

			int mileage = scanner.nextInt();
			// 사용자 입력 오류 처리 안 함
			
			Integer value = map.get(name);
			if(value == null) { // 이름이 해시맵에 없는 경우
				map.put(name, mileage);
			}
			else { // 이름이 이미 해시맵에 있는 경우
				mileage += value; // value가 int로 자동 언박싱됨
				map.put(name, mileage);
			}
		}
		
	}
	
	private void viewAll() { // 전체 출력과 함께 최고 마일리지 소유자 출력
		Set<String> keys = map.keySet(); // map 내의 모든 키들 알아내기
		Iterator<String> it = keys.iterator(); // 키들의 iterator
		
		String bestUser = "";
		int bestMileage = 0;
		while(it.hasNext()) { // 전체 키 셋 방문
			String name = it.next();
			int mileage = map.get(name);
			System.out.print("(" + name + ":" + mileage+")");
			
			// 현재까지 가장 마일리지가 높은 사용자 결정 
			if(bestMileage < mileage) {
				bestMileage = mileage;
				bestUser = name;
			}
		}
		System.out.println("가장 마일리지가 높은 고객은 " + bestUser + "입니다.");
	}
	
	public void run() {
		System.out.println("*** 마일리지 관리 프로그램입니다.***");
		read();
		viewAll();
		System.out.println("프로그램을 종료합니다.");
	}
	
	public static void main(String[] args) {
		MileageApp app = new MileageApp();
		app.run();
	}

}
