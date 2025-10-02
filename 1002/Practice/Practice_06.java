// 6. Point 클래스를 상속받아 양수만 가능한 점을 나타내는 PositivePoint 클래스를 작성하라.
//    다음 main() 메소드를 포함하여 실행 결과와 같이 출력되게 하라.
/*
    (5,5)의 점입니다.
    (5,5)의 점입니다.
    (1,1)의 점입니다.
*/

class Point {
    private int x, y;
    public Point(int x, int y) { this.x = x; this.y = y; }
    public int getX() { return x; }
    public int getY() { return y; }
    protected void move(int x, int y) { this.x = x; this.y = y; }
}

public class PositivePoint extends Point {
    public PositivePoint() {
        super(1, 1);
    }

    public PositivePoint(int x, int y) {
        super(x, y);
        if (x <= 0 || y <= 0) {
            super.move(1, 1);
        }
    }

    @Override
    public void move(int x, int y) {
        if (x > 0 && y > 0) {
            super.move(x, y);
        }
    }

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")의 점";
    }

    public static void main(String[] args) {
        PositivePoint p = new PositivePoint();
        p.move(5, 5);
        System.out.println(p.toString() + "입니다.");

        p.move(2, -2);
        System.out.println(p.toString() + "입니다.");

        PositivePoint q = new PositivePoint(-10, -10);
        System.out.println(q.toString() + "입니다.");
    }
}
