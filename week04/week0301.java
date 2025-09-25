// 1. 다음 프로그램에 대해 물음에 답하라.

/* int sum=0, i=1;
while (true) {
    if( i > 50)
        break;
    sum = sum + i;
    i = i + 3;
}
System.out.println(sum); */


public class week0301 {

    /*
     * (1) 무엇을 계산하는 코드인가? 실행 결과와 출력되는 내용은?
     *
     * - 계산 내용: 1부터 시작하여 3씩 증가하는 정수들을 50이 넘기 전까지 더하는 코드.
     * (즉, 1 + 4 + 7 + ... + 49의 합계를 계산.)
     * - 실행 결과 및 출력: 425
     */

    // (2) 위의 코드를 main() 메소드로 만들고 WhileLoop 클래스로 완성하라.
    static class WhileLoop {
        public static void main(String[] args) {
            int sum = 0, i = 1;
            while (true) {
                if (i > 50)
                    break;
                sum = sum + i;
                i = i + 3;
            }
            System.out.println("WhileLoop 결과: " + sum);
        }
    }

    // (3) for 문을 이용하여 동일하게 실행되는 ForLoop 클래스를 작성하라.
    static class ForLoop {
        public static void main(String[] args) {
            int sum = 0;
            for (int i = 1; i <= 50; i += 3) {
                sum = sum + i;
            }
            System.out.println("ForLoop 결과: " + sum);
        }
    }

    // (4) do-while 문을 이용하여 동일하게 실행되는 DoWhileLoop 클래스를 작성하라.
    static class DoWhileLoop {
        public static void main(String[] args) {
            int sum = 0;
            int i = 1;
            do {
                sum = sum + i;
                i = i + 3;
            } while (i <= 50);
            System.out.println("DoWhileLoop 결과: " + sum);
        }
    }

    // 한번에 모든 클래스의 실행 결과를 확인하기 위한 main 메소드
    public static void main(String[] args) {
        System.out.println("--- 1번 문제 실행 결과 ---");
        WhileLoop.main(args);
        ForLoop.main(args);
        DoWhileLoop.main(args);
    }
}