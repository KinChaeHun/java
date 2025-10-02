package intro;

public class week0404 {
    int radius;
    String name;

    public week0404() {
    }

    double getArea() {
        return 3.14 * radius * radius;
    }

    public static void main(String[] args){
        week0404 c1 = new week0404();
        c1.radius = 5;
        double c1Area = c1.getArea();
        System.out.println("원의 면적 = " + c1Area);
    }
}