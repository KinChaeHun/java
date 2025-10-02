// 8. Account 클래스는 1개의 은행 계좌를 나타낸다. 실행 결과와 같이 출력되도록 Account 클래스를
//    작성하라. 잔금이 인출하는 금액보다 작으면, 잔금만큼만 인출된다.
/*
    잔금은 5100원입니다.
    잔금은 6600원입니다.
    1000원 인출
    잔금은 5600원입니다.
*/

public class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }



    public void deposit(int[] amounts) {
        for (int amount : amounts) {
            this.balance += amount;
        }
    }

    public int withdraw(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return amount;
        } else {
            int withdrawnAmount = this.balance;
            this.balance = 0;
            return withdrawnAmount;
        }
    }

    public int getBalance() {
        return this.balance;
    }

    public static void main(String[] args) {
        Account a = new Account(100);
        a.deposit(5000);
        System.out.println("잔금은 " + a.getBalance() + "원입니다.");

        int bulk[] = {100, 500, 200, 700};
        a.deposit(bulk);
        System.out.println("잔금은 " + a.getBalance() + "원입니다.");

        int money = 1000;
        int wMoney = a.withdraw(money);
        System.out.println(wMoney + "원 인출");
        System.out.println("잔금은 " + a.getBalance() + "원입니다.");
    }
}
