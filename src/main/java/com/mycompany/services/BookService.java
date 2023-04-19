/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.mycompany.pojo.Book;
import com.mycompany.pojo.ViTri;
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
public class BookService {

    public boolean addBook(Book b) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "INSERT INTO book(id,name,tacgia,mota,namxb,noixb,ngaynhap,vitri_new,category_id) VALUES (?,?,?,?,?,?,?,?,?)"; // SQL injection
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, b.getId());
            stm.setString(2, b.getName());
            stm.setString(3, b.getTacGia());
            stm.setString(4, b.getMoTa());
            stm.setInt(5, b.getNamXB());
            stm.setString(6, b.getNoiXB());
            stm.setDate(7, b.getNgayNhap());
            stm.setString(8, b.getViTri());
            stm.setInt(9, b.getCategoryId());
            int r = stm.executeUpdate();

            return r > 0;
        }
    }

    public boolean deleteBooks(Book b) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM book WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, b.getId());
            int r = stm.executeUpdate();

            return r > 0;
        }
    }

    public void updateBooks(Book b) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE book SET name=?, tacgia=?, mota=?, namxb=?, noixb=?, ngaynhap=?, vitri_new=?, category_id=? WHERE id=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, b.getName());
            stm.setString(2, b.getTacGia());
            stm.setString(3, b.getMoTa());
            stm.setInt(4, b.getNamXB());
            stm.setString(5, b.getNoiXB());
            stm.setDate(6, b.getNgayNhap());
            stm.setString(7, b.getViTri());
            stm.setInt(8, b.getCategoryId());
            stm.setInt(9, b.getId());
            stm.executeUpdate();
        }
    }

    public List<ViTri> getViTri() throws SQLException {
        List<ViTri> viTriList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("SELECT * FROM vitri");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                viTriList.add(new ViTri(id, name));
            }
        }
        return viTriList;
    }

    public List<Book> getBooks(String kw) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM book";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE name like concat('%',?,'%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String tacgia = rs.getString("tacgia");
                String mota = rs.getString("mota");
                int namxb = rs.getInt("namxb");
                String noixb = rs.getString("noixb");
                Date ngaynhap = rs.getDate("ngaynhap");
                String vitri = rs.getString("vitri_new");
                int categoryId = rs.getInt("category_id");
                bookList.add(new Book(id, name, tacgia, mota, namxb, noixb, ngaynhap, vitri, categoryId));
            }
        }
        return bookList;
    }

    public List<Book> getBooksId(String kw) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM book";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE id like concat('%',?,'%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String tacgia = rs.getString("tacgia");
                String mota = rs.getString("mota");
                int namxb = rs.getInt("namxb");
                String noixb = rs.getString("noixb");
                Date ngaynhap = rs.getDate("ngaynhap");
                String vitri = rs.getString("vitri_new");
                int categoryId = rs.getInt("category_id");
                bookList.add(new Book(id, name, tacgia, mota, namxb, noixb, ngaynhap, vitri, categoryId));
            }
        }
        return bookList;
    }
    
    public List<Book> getBooksCategory(String kw) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM book JOIN category ON book.category_id = category.id";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE category.name like concat('%',?,'%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String tacgia = rs.getString("tacgia");
                String mota = rs.getString("mota");
                int namxb = rs.getInt("namxb");
                String noixb = rs.getString("noixb");
                Date ngaynhap = rs.getDate("ngaynhap");
                String vitri = rs.getString("vitri_new");
                int categoryId = rs.getInt("category_id");
                bookList.add(new Book(id, name, tacgia, mota, namxb, noixb, ngaynhap, vitri, categoryId));
            }
        }
        return bookList;
    }

}
