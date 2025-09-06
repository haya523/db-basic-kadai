package kadai_004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Employees_Chapter04 {
    public static void main(String[] args) {
        // 1) 接続情報
        String url = "jdbc:mysql://localhost:3306/challenge_java"
                   + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo";
        String user = "root";
        String password = "hayato0523"; 

        // 2) CREATE TABLE 文
        String createTableSql =
                "CREATE TABLE IF NOT EXISTS employees ("
              + "  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
              + "  name VARCHAR(60) NOT NULL,"
              + "  email VARCHAR(255) NOT NULL,"
              + "  age INT(11),"
              + "  address VARCHAR(255)"
              + ");";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement()) {

            System.out.println("データベース接続成功：" + con);

            int result = stmt.executeUpdate(createTableSql);
            System.out.println("社員テーブルを作成しました: 更新レコード数=" + result);

        } catch (SQLException e) {
            System.err.println("エラーが発生しました: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
