// 4. Point를 상속받아 색을 가진 점을 나타내는 ColorPoint2 클래스를 작성하라.
//    다음 main() 메소드를 포함하여 실행 결과와 같이 출력되게 하라.
/*
    WHITE색의 (0,0)의 점입니다.
    BLUE색의 (5,5)의 점입니다.
    cp에서 임계점까지의 거리는 120.41594578792295
*/

class Point {
    private int x, y;
    public Point(int x, int y) { this.x = x; this.y = y; }
    public int getX() { return x; }
    public int getY() { return y; }
    protected void move(int x, int y) { this.x = x; this.y = y; }
}

public class ColorPoint2 extends Point {
    private String color;

    public ColorPoint2() {
        this(0, 0, "WHITE");
    }

    public ColorPoint2(int x, int y) {
        this(x, y, "BLACK");
    }

    public ColorPoint2(int x, int y, String color) {
        super(x, y);
        this.color = color;
    }

    public void setXY(int x, int y) {
        move(x, y);
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getDistance(ColorPoint2 p) {
        long dx = this.getX() - p.getX();
        long dy = this.getY() - p.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return this.color + "색의 (" + getX() + "," + getY() + ")의 점";
    }

    public static void main(String[] args) {
        ColorPoint2 zeroPoint = new ColorPoint2();
        System.out.println(zeroPoint.toString() + "입니다.");

        ColorPoint2 cp = new ColorPoint2(10, 10, "RED");
        cp.setXY(5, 5);
        cp.setColor("BLUE");
        System.out.println(cp.toString() + "입니다.");

        ColorPoint2 thresholdPoint = new ColorPoint2(100, 100);
        System.out.println("cp에서 임계점까지의 거리는 " + cp.getDistance(thresholdPoint));
    }
}
