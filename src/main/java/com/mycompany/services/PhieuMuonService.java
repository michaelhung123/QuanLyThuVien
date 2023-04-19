package com.mycompany.services;

import com.mycompany.pojo.Book;
import com.mycompany.pojo.PhieuMuon;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
public class PhieuMuonService {

    public boolean addPhieuMuon(PhieuMuon pm) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "INSERT INTO phieumuon(id,book_id,docgia_id,soluong,hantra,tienphat,active) VALUES (?,?,?,?,?,?,?)"; // SQL injection
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, pm.getId());
            stm.setInt(2, pm.getBookId());
            stm.setString(3, pm.getDocGiaId());
            stm.setInt(4, pm.getSoLuong());
            stm.setDate(5, pm.getHanTra());
            stm.setDouble(6, pm.getTienPhat());
            stm.setBoolean(7, pm.isActive());
            int r = stm.executeUpdate();
            return r > 0;
        }
    }

    public boolean deletePhieuMuon(PhieuMuon pm) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM phieumuon WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, pm.getId());
            String sql2 = "SELECT COUNT(*) FROM phieumuon";
            int r = stm.executeUpdate();
            return r > 0;
        }
    }

//    public void traPhieuMuon(int activeDaTra, int id)throws SQLException {
//        try (Connection conn = JdbcUtils.getConn()) {
//            String sql = "UPDATE phieumuon SET active = ? WHERE id=?";
//            PreparedStatement stm = conn.prepareStatement(sql);
//            stm.setInt(1, activeDaTra);
//            stm.setInt(2, id);
//            stm.executeUpdate();
//        }
//    }
    public void updateSLPhieuMuon(int soLuong, Date hanTra, int bookId, String docGiaId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE phieumuon SET soluong=?, hantra=? WHERE book_id=? AND docgia_id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, soLuong);
            stm.setDate(2, hanTra);
            stm.setInt(3, bookId);
            stm.setString(4, docGiaId);
            stm.executeUpdate();
        }
    }

    public void updateTienPhatPhieuMuon(double tienPhat, int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE phieumuon SET tienphat=? WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setDouble(1, tienPhat);
            stm.setInt(2, id);
            stm.executeUpdate();
        }
    }

    public List<PhieuMuon> getPhieuMuon(String kw) throws SQLException {
        List<PhieuMuon> phieuMuonList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM phieumuon JOIN book ON phieumuon.book_id = book.id JOIN docgia ON phieumuon.docgia_id = docgia.id WHERE phieumuon.active != 2";
            if (kw != null && !kw.isEmpty()) {
                sql += " AND docgia_id like concat('%',?,'%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int bookId = rs.getInt("book_id");
                String docgiaId = rs.getString("docgia_id");
                int soLuong = rs.getInt("soluong");
                Date ngayMuon = rs.getDate("ngaymuon");
                Date hanTra = rs.getDate("hantra");
                String tenDocGia = rs.getString("docgia.name");
                String tenSach = rs.getString("book.name");
                double tienPhat = rs.getDouble("tienphat");
                boolean active = rs.getBoolean("active");
                phieuMuonList.add(new PhieuMuon(id, bookId, docgiaId, soLuong, ngayMuon, hanTra, tenDocGia, tenSach, tienPhat, active));
            }

        }
        return phieuMuonList;
    }

//    public List<PhieuMuon> getPhieuMuonFullActive(String kw) throws SQLException {
//        List<PhieuMuon> phieuMuonList = new ArrayList<>();
//        try (Connection conn = JdbcUtils.getConn()) {
//            String sql = "SELECT * FROM phieumuon JOIN book ON phieumuon.book_id = book.id JOIN docgia ON phieumuon.docgia_id = docgia.id";
//            if (kw != null && !kw.isEmpty()) {
//                sql += " WHERE docgia_id like concat('%',?,'%')";
//            }
//            PreparedStatement stm = conn.prepareCall(sql);
//            if (kw != null && !kw.isEmpty()) {
//                stm.setString(1, kw);
//            }
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                int bookId = rs.getInt("book_id");
//                String docgiaId = rs.getString("docgia_id");
//                int soLuong = rs.getInt("soluong");
//                Date ngayMuon = rs.getDate("ngaymuon");
//                Date hanTra = rs.getDate("hantra");
//                String tenDocGia = rs.getString("docgia.name");
//                String tenSach = rs.getString("book.name");
//                double tienPhat = rs.getDouble("tienphat");
//                boolean active = rs.getBoolean("active");
//                phieuMuonList.add(new PhieuMuon(id, bookId, docgiaId, soLuong, ngayMuon, hanTra, tenDocGia, tenSach, tienPhat, active));
//            }
//        }
//        return phieuMuonList;
//    }
//

    public List<PhieuMuon> searchPhieuMuonId(String kw) throws SQLException {
        List<PhieuMuon> phieuMuonList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM phieumuon JOIN book ON phieumuon.book_id = book.id JOIN docgia ON phieumuon.docgia_id = docgia.id";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE phieumuon.id like concat('%',?,'%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int bookId = rs.getInt("book_id");
                String docgiaId = rs.getString("docgia_id");
                int soLuong = rs.getInt("soluong");
                Date ngayMuon = rs.getDate("ngaymuon");
                Date hanTra = rs.getDate("hantra");
                String tenDocGia = rs.getString("docgia.name");
                String tenSach = rs.getString("book.name");
                double tienPhat = rs.getDouble("tienphat");
                boolean active = rs.getBoolean("active");
                phieuMuonList.add(new PhieuMuon(id, bookId, docgiaId, soLuong, ngayMuon, hanTra, tenDocGia, tenSach, tienPhat, active));
            }
        }
        return phieuMuonList;
    }
    public boolean checkSoLuongPhieuMuon(String docgia_id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT SUM(soluong) FROM phieumuon WHERE docgia_id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, docgia_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int total = rs.getInt(1);
                System.out.println(total);
                System.out.println("XAM NHAP VAO HAM CHECKSOLUONG");
                if (total > 4) {
                    System.out.println("XAM NHAP VAO dieu kien true");
                    return true;
                }
            }
        }
        return false;
    }

    public int getActiveDangMuon() throws SQLException {
        int total = 0;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT COUNT(active) FROM phieumuon WHERE active=1";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
               total = rs.getInt(1);
            }
        }
        return total;
    }
    
    public int getActiveDatTruoc() throws SQLException {
        int total = 0;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT COUNT(active) FROM phieumuon WHERE active=0";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
               total = rs.getInt(1);
            }
        }
        return total;
    }
    
    public int getActiveTotal() throws SQLException {
        int total = 0;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT COUNT(active) FROM phieumuon";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
               total = rs.getInt(1);
            }
        }
        return total;
    }
    
    public int getTotalPhieuMuon() throws SQLException{
        int total = 0;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT COUNT(id) FROM phieumuon";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
               total = rs.getInt(1);
            }
        }
        return total;
    }


}
