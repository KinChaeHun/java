import java.util.Random;
import java.util.Scanner;

public class openChallenge{
    public static void main(String args[]) {
        Random r = new Random();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            int pc = r.nextInt(100);
            System.out.println("수를 결정하였습니다. 맞추어보세요");

            int count = 1, max = 99, min = 0;
            String option = "";

            while(true) {
                System.out.println(min+"-"+max);
                System.out.print(count+">>");
                int user = scanner.nextInt();

                if(user>pc){
                    System.out.println("더 낮습니다");
                    max = user;
                }
                else if(user<pc) {
                    System.out.println("더 높습니다");
                    min = user;
                }
                else {
                    System.out.println("정답입니다.");
                    break;
                }
                count++;
            }
            System.out.print("다시하시겠습니까(y/n)>>");
            option = scanner.next();

            if(option.equals("y")) continue;
            else break;
        }

        System.out.print("프로그램을 종료합니다.");
        scanner.close();
        return;
    }
}