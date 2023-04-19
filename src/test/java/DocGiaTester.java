
import com.mycompany.pojo.DocGia;
import com.mycompany.services.DocGiaService;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
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
public class DocGiaTester {

    @Test
    public void testGetDocGias() throws SQLException {
        DocGiaService g = new DocGiaService();
        List<DocGia> docGiaList = g.getDocGias("");

        //Kiem tra xem List Doc Gia co tra ve "10"  Doc Gia hay khong
        Assertions.assertEquals(9, docGiaList.size());
    }

    @Test
    public void testDeleteDocGia() throws SQLException {
        DocGiaService g = new DocGiaService();
        DocGia dg = new DocGia("BAO01");
        boolean result = g.deleteDocGia(dg);
        Assertions.assertTrue(result);
        
        List<DocGia> docGiaList = g.getDocGias("");
        Assertions.assertEquals(8, docGiaList.size());
    }
    
     @Test
    public void testInsert() throws SQLException {
        DocGiaService g = new DocGiaService();
        DocGia dg = new DocGia("BA0123", "Dang Khoa", "Nam", Date.valueOf("2002-06-23"), "Khoa CNTT", Date.valueOf("2002-06-23"), "deekay02@gmail.com", "HCM", "012937824");
        g.addDocGia(dg);
        List<DocGia> docGiaList = g.getDocGias("");
        Assertions.assertEquals(9, docGiaList.size());
    }
    
    @Test
    public void testUpdate() throws SQLException {
        DocGiaService g = new DocGiaService();
        DocGia dg = new DocGia("BA0123", "Tran Dang Khoa", "Nam", Date.valueOf("2002-06-23"), "Khoa CNTT", Date.valueOf("2002-06-23"), "deekay02@gmail.com", "HCM", "0999999999");
        g.updateDocGia(dg);
        List<DocGia> docGiaList = g.getDocGias("");
        Assertions.assertEquals("0999999999", docGiaList.get(0).getPhoneNumber());
    }
}
