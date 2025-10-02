// 6. 다음 멤버를 가지고 사각형을 표현하는 Rectangle 클래스를 작성하라.
//    - int 타입의 x, y, width, height 필드
//    - x, y, width, height 값을 매개변수로 받아 필드를 초기화하는 생성자
//    - 정사각형인지 아닌지 판별하는 isSquare() 메소드
//    - 매개변수로 받은 Rectangle r이 현 사각형 안에 있으면 true를 리턴하는 contains() 메소드
//    - main() 메소드와 실행 결과는 다음과 같다.
/*
    (3,3)에서 크기가 6x6인 사각형
    a는 정사각형입니다.
    a는 b를 포함합니다.
*/

public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void show() {
        System.out.println("(" + x + "," + y + ")에서 크기가 " + width + "x" + height + "인 사각형");
    }

    public boolean isSquare() {
        return (width == height);
    }

    public boolean contains(Rectangle r) {
        if (this.x < r.x && this.y < r.y
                && (this.x + this.width) > (r.x + r.width)
                && (this.y + this.height) > (r.y + r.height)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Rectangle a = new Rectangle(3, 3, 6, 6);
        Rectangle b = new Rectangle(4, 4, 2, 3);

        a.show();
        if (a.isSquare()) {
            System.out.println("a는 정사각형입니다.");
        } else {
            System.out.println("a는 직사각형입니다.");
        }

        if (a.contains(b)) {
            System.out.println("a는 b를 포함합니다.");
        }
    }
}
