// 9. 4x4 크기의 2차원 정수 배열을 생성하고, 0~255 범위의 정수 16개를 랜덤하게 생성하여
//    저장한 후, 배열을 실행 사례와 같이 출력하라.
/*
    4x4 배열에 랜덤한 값을 저장한 후 출력합니다.
    35  42  120 12
    67  77  204 228
    19  208 58  203
    224 221 23  54
*/

public class week0309 {

    public static void main(String[] args) {
        int[][] intArray = new int[4][4];

        for (int i = 0; i < intArray.length; i++) {
            for (int j = 0; j < intArray[i].length; j++) {
                intArray[i][j] = (int) (Math.random() * 256);
            }
        }

        System.out.println("4x4 배열에 랜덤한 값을 저장한 후 출력합니다.");

        for (int i = 0; i < intArray.length; i++) {
            for (int j = 0; j < intArray[i].length; j++) {
                System.out.print(intArray[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
