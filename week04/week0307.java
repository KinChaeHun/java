// 7. 정수를 10개 저장하는 배열을 만들고, 11~19 범위의 정수를 랜덤하게 생성하여 배열에 저장하라.
//    그리고 배열에 들어 있는 정수들과 평균을 출력하라.
/*
    랜덤한 정수들...16 13 17 19 12 16 14 17 16 11
    평균은 15.1
*/

public class week0307 {

    public static void main(String[] args) {
        int[] intArray = new int[10];
        double sum = 0.0;

        for (int i = 0; i < intArray.length; i++) {
            int randomNum = (int) (Math.random() * 9) + 11;
            intArray[i] = randomNum;
            sum += intArray[i];
        }

        System.out.print("랜덤한 정수들 : ");
        for (int i = 0; i < intArray.length; i++) {
            System.out.print(intArray[i] + " ");
        }
        System.out.println();

        double average = sum / intArray.length;
        System.out.println("평균은 " + average);
    }
}
