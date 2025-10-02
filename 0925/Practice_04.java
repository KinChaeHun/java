// 4. 다음 main() 메소드는 Average 클래스를 이용하여 정수를 저장하고 평균을 구하여 출력한다.
//    이 코드와 실행 결과를 참고하여 Average 클래스를 작성하라.
//    Average 클래스는 최대 10개까지만 정수를 저장할 수 있다.
/*
    ***** 저장된 데이터 모두 출력 *****
    10 15 100
    평균은 41.666666666666664
*/

public class Average {
    private int[] nums;
    private int count;

    public Average() {
        this.nums = new int[10];
        this.count = 0;
    }

    public void put(int num) {
        if (this.count < this.nums.length) {
            this.nums[this.count] = num;
            this.count++;
        }
    }

    public void showAll() {
        System.out.println("***** 저장된 데이터 모두 출력 *****");
        for (int i = 0; i < this.count; i++) {
            System.out.print(this.nums[i] + " ");
        }
        System.out.println();
    }

    public double getAvg() {
        if (this.count == 0) {
            return 0.0;
        }
        int sum = 0;
        for (int i = 0; i < this.count; i++) {
            sum += this.nums[i];
        }
        return (double) sum / this.count;
    }

    public static void main(String[] args) {
        Average avg = new Average();
        avg.put(10);
        avg.put(15);
        avg.put(100);
        avg.showAll();
        System.out.println("평균은 " + avg.getAvg());
    }
}
