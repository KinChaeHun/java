// 8. 커피 자판기 프로그램을 작성하고자 한다. 다음은 박스를 표현하는 추상클래스 Box의 코드이다.
//    Box 상속받아 자판기 커피의 각 재료를 담을 수 있는 IngredientBox 클래스를 작성하라.
//    커피, 프림, 설탕을 각각 담는 3개의 IngredientBox 객체를 생성하고 다음 실행 결과와 같이
//    작동하는 커피 자판기 프로그램을 완성하라.
/*
    *****청춘 커피 자판기 입니다.*****
    커피 *****
    프림 *****
    설탕 *****
    다방커피:1, 설탕 커피:2, 블랙 커피:3, 종료:4>>1
    커피 ****
    프림 ****
    설탕 ****
    다방커피:1, 설탕 커피:2, 블랙 커피:3, 종료:4>>2
    커피 ***
    프림 ****
    설탕 ***
    다방커피:1, 설탕 커피:2, 블랙 커피:3, 종료:4>>3
    커피 **
    프림 ****
    설탕 ***
    다방커피:1, 설탕 커피:2, 블랙 커피:3, 종료:4>>1
    커피 *
    프림 ***
    설탕 **
    다방커피:1, 설탕 커피:2, 블랙 커피:3, 종료:4>>2
    커피 
    프림 ***
    설탕 *
    재료가 부족합니다.
    다방커피:1, 설탕 커피:2, 블랙 커피:3, 종료:4>>4
    청춘 커피 자판기 프로그램을 종료합니다
*/

import java.util.Scanner;

abstract class Box {
    protected int size;
    public Box(int size) { this.size = size; }
    public boolean isEmpty() { return size == 0; }
    public abstract boolean consume();
    public abstract void print();
}

class IngredientBox extends Box {
    private String name;

    public IngredientBox(String name, int size) {
        super(size);
        this.name = name;
    }

    @Override
    public boolean consume() {
        if (isEmpty()) {
            return false;
        }
        this.size--;
        return true;
    }

    @Override
    public void print() {
        System.out.print(this.name + " ");
        for (int i = 0; i < this.size; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}

public class CoffeeVendingMachine {
    private IngredientBox coffeeBox;
    private IngredientBox creamBox;
    private IngredientBox sugarBox;
    private Scanner scanner;

    public CoffeeVendingMachine() {
        this.coffeeBox = new IngredientBox("커피", 5);
        this.creamBox = new IngredientBox("프림", 5);
        this.sugarBox = new IngredientBox("설탕", 5);
        this.scanner = new Scanner(System.in);
    }

    private void printState() {
        coffeeBox.print();
        creamBox.print();
        sugarBox.print();
    }

    public void run() {
        System.out.println("*****청춘 커피 자판기 입니다.*****");
        while (true) {
            printState();
            System.out.print("다방커피:1, 설탕 커피:2, 블랙 커피:3, 종료:4>>");
            int choice = scanner.nextInt();
            boolean success = false;

            switch (choice) {
                case 1: // Dabang Coffee
                    if (!coffeeBox.isEmpty() && !creamBox.isEmpty() && !sugarBox.isEmpty()) {
                        coffeeBox.consume();
                        creamBox.consume();
                        sugarBox.consume();
                        success = true;
                    }
                    break;
                case 2: // Sugar Coffee
                    if (!coffeeBox.isEmpty() && !sugarBox.isEmpty()) {
                        coffeeBox.consume();
                        sugarBox.consume();
                        success = true;
                    }
                    break;
                case 3: // Black Coffee
                    if (!coffeeBox.isEmpty()) {
                        coffeeBox.consume();
                        success = true;
                    }
                    break;
                case 4: // Exit
                    System.out.println("청춘 커피 자판기 프로그램을 종료합니다");
                    scanner.close();
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
                    continue;
            }

            if (!success && choice >= 1 && choice <= 3) {
                System.out.println("재료가 부족합니다.");
            }
        }
    }

    public static void main(String[] args) {
        CoffeeVendingMachine vm = new CoffeeVendingMachine();
        vm.run();
    }
}
