/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.mycompany.pojo.BoPhan;
import com.mycompany.pojo.Book;
import com.mycompany.pojo.DocGia;
import com.mycompany.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dell
 */
public class AccountService {

   public boolean checkAccount(User u) throws SQLException{
       try (Connection conn = JdbcUtils.getConn()) {

            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }

            conn.close();
        }
       return false;
   }
}
