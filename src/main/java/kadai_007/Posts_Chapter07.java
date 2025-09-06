package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
    public static void main(String[] args) {
        // 1) DB接続情報
        String url = "jdbc:mysql://localhost:3306/challenge_java"
                   + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo";
        String user = "root";
        String password = "hayato0523";

        // 2) SQL文
       
        String insertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES"
                + "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13),"
                + "(1002, '2023-02-08', 'お疲れ様です！', 12),"
                + "(1003, '2023-02-09', '今日も頑張ります！', 18),"
                + "(1001, '2023-02-09', '無理は禁物ですよ！', 17),"
                + "(1002, '2023-02-10', '明日から連休ですね！', 20);";

        String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";

        // 3) DB処理
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement()) {

            System.out.println("データベース接続成功：" + con);
            
            stmt.executeUpdate("TRUNCATE TABLE posts");

            // データ追加
            System.out.println("レコード追加を実行します");
            int rows = stmt.executeUpdate(insertSql);
            System.out.println(rows + "件のレコードが追加されました");

            // データ検索
            ResultSet rs = stmt.executeQuery(selectSql);
            System.out.println("ユーザーIDが1002のレコードを検索しました");

            int count = 1;
            while (rs.next()) {
                Date postedAt = rs.getDate("posted_at");
                String content = rs.getString("post_content");
                int likes = rs.getInt("likes");

                System.out.printf("%d件目：投稿日時=%s／投稿内容=%s／いいね数=%d%n",
                        count, postedAt, content, likes);
                count++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
