package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Scores_Chapter10 {
    public static void main(String[] args) {
        // 接続情報（パスワードは自分のものに）
        String url = "jdbc:mysql://localhost:3306/challenge_java"
                   + "?useSSL=false&allowPublicKeyRetrieval=true"
                   + "&serverTimezone=Asia/Tokyo&useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String password = "hayato0523";

        // ① id=5（武者小路勇気）の点数を更新（0→95,80）
        String updateSql = "UPDATE scores SET score_math=?, score_english=? WHERE id=?";

        // ② 数学→英語の降順で並べ替えて全件取得
        String selectSql = "SELECT id, name, score_math, score_english "
                         + "FROM scores "
                         + "ORDER BY score_math DESC, score_english DESC, id ASC";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("データベース接続成功：" + con);

            System.out.println("レコード更新を実行します");
            try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                ps.setInt(1, 95); // math
                ps.setInt(2, 80); // english
                ps.setInt(3, 5);  // id=5 を更新
                int updated = ps.executeUpdate();
                System.out.println(updated + "件のレコードが更新されました");
            }

            System.out.println("数学・英語の点数が高い順に並べ替えました");
            try (PreparedStatement ps2 = con.prepareStatement(selectSql);
                 ResultSet rs = ps2.executeQuery()) {
                int n = 1;
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int m = rs.getInt("score_math");
                    int e = rs.getInt("score_english");
                    System.out.printf("%d件目：生徒ID=%d／氏名=%s／数学=%d／英語=%d%n",
                            n, id, name, m, e);
                    n++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
