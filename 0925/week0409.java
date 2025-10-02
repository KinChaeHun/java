public class week0409 {
    int a, b, c, d;

    public week0409(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public week0409(int a, int b, int c) {
        this(a, b, c, 10);
    }

    public week0409(int a, int b) {
        this(a, b, 20, 10);
    }

    public week0409(int a) {
        this(a, 30, 20, 10);
    }

    public week0409() {
        this(40, 30, 20, 10);
    }

    public static void main(String[] args) {
        week0409 testArg = new week0409();
        System.out.println(testArg.a);

        int[] a = new int[5];
        a[0] = 1;

        week0409[] c = new week0409[5];
        c[0] = new week0409();
    }
}