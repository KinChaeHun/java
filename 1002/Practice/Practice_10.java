// 10. BaseArray를 상속받아 값이 큰 순서로 배열을 항상 유지하는 SortedArray를 작성하라.
//     다음 main() 메소드를 포함하고 실행 결과와 같이 출력되게 하라.
/*
    >>10 20 30 40 50 13 24 35 46 31
    10 13 20 24 30 31 35 40 46 50
*/

import java.util.Arrays;
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

public class SortedArray extends BaseArray {
    public SortedArray(int size) {
        super(size);
    }

    @Override
    public void add(int n) {
        super.add(n);
        Arrays.sort(this.array, 0, this.nextIndex);
    }

    public static void main(String[] args) {
        SortedArray sArray = new SortedArray(10);
        Scanner scanner = new Scanner(System.in);
        System.out.print(">>");

        for (int i = 0; i < sArray.length(); i++) {
            int n = scanner.nextInt();
            sArray.add(n);
        }

        sArray.print();
        scanner.close();
    }
}
