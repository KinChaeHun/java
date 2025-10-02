// 13. 다음의 필드와 메소드를 가진 4개의 클래스 Add, Sub, Mul, Div를 작성하려고 한다.
//     - 필드: String errorMsg, int a, int b
//     - 메소드: setValue(int a, int b), calculate()
//     이 클래스들의 공통 멤버를 가지는 추상 클래스 Calc를 작성하고, 이를 상속받아 Add, Sub, Mul, Div를
//     구현하라. 이들을 활용하여 다음 실행 결과와 같이 사용자가 입력한 연산 명령을 실행하는 Calculator
//     클래스를 작성하라.
/*
    두 정수와 연산자를 입력하시오>>15 16 +
    결과는 31
    두 정수와 연산자를 입력하시오>>12 12 *
    결과는 144
    두 정수와 연산자를 입력하시오>>100 0 /
    0으로 나눌 수 없음. 프로그램 종료
*/

import java.util.Scanner;

abstract class Calc {
    protected String errorMsg;
    protected int a;
    protected int b;

    public void setValue(int a, int b) {
        this.a = a;
        this.b = b;
        this.errorMsg = null;
    }

    public abstract int calculate();
}

class Add extends Calc {
    @Override
    public int calculate() {
        return a + b;
    }
}

class Sub extends Calc {
    @Override
    public int calculate() {
        return a - b;
    }
}

class Mul extends Calc {
    @Override
    public int calculate() {
        return a * b;
    }
}

class Div extends Calc {
    @Override
    public int calculate() {
        if (b == 0) {
            errorMsg = "0으로 나눌 수 없음.";
            return -1; // Error code
        }
        return a / b;
    }
}


public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("두 정수와 연산자를 입력하시오>>");
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            String op = scanner.next();
            Calc calc;

            switch (op) {
                case "+":
                    calc = new Add();
                    break;
                case "-":
                    calc = new Sub();
                    break;

                case "*":
                    calc = new Mul();
                    break;
                case "/":
                    calc = new Div();
                    break;
                default:
                    System.out.println("잘못된 연산자입니다.");
                    continue;
            }

            calc.setValue(a, b);
            int result = calc.calculate();

            if (calc.errorMsg != null) {
                System.out.println(calc.errorMsg + ". 프로그램 종료");
                break;
            } else {
                System.out.println("결과는 " + result);
            }
        }
        
        scanner.close();
    }
}
