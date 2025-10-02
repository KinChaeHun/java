public class week0401 {
    String title, author;

    public Book(String t) {
        this(t, "작가 미상");
    }

    public Book(String t, String a) {
        title = t;
        author = a;
    }

    @Override
    public String toString() {
        return "제목: " + title + ", 저자: " + author;
    }

    public static void main(String[] args) {
        Book littleprince = new Book("어린왕자", "생떼쥐베리");
        Book lovestory = new Book("춘향전");

        System.out.println(littleprince);
        System.out.println(lovestory);
    }
}
