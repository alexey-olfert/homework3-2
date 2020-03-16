package tests;

import data.DBUtils;
import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;


public class LoginTest {

    @Test
    void stubTest() throws SQLException {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify(DBUtils.getVerificationCode());
    }

    @AfterAll
    static void clearTables() throws SQLException {
        DBUtils.clearTables();
    }
}



