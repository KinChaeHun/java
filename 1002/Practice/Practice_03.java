// [3~7] 다음과 같이 2차원 상의 한 점을 표현하는 Point 클래스가 있다.
/*
    class Point {
        private int x, y;
        public Point(int x, int y) { this.x = x; this.y = y; }
        public int getX() { return x; }
        public int getY() { return y; }
        protected void move(int x, int y) { this.x = x; this.y = y; }
    }
*/
// 3. Point를 상속받아 색을 가진 점을 나타내는 ColorPoint 클래스를 작성하라.
//    다음 main() 메소드를 포함하여 실행 결과와 같이 출력되게 하라.
/*
    BLUE색의 (10,20)의 점입니다.
*/

class Point {
    private int x, y;
    public Point(int x, int y) { this.x = x; this.y = y; }
    public int getX() { return x; }
    public int getY() { return y; }
    protected void move(int x, int y) { this.x = x; this.y = y; }
}

public class ColorPoint extends Point {
    private String color;

    public ColorPoint(int x, int y, String color) {
        super(x, y);
        this.color = color;
    }

    public void setXY(int x, int y) {
        move(x, y);
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.color + "색의 (" + getX() + "," + getY() + ")의 점";
    }

    public static void main(String[] args) {
        ColorPoint cp = new ColorPoint(5, 5, "RED");
        cp.setXY(10, 20);
        cp.setColor("BLUE");
        String str = cp.toString();
        System.out.println(str + "입니다.");
    }
}
