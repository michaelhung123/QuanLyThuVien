/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

import com.dht.utils.MessageBox;
import com.mycompany.pojo.User;
import com.mycompany.services.AccountService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author dell
 */
public class HomeController {

    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtTaiKhoan;
    @FXML
    private TextField txtMatKhau;
    @FXML
    private AnchorPane sceneLogin;
    Stage stageOut;

    public void moveLogin(ActionEvent evt) throws IOException, SQLException {
        AccountService acc = new AccountService();
        User u = new User();
        u.setUsername(txtTaiKhoan.getText());
        u.setPassword(txtMatKhau.getText());
        boolean isCheckAcc = acc.checkAccount(u);
        if (isCheckAcc) {
            Alert a = MessageBox.getBox("Notification", "Đăng nhập thành công!", Alert.AlertType.INFORMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    // Tạo Stage mới
                    Stage stage = new Stage();
                    // Tạo Scene mới
                    Parent root;
                    try {
                        stageOut = (Stage) sceneLogin.getScene().getWindow();
                        stageOut.close();

                        root = FXMLLoader.load(getClass().getResource("primary2.fxml"));
                        Scene scene = new Scene(root);
                        // Thiết lập Scene cho Stage mới
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } else {
            MessageBox.getBox("Notification", "Tài khoản hoặc mật khẩu không chính xác!", Alert.AlertType.ERROR).show();
        }
    }
}
