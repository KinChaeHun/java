// 2. 다음 main() 메소드와 실행 결과를 참고하여 ColorTV를 상속받는 SmartTV 클래스를 작성하라.
/*
    나의 SmartTV는 192.168.0.5 주소의 77인치 2000000컬러
*/

class TV {
    private int size;
    public TV(int size) { this.size = size; }
    protected int getSize() { return size; }
}

class ColorTV extends TV {
    private int colors;
    public ColorTV(int size, int colors) {
        super(size);
        this.colors = colors;
    }
    protected int getColors() {
        return this.colors;
    }
    public void printProperty() {
        System.out.println(getSize() + "인치 " + this.colors + "컬러");
    }
}

public class SmartTV extends ColorTV {
    private String ip;

    public SmartTV(String ip, int size, int colors) {
        super(size, colors);
        this.ip = ip;
    }

    @Override
    public void printProperty() {
        System.out.println("나의 SmartTV는 " + this.ip + " 주소의 " + getSize() + "인치 " + getColors() + "컬러");
    }

    public static void main(String[] args) {
        SmartTV smartTV = new SmartTV("192.168.0.5", 77, 2000000);
        smartTV.printProperty();
    }
}
