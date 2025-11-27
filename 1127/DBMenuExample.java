
import java.sql.*;
import java.util.Scanner;

public class DBMenuExample {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
        String user = "root";
        String password = "0000";

        try (Connection conn = DriverManager.getConnection(url, user, password); Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("1. Select");
                System.out.println("2. Update -> Computer");
                System.out.println("3. Insert -> \"1234\", \"Hong\", \"SW\"");
                System.out.println("4. Delete -> \"SW\"");
                System.out.println("0. Exit");
                System.out.print("선택: ");

                int choice = sc.nextInt();
                sc.nextLine(); // 줄바꿈 제거

                switch (choice) {
                    case 1: // SELECT
                        String selectSql = "SELECT id, code, name, dept FROM user";
                        try (PreparedStatement pstmt = conn.prepareStatement(selectSql); ResultSet rs = pstmt.executeQuery()) {
                            System.out.println("id | code | name | dept");
                            while (rs.next()) {
                                System.out.printf("%d | %s | %s | %s\n",
                                        rs.getInt("id"),
                                        rs.getString("code"),
                                        rs.getString("name"),
                                        rs.getString("dept"));
                            }
                        }
                        break;

                    case 2: // UPDATE -> Computer
                        String updateSql = "UPDATE user SET dept = ? WHERE dept = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                            pstmt.setString(1, "Computer");
                            pstmt.setString(2, "SW");
                            int count = pstmt.executeUpdate();
                            System.out.println(count + "개 레코드 업데이트 완료!");
                        }
                        break;

                    case 3: // INSERT
                        String insertSql = "INSERT INTO user(code, name, dept) VALUES(?, ?, ?)";
                        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                            pstmt.setString(1, "1234");
                            pstmt.setString(2, "Hong");
                            pstmt.setString(3, "SW");
                            pstmt.executeUpdate();
                            System.out.println("데이터 삽입 완료!");
                        }
                        break;

                    case 4: // DELETE
                        String deleteSql = "DELETE FROM user WHERE dept = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                            pstmt.setString(1, "SW");
                            int count = pstmt.executeUpdate();
                            System.out.println(count + "개 레코드 삭제 완료!");
                        }
                        break;

                    case 0: // EXIT
                        System.out.println("프로그램 종료!");
                        return;

                    default:
                        System.out.println("잘못된 선택입니다.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
