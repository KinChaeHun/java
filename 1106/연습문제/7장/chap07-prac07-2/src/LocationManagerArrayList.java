import java.util.*;

// 하나의 도시를 묘사하는 클래스
class Location {
	private String city;
	private double longitude; // 경도
	private double latitude; // 위도
	
	public Location(String city, double longitude, double latitude) {
		this.city = city;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public void setCity(String city) { 
		this.city = city;
	}
	public String getCity() {
		return city;
	}
	public void setLogitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLatitude() {
		return latitude;
	}
}

public class LocationManagerArrayList {
	private Scanner scanner = new Scanner(System.in);
	private ArrayList<Location> locList = new ArrayList<Location>();
	
	private void read() {
		System.out.println("도시,경도,위도를 입력하세요.");
		for (int i=0; i<4; i++) {
			System.out.print(">> ");
			String text = scanner.nextLine();
			StringTokenizer st = new StringTokenizer(text, ",");
			String city = st.nextToken().trim();
			double logitude = Double.parseDouble(st.nextToken().trim());
			double latitude = Double.parseDouble(st.nextToken().trim());
			
			Location loc = new Location(city, logitude, latitude);
			locList.add(loc); // ArrayList 에 저장
		}			
	}
	
	private void printAll() {
		System.out.println("---------------------------");
		for(Location loc : locList) {
			System.out.print(loc.getCity() + "\t");
			System.out.print(loc.getLongitude() + "\t");
			System.out.println(loc.getLatitude());
		}
		System.out.println("---------------------------");		
	}

	private void processQuery() {
		while(true) {
			System.out.print("도시 이름 >> ");
			String city = scanner.nextLine(); // 도시 이름 입력
			if(city.equals("그만"))
				return; // 종료
			
			
			Location foundLoc = null; 
			for(Location loc : locList) { // ArrayList 전체 검색
				if(loc.getCity().equals(city)) { // 도시 이름이 저장된 이름과 같다면
					foundLoc = loc;
					break;
				}
			}
			if(foundLoc == null) { // 도시가 ArrayList에 저장되어 있지 않다면
				System.out.println(city + "는 없습니다.");
			}
			else { // 검색된 Location 객체
				System.out.print(foundLoc.getCity()  + "\t");
				System.out.print(foundLoc.getLongitude() + "\t");
				System.out.println(foundLoc.getLatitude());
			}
		}
	}
	
	public void run() {
		read();
		printAll();
		processQuery();
	}
	
	public static void main (String[] args) {
		LocationManagerArrayList man = new LocationManagerArrayList();
		man.run();
	}
}
