// 7. Point 클래스를 상속받아 3차원 색 점을 나타내는 Point3DColor 클래스를 작성하라.
//    다음 main() 메소드를 포함하여 실행 결과와 같이 출력되게 하라.
/*
    (10,20,30)RED점입니다.
    (1,2,3)BLUE점입니다.
    아니오
*/

class Point {
    private int x, y;
    public Point(int x, int y) { this.x = x; this.y = y; }
    public int getX() { return x; }
    public int getY() { return y; }
    protected void move(int x, int y) { this.x = x; this.y = y; }
}

public class Point3DColor extends Point {
    private int z;
    private String color;

    public Point3DColor(int x, int y, int z, String color) {
        super(x, y);
        this.z = z;
        this.color = color;
    }

    public void move(Point3DColor p) {
        super.move(p.getX(), p.getY());
        this.z = p.z;
        this.color = p.color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Point3DColor)) {
            return false;
        }
        Point3DColor other = (Point3DColor) obj;
        if (this.getX() == other.getX() && this.getY() == other.getY() 
            && this.z == other.z && this.color.equals(other.color)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + "," + this.z + ")" + this.color + "점";
    }

    public static void main(String[] args) {
        Point3DColor p = new Point3DColor(10, 20, 30, "RED");
        System.out.println(p.toString() + "입니다.");

        Point3DColor q = new Point3DColor(1, 2, 3, "BLUE");
        p.move(q);
        System.out.println(p.toString() + "입니다.");
        
        if (p.equals(q)) {
            System.out.println("같은 위치 색의 점입니다.");
        } else {
            System.out.println("아니오");
        }
    }
}
