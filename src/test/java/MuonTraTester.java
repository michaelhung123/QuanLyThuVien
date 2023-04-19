
import com.mycompany.pojo.PhieuMuon;
import com.mycompany.services.PhieuMuonService;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dell
 */
public class MuonTraTester {
   @Test
    public void testGetDocGias() throws SQLException {
        PhieuMuonService p = new PhieuMuonService();
        List<PhieuMuon> phieumuonList = p.getPhieuMuon("");

        //Kiem tra xem List Phieu Muon co tra ve "10" Phieumuon hay khong
        Assertions.assertEquals(7, phieumuonList.size());
    }

    @Test
    public void testDelete() throws SQLException {
        PhieuMuonService p = new PhieuMuonService();
        PhieuMuon pm = new PhieuMuon();
        boolean result = p.deletePhieuMuon(pm);
        Assertions.assertTrue(result);
        
        List<PhieuMuon> phieuMuonList = p.getPhieuMuon("");
        Assertions.assertEquals(6, phieuMuonList.size());
    }
    
     @Test
    public void testInsert() throws SQLException {
        PhieuMuonService p = new PhieuMuonService();
        PhieuMuon pm = new PhieuMuon(219, 7, "BA0123", 3, Date.valueOf("2002-06-23"), Date.valueOf("2002-06-30"), "Tran Dang Khoa", "CRYPTO", 0, true);
        boolean result = p.addPhieuMuon(pm);
        Assertions.assertTrue(result);
        
        List<PhieuMuon> phieuMuonList = p.getPhieuMuon("");
        Assertions.assertEquals(8, phieuMuonList.size());
    }
}
