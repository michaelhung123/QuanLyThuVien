
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dell
 */
public class ConnectionTester {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlythuvien";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "kiet741852963";
    private static Connection conn;

    @BeforeAll
    public static void openConn() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw  new ArithmeticException();
        }
    }

    @AfterAll
    public static void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw  new ArithmeticException();
        }
    }

    @Test
    public void testSqlConnection() {
        try {
            Assertions.assertTrue(conn.isValid(5));
            Assertions.assertFalse(conn.isClosed());
        } catch (SQLException e) {
            throw  new ArithmeticException();
        }
    }
}
