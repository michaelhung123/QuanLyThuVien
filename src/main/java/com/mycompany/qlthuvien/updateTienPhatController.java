/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

import com.dht.utils.MessageBox;
import static com.mycompany.qlthuvien.MuonTraController.p;
import static com.mycompany.qlthuvien.MuonTraController.selectedRow;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
public class updateTienPhatController {
    @FXML
    private TextField txtTienPhat;
    @FXML
    private AnchorPane scenePane;
    Stage stageOut;

    public void updateTienPhatHandler(ActionEvent evt) throws SQLException {
        try {
            selectedRow.setTienPhat(Double.parseDouble(txtTienPhat.getText()));
            p.updateTienPhatPhieuMuon(selectedRow.getTienPhat(), selectedRow.getId());

            Alert a = MessageBox.getBox("Question", "Bạn có chắc muốn cập nhật tiền phạt cho độc giả này không? ", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    stageOut = (Stage) scenePane.getScene().getWindow();
                    stageOut.close();
                }
            });
        } catch (SQLException ex) {
            MessageBox.getBox("Notification", "Cập nhật tiền phạt thất bại", Alert.AlertType.ERROR).show();
            Logger.getLogger(updateTienPhatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearHandler(ActionEvent evt) {
        this.txtTienPhat.clear();
    }
}
