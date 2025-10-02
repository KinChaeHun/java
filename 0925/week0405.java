package intro;

class CircleEx {
    int radius;

    public CircleEx(int radius) {
        this.radius = radius;
    }

    public double getArea() {
        return 3.14 * radius * radius;
    }
}

public class week0405 {
    public static void main(String[] args) {
        CircleEx[] ce = new CircleEx[5];

        for (int i = 0; i < ce.length; i++) {
            ce[i] = new CircleEx(i + 1);
            System.out.println("ce[" + i + "]의 면적: " + ce[i].getArea());
        }
    }