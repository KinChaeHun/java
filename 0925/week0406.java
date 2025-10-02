public class week0406 {
    int radius;
    String name;

    double getArea() {
        return 3.14 * radius * radius;
    }

    public week0406() {}

    public week0406(int radius, String name) {
        this.radius = radius;
        this.name = name;
    }

    public static void main(String[] args) {
        week0406 p1 = new week0406(5, "청담피자");
        week0406 p2 = new week0406();

        p1.radius = 5;
        p2.radius = 10;

        p1.name = "청담피자";
        p2.name = "도미노피자";

        System.out.println(p1.name + "의 크기는 = " + p1.getArea());
        System.out.println(p2.name + "의 크기는 = " + p2.getArea());
    }
}