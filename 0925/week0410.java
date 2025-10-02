package intro;

public class week0410 {
    int height, width;

    double getArea() {
        return 0.5 * height * width;
    }

    public static void main(String[] args) {
        week0410 t1 = new week0410();
        t1.height = 10;
        t1.width = 20;
        System.out.println("삼각형의 면적 = " + t1.getArea());
    }
}