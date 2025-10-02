// [9~10] 다음은 정수를 배열로 저장하는 클래스 BaseArray이다.
/*
    class BaseArray {
        protected int array[];
        protected int nextIndex = 0;
        public BaseArray(int size) { array = new int[size]; }
        public int length() { return array.length; }
        public void add(int n) {
            if(nextIndex == array.length) return;
            array[nextIndex] = n;
            nextIndex++;
        }
        public void print() {
            for(int n : array) System.out.print(n + " ");
            System.out.println();
        }
    }
*/
// 9. BaseArray 클래스를 상속받아 임계값(threshold)보다 크면 1, 아니면 0의 값을 가지는
//    BinaryArray를 작성하라. 다음 main() 메소드를 포함하고 실행 결과와 같이 출력되게 하라.
/*
    >>10 20 30 40 50 60 70 80 90 100
    0 0 0 0 0 1 1 1 1 1
*/

import java.util.Scanner;

class BaseArray {
    protected int array[];
    protected int nextIndex = 0;

    public BaseArray(int size) {
        array = new int[size];
    }

    public int length() {
        return array.length;
    }

    public void add(int n) {
        if (nextIndex == array.length) {
            return;
        }
        array[nextIndex] = n;
        nextIndex++;
    }

    public void print() {
        for (int i = 0; i < nextIndex; i++) {
             System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}

public class BinaryArray extends BaseArray {
    private int threshold;

    public BinaryArray(int size, int threshold) {
        super(size);
        this.threshold = threshold;
    }

    @Override
    public void add(int n) {
        if (n > this.threshold) {
            super.add(1);
        } else {
            super.add(0);
        }
    }

    public static void main(String[] args) {
        int threshold = 50;
        BinaryArray bArray = new BinaryArray(10, threshold);

        Scanner scanner = new Scanner(System.in);
        System.out.print(">>");
        for (int i = 0; i < bArray.length(); i++) {
            int n = scanner.nextInt();
            bArray.add(n);
        }

        bArray.print();
        scanner.close();
    }
}
