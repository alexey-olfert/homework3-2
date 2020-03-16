package data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

   public static String getVerificationCode() throws SQLException {
        val codesSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");) {
            val code = runner.query(conn, codesSQL, new ScalarHandler<>());
            return code.toString();
        }
   }

   public static void clearTables() throws SQLException {
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");) {
            runner.update(conn, "DELETE FROM cards;");
            runner.update(conn, "DELETE FROM card_transactions;");
            runner.update(conn, "DELETE FROM auth_codes;");
            runner.update(conn, "DELETE FROM users;");
        }
   }

}
