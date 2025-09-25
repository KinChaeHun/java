// 2. 다음 프로그램에 대해 물음에 답하라.
/*
int n[] = {1, -2, 6, 20, 5, 72, -16, 256};
for (int i=0; i<n.length; i++) {
    if(n[i] > 0 && n[i] % 4 == 0) {
        System.out.print(n[i] + " ");
    }
}
*/

public class week0302 {

    /*
     * (1) 무엇을 계산하는 코드인가? 실행 결과와 출력되는 내용은?
     *
     * - 계산 내용: 정수 배열 n에 저장된 값 중에서 '양수이면서 동시에 4의 배수'인 숫자들만 골라서 출력.
     * - 실행 결과 및 출력: 20 72 256
     */

    // (2) 위의 코드를 main() 메소드로 만들고 ForLoopArray 클래스로 완성하라.
    static class ForLoopArray {
        public static void main(String[] args) {
            int n[] = {1, -2, 6, 20, 5, 72, -16, 256};
            System.out.print("ForLoopArray 결과: ");
            for (int i = 0; i < n.length; i++) {
                if (n[i] > 0 && n[i] % 4 == 0) {
                    System.out.print(n[i] + " ");
                }
            }
            System.out.println(); 
        }
    }

    // (3) while 문을 이용하여 동일하게 실행되는 WhileLoopArray 클래스를 작성하라.
    static class WhileLoopArray {
        public static void main(String[] args) {
            int n[] = {1, -2, 6, 20, 5, 72, -16, 256};
            System.out.print("WhileLoopArray 결과: ");
            int i = 0;
            while (i < n.length) {
                if (n[i] > 0 && n[i] % 4 == 0) {
                    System.out.print(n[i] + " ");
                }
                i++;
            }
            System.out.println(); 
        }
    }

    // (4) do-while 문을 이용하여 동일하게 실행되는 DoWhileLoopArray 클래스를 작성하라.
    static class DoWhileLoopArray {
        public static void main(String[] args) {
            int n[] = {1, -2, 6, 20, 5, 72, -16, 256};
            System.out.print("DoWhileLoopArray 결과: ");
            if (n.length > 0) {
                int i = 0;
                do {
                    if (n[i] > 0 && n[i] % 4 == 0) {
                        System.out.print(n[i] + " ");
                    }
                    i++;
                } while (i < n.length);
            }
            System.out.println(); 
        }
    }

    public static void main(String[] args) {
        System.out.println("--- 2번 문제 실행 결과 ---");
        ForLoopArray.main(args);
        WhileLoopArray.main(args);
        DoWhileLoopArray.main(args);
    }
}
