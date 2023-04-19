/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.dht.utils.MessageBox;
import com.mycompany.pojo.Book;
import com.mycompany.pojo.Category;
import com.mycompany.pojo.ViTri;
import com.mycompany.qlthuvien.AddBookController;
import com.mycompany.services.BookService;
import com.mycompany.services.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
        Book b = new Book(1, "Quoc Hung", "DHT", "Tester", 2021, "HCM", Date.valueOf("23/02/2022"), "Tang1-K1", 1);
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
                Assertions.assertEquals(b.getId(), rs.getString("name"));
                Assertions.assertEquals(b.getId(), rs.getString("tacgia"));
                Assertions.assertEquals(b.getId(), rs.getString("mota"));
                Assertions.assertEquals(b.getId(), rs.getInt("namxb"));
                Assertions.assertEquals(b.getId(), rs.getDate("ngaynhap"));
                Assertions.assertEquals(b.getId(), rs.getString("vitri_new"));
                Assertions.assertEquals(b.getId(), rs.getInt("category_id"));
                Assertions.assertEquals(b.getId(), rs.getInt("id"));
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
