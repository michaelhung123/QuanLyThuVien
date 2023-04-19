/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.pojo.Book;
import com.mycompany.services.BookService;
import com.mycompany.services.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dell
 */
public class AddBookTester {

    private static Connection conn;
    private static BookService s = new BookService();

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(AddBookTester.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @AfterAll
    public static void afterAll() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void tesAddSuccessful() {
        Book b = new Book(1, "Quoc Hung", "DHT", "Tester", 2021, "HCM", Date.valueOf("2022-02-23"), "Tang1-K1", 1);
        try {
            boolean actual = s.addBook(b);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM book";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, b.getId());
            stm.setString(2, b.getName());
            stm.setString(3, b.getTacGia());
            stm.setString(4, b.getMoTa());
            stm.setInt(5, b.getNamXB());
            stm.setString(6, b.getNoiXB());
            stm.setDate(7, b.getNgayNhap());
            stm.setString(8, b.getViTri());
            stm.setInt(9, b.getCategoryId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            while (rs.next()) {
                Assertions.assertEquals(b.getId(), rs.getInt("id"));
                Assertions.assertEquals(b.getName(), rs.getString("name"));
                Assertions.assertEquals(b.getTacGia(), rs.getString("tacgia"));
                Assertions.assertEquals(b.getMoTa(), rs.getString("mota"));
                Assertions.assertEquals(b.getNamXB(), rs.getInt("namxb"));
                Assertions.assertEquals(b.getNgayNhap(), rs.getDate("ngaynhap"));
                Assertions.assertEquals(b.getViTri(), rs.getString("vitri_new"));
                Assertions.assertEquals(b.getCategoryId(), rs.getInt("category_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void testTimeout() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            tesAddSuccessful();
        });
    }
}
