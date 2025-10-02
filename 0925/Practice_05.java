// 5. 노래 한 곡을 나타내는 Song 클래스를 작성하라. Song 클래스의 필드는 다음과 같다.
//    - 노래의 제목 title
//    - 가수 이름 singer
//    - 발표 년도 year
//    - 가수 나라 lang
//    또한 Song 클래스는 다음 생성자와 메소드를 갖는다.
//    - 생성자: 제목, 가수, 년도, 나라를 매개변수로 받아 필드를 초기화
//    - 노래 정보를 출력하는 show() 메소드
//    main() 메소드는 "가로수 그늘 아래 서면", "이문세", "한국", 1988을 매개변수로 하여 Song 객체를 생성하고,
//    이 객체의 show()를 호출하여 노래 정보를 출력한다.
/*
    1988년 한국의 이문세가 부른 가로수 그늘 아래 서면
*/

public class Song {
    private String title;
    private String singer;
    private int year;
    private String lang;

    public Song(String title, String singer, String lang, int year) {
        this.title = title;
        this.singer = singer;
        this.lang = lang;
        this.year = year;
    }

    public void show() {
        System.out.println(this.year + "년 " + this.lang + "의 " + this.singer + "가 부른 " + this.title);
    }

    public static void main(String[] args) {
        Song song = new Song("가로수 그늘 아래 서면", "이문세", "한국", 1988);
        song.show();
    }
}
