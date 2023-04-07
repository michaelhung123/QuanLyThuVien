/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.mycompany.pojo.BoPhan;
import com.mycompany.pojo.DocGia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dell
 */
public class DocGiaService {

    public boolean addDocGia(DocGia dg) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "INSERT INTO docgia(id,name,gioitinh,ngaysinh,bophan,email,diachi,phonenumber) VALUES (?,?,?,?,?,?,?,?)"; // SQL injection
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, dg.getId());
            stm.setString(2, dg.getName());
            stm.setString(3, dg.getGioiTinh());
            stm.setDate(4, dg.getNgaySinh());
            stm.setString(5, dg.getBoPhan());
            stm.setString(6, dg.getEmail());
            stm.setString(7, dg.getDiaChi());
            stm.setString(8, dg.getPhoneNumber());
            int r = stm.executeUpdate();
            return r > 0;
        }
    }

    public boolean deleteDocGia(DocGia dg) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM docgia WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, dg.getId());
            int r = stm.executeUpdate();

            return r > 0;
        }
    }

    public void updateDocGia(DocGia dg) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE docgia SET name=?, gioitinh=? , ngaysinh=?, bophan=?,email=?,diachi=?,phonenumber=? WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, dg.getName());
            stm.setString(2, dg.getGioiTinh());
            stm.setDate(3, dg.getNgaySinh());
            stm.setString(4, dg.getBoPhan());
            stm.setString(5, dg.getEmail());
            stm.setString(6, dg.getDiaChi());
            stm.setString(7, dg.getPhoneNumber());
            stm.setString(8, dg.getId());

            stm.executeUpdate();
        }
    }

    public List<BoPhan> getBoPhan() throws SQLException {
        List<BoPhan> boPhanList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("SELECT * FROM bophan");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                boPhanList.add(new BoPhan(id, name));
            }
        }
        return boPhanList;
    }

    public List<DocGia> getDocGias(String kw) throws SQLException {
        List<DocGia> DocGiaList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM docgia";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE name like concat('%',?,'%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String gioitinh = rs.getString("gioitinh");
                Date ngaysinh = rs.getDate("ngaysinh");
                String bophan = rs.getString("bophan");
                Date create_time = rs.getDate("create_time");
                String email = rs.getString("email");
                String diachi = rs.getString("diachi");
                String phonenumber = rs.getString("phonenumber");
                DocGiaList.add(new DocGia(id, name, gioitinh, ngaysinh, bophan, create_time, email, diachi, phonenumber));
            }
        }
        return DocGiaList;
    }
    
    public List<DocGia> getIdDocGia(String kw) throws SQLException {
        List<DocGia> docgias = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
             String sql = "SELECT * FROM docgia";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE bophan like concat('%',?,'%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                docgias.add(new DocGia(id));
            }
        }
        return docgias;
    }
}
