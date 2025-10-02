// 14. 텍스트로 입출력하는 간단한 그래픽 편집기를 만들어보자. 본문에 사용한 추상 클래스 Shape과
//     Line, Rect, Circle 클래스 코드를 잘 완성하고 그래픽 편집기처럼 다음 실행 예시처럼 "삽입",
//     "삭제", "모두 보기", "종료"의 4가지 그래픽 편집 기능을 가진 GraphicEditor 클래스를 작성하라.
/*
    그래픽 에디터 Beauty Graphic Editor를 실행합니다.
    삽입(1), 삭제(2), 모두 보기(3), 종료(4)>>1
    Line(1), Rect(2), Circle(3)>>1
    삽입(1), 삭제(2), 모두 보기(3), 종료(4)>>1
    Line(1), Rect(2), Circle(3)>>2
    삽입(1), 삭제(2), 모두 보기(3), 종료(4)>>3
    Line
    Rect
    삽입(1), 삭제(2), 모두 보기(3), 종료(4)>>2
    삭제할 도형의 위치>>1
    삽입(1), 삭제(2), 모두 보기(3), 종료(4)>>3
    Rect
    삽입(1), 삭제(2), 모두 보기(3), 종료(4)>>4
    Beauty Graphic Editor를 종료합니다.
*/

import java.util.Scanner;

abstract class Shape {
    private Shape next;
    public Shape() { this.next = null; }
    public void setNext(Shape obj) { this.next = obj; }
    public Shape getNext() { return this.next; }
    public abstract void draw();
}

class Line extends Shape {
    @Override
    public void draw() {
        System.out.println("Line");
    }
}

class Rect extends Shape {
    @Override
    public void draw() {
        System.out.println("Rect");
    }
}

class Circle extends Shape {
    @Override
    public void draw() {
        System.out.println("Circle");
    }
}

public class GraphicEditor {
    private Shape head, tail;
    private Scanner scanner;

    public GraphicEditor() {
        this.head = null;
        this.tail = null;
        this.scanner = new Scanner(System.in);
    }

    private void insert() {
        System.out.print("Line(1), Rect(2), Circle(3)>>");
        int type = scanner.nextInt();
        Shape newShape;
        switch (type) {
            case 1: newShape = new Line(); break;
            case 2: newShape = new Rect(); break;
            case 3: newShape = new Circle(); break;
            default: System.out.println("잘못된 입력입니다."); return;
        }

        if (head == null) {
            head = newShape;
            tail = newShape;
        } else {
            tail.setNext(newShape);
            tail = newShape;
        }
    }

    private void delete() {
        System.out.print("삭제할 도형의 위치>>");
        int index = scanner.nextInt();

        if (head == null) {
            System.out.println("삭제할 도형이 없습니다.");
            return;
        }

        if (index == 1) {
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
            return;
        }

        Shape current = head;
        Shape prev = null;
        int i = 1;
        while (current != null && i < index) {
            prev = current;
            current = current.getNext();
            i++;
        }

        if (current == null) {
            System.out.println("삭제할 수 없습니다.");
        } else {
            prev.setNext(current.getNext());
            if (current == tail) {
                tail = prev;
            }
        }
    }

    private void view() {
        Shape p = head;
        while (p != null) {
            p.draw();
            p = p.getNext();
        }
    }

    public void run() {
        System.out.println("그래픽 에디터 Beauty Graphic Editor를 실행합니다.");
        while (true) {
            System.out.print("삽입(1), 삭제(2), 모두 보기(3), 종료(4)>>");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: insert(); break;
                case 2: delete(); break;
                case 3: view(); break;
                case 4:
                    System.out.println("Beauty Graphic Editor를 종료합니다.");
                    scanner.close();
                    return;
                default: System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public static void main(String[] args) {
        GraphicEditor ge = new GraphicEditor();
        ge.run();
    }
}
