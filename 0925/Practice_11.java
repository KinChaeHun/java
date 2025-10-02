// 11. 다음 2개의 static 속성을 가진 ArrayUtil 클래스를 만들어보자. 다음 코드와 실행 결과를
//     참고하여 concat()와 print()를 작성하여 ArrayUtil 클래스를 완성하라.
/*
    [ 1 5 7 9 3 6 -1 100 77 ]
*/

class ArrayUtil {
    public static int[] concat(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i];
        }
        
        for (int i = 0; i < b.length; i++) {
            result[a.length + i] = b[i];
        }
        
        return result;
    }

    public static void print(int[] a) {
        System.out.print("[ ");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.print("]");
    }
}

public class StaticEx {
    public static void main(String[] args) {
        int[] array1 = { 1, 5, 7, 9 };
        int[] array2 = { 3, 6, -1, 100, 77 };
        int[] array3 = ArrayUtil.concat(array1, array2);
        ArrayUtil.print(array3);
    }
}
