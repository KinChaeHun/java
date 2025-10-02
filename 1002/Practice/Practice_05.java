// 5. Point 클래스를 상속받아 3차원의 점을 나타내는 Point3D 클래스를 작성하라.
//    다음 main() 메소드를 포함하여 실행 결과와 같이 출력되게 하라.
/*
    (1,2,3)의 점입니다.
    (1,2,4)의 점입니다.
    (1,2,2)의 점입니다.
    (5,5,2)의 점입니다.
    (100,200,300)의 점입니다.
*/

class Point {
    private int x, y;
    public Point(int x, int y) { this.x = x; this.y = y; }
    public int getX() { return x; }
    public int getY() { return y; }
    protected void move(int x, int y) { this.x = x; this.y = y; }
}

public class Point3D extends Point {
    private int z;

    public Point3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    public void moveUp() {
        this.z++;
    }

    public void moveDown(int distance) {
        this.z -= distance;
    }

    public void move(int x, int y, int z) {
        super.move(x, y);
        this.z = z;
    }

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + "," + this.z + ")의 점";
    }

    public static void main(String[] args) {
        Point3D p = new Point3D(1, 2, 3);
        System.out.println(p.toString() + "입니다.");

        p.moveUp();
        System.out.println(p.toString() + "입니다.");

        p.moveDown(2);
        System.out.println(p.toString() + "입니다.");

        p.move(5, 5);
        System.out.println(p.toString() + "입니다.");

        p.move(100, 200, 300);
        System.out.println(p.toString() + "입니다.");
    }
}
