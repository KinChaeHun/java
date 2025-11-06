import java.util.HashMap;
import java.util.Scanner;

public class StockHashMap {
	private HashMap<String, Integer> stockMap = new HashMap<String, Integer>();
	private Scanner scanner = new Scanner(System.in);
	
	public StockHashMap() { }

	// 사용자로부터 여러 주식 종목과 주가 입력
	private void read() {
		System.out.println("주식 종목과 주가를 입력하세요(예:삼송전자 75000)");
		while(true) {
			System.out.print("종목, 주가>>");
			String stockName = scanner.next();
			if(stockName.equals("그만"))
				 break;
			int stockPrice = scanner.nextInt();
			stockMap.put(stockName, stockPrice);
		}
	}
	
	// 종목을 입력받고 주가 검색
	private void search() {
		System.out.println("주가를 검색합니다.");
		while(true) {
			System.out.print("종목>>");
			String stockName = scanner.next();
			if(stockName.equals("그만"))
				 break;
			Integer stockPrice = stockMap.get(stockName);
			if(stockPrice == null) 
				System.out.println(stockName + "은 없는 종목입니다.");
			else 
				System.out.println(stockName + "의 주가는 " + stockPrice + "원");
		}
	}
	
	public void run() {
		read(); // 사용자로부터 여러 주식 종목과 주가 입력
		search();
	}
	
	public static void main(String[] args) {
		StockHashMap app = new StockHashMap();
		app.run();
	}

}
