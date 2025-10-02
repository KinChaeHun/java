public class week0403 {
    public static void main(String[] args) {
        Circle pizza = new intro.Circle(5);

        interface(pizza);
        System.out.println(pizza.radius);
    }
    public static void changeCircle(Circle c) {
        c.radius = 10;
    }
}
