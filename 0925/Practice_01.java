// 1. 자바 클래스를 만들어보자. 다음 main() 메소드를 실행하였을 때 예시와 같이 출력되도록
//    TV 클래스를 작성하라.
/*
    Samsung에서 만든 300만원짜리 50인치 TV
*/

public class TV {
    private String manufacturer;
    private int size;
    private int price;

    public TV(String manufacturer, int size, int price) {
        this.manufacturer = manufacturer;
        this.size = size;
        this.price = price;
    }

    public void show() {
        System.out.println(manufacturer + "에서 만든 " + price + "만원짜리 " + size + "인치 TV");
    }

    public static void main(String[] args) {
        TV tv = new TV("Samsung", 50, 300); // 300만원짜리 Samsung에서 만든 50인치 TV
        tv.show();
    }
}
