package tests;

import data.DataHelper;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    @Test
    void stubTest() throws SQLException {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val codesSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();

        try (val conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/app", "app", "pass");)
        {
            val code = runner.query(conn, codesSQL, new ScalarHandler<>());
            val verificationCode = code.toString();
            verificationPage.validVerify(verificationCode);
            runner.update(conn, "SET FOREIGN_KEY_CHECKS=0;");
            runner.update(conn, "DELETE FROM users");
            runner.update(conn, "DELETE FROM auth_codes");            ;
        }

    }


}
