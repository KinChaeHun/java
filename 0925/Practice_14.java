// 14. 배열은 정해진 크기 이상 저장할 수 없는 한계가 있다. 이 한계를 해소하는 가변 배열 클래스 Varray를
//     만들어보자. Varray는 생성자를 통해 전달받은 크기 내부에 배열을 만들다. Varray는 배열 크기를 넘어
//     계속 저장되면 내부 배열의 크기를 2배로 늘린다. Varray의 add()는 배열의 마지막 원소로 저장하며,
//     insert()의 첫 번째 매개변수인 index 값은 현재 배열에 저장된 크기를 넘을 수 없다. 독자들은 다음
//     main() 메소드를 가진 VarrayEx 클래스를 작성하고 실행 예시를 참고하여 Varray 클래스를 완성하라.
/*
    용량: 5, 저장된 개수: 0
    용량: 10, 저장된 개수: 7
    0 1 2 3 4 5 6
    용량: 10, 저장된 개수: 8
    0 1 2 3 4 200 5 6
    용량: 10, 저장된 개수: 8
    0 1 2 3 4 200 5 6
    용량: 20, 저장된 개수: 13
    0 1 2 3 4 200 5 6 50 51 52 53 54
*/

class Varray {
    private int[] arr;
    private int size;

    public Varray(int capacity) {
        this.arr = new int[capacity];
        this.size = 0;
    }

    private void resize() {
        if (this.size == this.arr.length) {
            int[] newArr = new int[this.arr.length * 2];
            for (int i = 0; i < this.size; i++) {
                newArr[i] = this.arr[i];
            }
            this.arr = newArr;
        }
    }

    public void add(int value) {
        resize();
        this.arr[this.size++] = value;
    }

    public void insert(int index, int value) {
        if (index > this.size || index < 0) {
            return;
        }
        resize();
        for (int i = this.size - 1; i >= index; i--) {
            this.arr[i + 1] = this.arr[i];
        }
        this.arr[index] = value;
        this.size++;
    }

    public void remove(int value) {
        int targetIndex = -1;
        for (int i = 0; i < this.size; i++) {
            if (this.arr[i] == value) {
                targetIndex = i;
                break;
            }
        }
        if (targetIndex != -1) {
            for (int i = targetIndex; i < this.size - 1; i++) {
                this.arr[i] = this.arr[i + 1];
            }
            this.size--;
        }
    }

    public int capacity() {
        return this.arr.length;
    }

    public int size() {
        return this.size;
    }

    public void printAll() {
        for (int i = 0; i < this.size; i++) {
            System.out.print(this.arr[i] + " ");
        }
        System.out.println();
    }
}

public class VarrayEx {
    public static void main(String[] args) {
        Varray v = new Varray(5);
        System.out.println("용량: " + v.capacity() + ", 저장된 개수: " + v.size());

        for (int i = 0; i < 7; i++) {
            v.add(i);
        }
        System.out.println("용량: " + v.capacity() + ", 저장된 개수: " + v.size());
        v.printAll();

        v.insert(5, 200);
        System.out.println("용량: " + v.capacity() + ", 저장된 개수: " + v.size());
        v.printAll();

        v.remove(10);
        System.out.println("용량: " + v.capacity() + ", 저장된 개수: " + v.size());
        v.printAll();

        for (int i = 50; i < 55; i++) {
            v.add(i);
        }
        System.out.println("용량: " + v.capacity() + ", 저장된 개수: " + v.size());
        v.printAll();
    }
}
