// 15. 다음은 도형을 묘사한 Shape 인터페이스이다. 다음 main() 메소드와 실행 결과를 참고하여,
//     Shape 인터페이스를 상속받아 구현한 클래스 Circle, Oval, Rect를 작성하고 main() 메소드를
//     포함하는 ShapeEx 클래스를 작성하라.
/*
    --- 다시 그립니다. 반지름이 5인 원
    --- 다시 그립니다. 20x30에 내접하는 타원
    --- 다시 그립니다. 10x40크기의 사각형
    면적은 78.5
    면적은 471.0
    면적은 400.0
*/

interface Shape {
    final double PI = 3.14;
    void draw();
    double getArea();
    default public void redraw() {
        System.out.print("--- 다시 그립니다. ");
        draw();
    }
}

class Circle implements Shape {
    private int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("반지름이 " + this.radius + "인 원");
    }

    @Override
    public double getArea() {
        return PI * this.radius * this.radius;
    }
}

class Oval implements Shape {
    private int width;
    private int height;

    public Oval(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println(this.width + "x" + this.height + "에 내접하는 타원");
    }

    @Override
    public double getArea() {
        return PI * (this.width / 2.0) * (this.height / 2.0);
    }
}

class Rect implements Shape {
    private int width;
    private int height;

    public Rect(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println(this.width + "x" + this.height + "크기의 사각형");
    }

    @Override
    public double getArea() {
        return this.width * this.height;
    }
}


public class ShapeEx {
    public static void main(String[] args) {
        Shape[] list = new Shape[3];
        list[0] = new Circle(5);
        list[1] = new Oval(20, 30);
        list[2] = new Rect(10, 40);

        for (int i = 0; i < list.length; i++) {
            list[i].redraw();
        }

        for (int i = 0; i < list.length; i++) {
            // 예시와 유사한 출력을 위해 소수점 첫째 자리까지만 표기
            System.out.println("면적은 " + String.format("%.1f", list[i].getArea()));
        }
    }
}
