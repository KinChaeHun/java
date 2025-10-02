// 2. Cube는 직육면체를 표현하는 클래스이다. 다음 main() 메소드와 실행 결과를 참고하여
//    Cube 클래스를 작성하라.
/*
    큐브의 부피는 6
    큐브의 부피는 48
    큐브의 부피는 0이 아님
*/

public class Cube {
    private int width;
    private int height;
    private int depth;

    public Cube(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public int getVolume() {
        return this.width * this.height * this.depth;
    }

    public void increase(int w, int h, int d) {
        this.width += w;
        this.height += h;
        this.depth += d;
    }

    public boolean isZero() {
        return (getVolume() == 0);
    }

    public static void main(String[] args) {
        Cube cube = new Cube(1, 2, 3);
        System.out.println("큐브의 부피는 " + cube.getVolume());

        cube.increase(1, 2, 3);
        System.out.println("큐브의 부피는 " + cube.getVolume());

        if (cube.isZero()) {
            System.out.println("큐브의 부피는 0");
        } else {
            System.out.println("큐브의 부피는 0이 아님");
        }
    }
}
