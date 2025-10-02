// [1~2] 다음 TV 클래스가 있다.
/*
    class TV {
        private int size;
        public TV(int size) { this.size = size; }
        protected int getSize() { return size; }
    }
*/
// 1. 다음 main() 메소드와 실행 결과를 참고하여 TV를 상속받은 ColorTV 클래스를 작성하라.
/*
    65인치 65536컬러
*/

class TV {
    private int size;
    public TV(int size) { this.size = size; }
    protected int getSize() { return size; }
}

public class ColorTV extends TV {
    private int colors;

    public ColorTV(int size, int colors) {
        super(size);
        this.colors = colors;
    }

    public void printProperty() {
        System.out.println(getSize() + "인치 " + this.colors + "컬러");
    }

    public static void main(String[] args) {
        ColorTV myTV = new ColorTV(65, 65536);
        myTV.printProperty();
    }
}
