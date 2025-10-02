// 7. 1개의 메모 정보를 담는 Memo 클래스를 작성하라. Memo는 생성자를 비롯하여 다음과 같은
//    필드와 메소드를 가진다.
//    - 필드: name, time, content
//    - isSameName(), show(), length(), getName() 메소드
/*
    김승겸, 10:10 자바 과제 있음
    다른 사람입니다.
    김승겸가 작성한 메모의 길이는 14
*/

public class Memo {
    private String name;
    private String time;
    private String content;

    public Memo(String name, String time, String content) {
        this.name = name;
        this.time = time;
        this.content = content;
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public void show() {
        System.out.println(this.name + ", " + this.time + " " + this.content);
    }

    public int length() {
        return this.content.length();
    }

    public String getName() {
        return this.name;
    }

    public static void main(String[] args) {
        Memo a = new Memo("김승겸", "10:10", "자바 과제 있음");
        Memo b = new Memo("박채원", "10:10", "자카르타로 어학 연수가요!");
        Memo c = new Memo("김승겸", "11:30", "사랑하는 사람이 생겼어요.");

        a.show();
        if (a.isSameName(b.getName())) {
            System.out.println("동일한 사람입니다.");
        } else {
            System.out.println("다른 사람입니다.");
        }

        System.out.println(c.getName() + "가 작성한 메모의 길이는 " + c.length());
    }
}
