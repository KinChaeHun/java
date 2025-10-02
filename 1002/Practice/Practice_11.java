// 11. 다음 IStack 인터페이스를 상속받아 문자열을 저장하는 StringStack 클래스를 구현하라.
//     push()는 스택의 꼭대기에 문자열을 삽입하고 pop()은 스택의 꼭대기에서 문자열을 꺼내는
//     메소드이다. 그리고 StringStack 클래스를 활용하여 실행 사례와 같이 작동하는 main() 메소드를
//     가진 StackApp 클래스를 작성하라.
/*
    스택 용량>>3
    문자열 입력>>모바일
    문자열 입력>>빅데이터
    문자열 입력>>인공지능
    스택이 꽉 차서 더이상 저장 불가
    스택에 저장된 문자열 팝 : 인공지능 빅데이터 모바일
*/

import java.util.Scanner;

interface IStack {
    int capacity();
    int length();
    boolean push(String val);
    String pop();
}

class StringStack implements IStack {
    private String[] stack;
    private int top;

    public StringStack(int capacity) {
        this.stack = new String[capacity];
        this.top = -1;
    }

    @Override
    public int capacity() {
        return stack.length;
    }

    @Override
    public int length() {
        return top + 1;
    }

    @Override
    public boolean push(String val) {
        if (top == stack.length - 1) {
            return false;
        } else {
            stack[++top] = val;
            return true;
        }
    }

    @Override
    public String pop() {
        if (top == -1) {
            return null;
        } else {
            String val = stack[top--];
            return val;
        }
    }
}

public class StackApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("스택 용량>>");
        int capacity = scanner.nextInt();
        StringStack stack = new StringStack(capacity);

        while (true) {
            System.out.print("문자열 입력>>");
            String input = scanner.next();

            if (input.equals("그만")) {
                break;
            }

            if (!stack.push(input)) {
                System.out.println("스택이 꽉 차서 더이상 저장 불가");
                break;
            }
        }

        System.out.print("스택에 저장된 문자열 팝 : ");
        while (stack.length() > 0) {
            System.out.print(stack.pop() + " ");
        }
        
        System.out.println();
        scanner.close();
    }
}
