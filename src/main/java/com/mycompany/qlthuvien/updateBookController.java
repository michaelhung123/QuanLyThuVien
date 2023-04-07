/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

import com.dht.utils.MessageBox;
import com.mycompany.pojo.Book;
import com.mycompany.pojo.Category;
import com.mycompany.pojo.ViTri;
import static com.mycompany.qlthuvien.AddBookController.c;
import static com.mycompany.qlthuvien.AddBookController.s;
import static com.mycompany.qlthuvien.AddBookController.selectedRow;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author dell
 */
public class updateBookController implements Initializable {


    @FXML
    private TextField txtTenSach;
    @FXML
    private TextField txtTacGia;
    @FXML
    private TextField txtMoTa;
    @FXML
    private TextField txtNamXB;
    @FXML
    private TextField txtNoiXB;
    @FXML
    private DatePicker txtNgayNhap;
    @FXML
    private ComboBox<ViTri> cbViTri;
    @FXML private ComboBox<Category> cbCategory;
    
    @FXML
    private AnchorPane scenePane;
    Stage stageOut;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<ViTri> viTriList = s.getViTri();
            List<Category> cates = c.getCategories();
            this.cbCategory.setItems(FXCollections.observableList(cates));
            this.cbViTri.setItems(FXCollections.observableList(viTriList));
        } catch (SQLException ex) {
            Logger.getLogger(updateBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ViTri vt = new ViTri(selectedRow.getViTri());
        
        cbViTri.setValue(vt);
        txtTenSach.setText(selectedRow.getName());
        txtNamXB.setText(Integer.toString(selectedRow.getNamXB()));
        txtMoTa.setText(selectedRow.getMoTa());
        txtNoiXB.setText(selectedRow.getNoiXB());
        txtTacGia.setText(selectedRow.getTacGia());
        txtNgayNhap.setValue(selectedRow.getNgayNhap().toLocalDate());
        cbCategory.setValue(new Category(selectedRow.getCategoryString()));
    }
    public void updateBookHandler(ActionEvent evt) {
        try {
            selectedRow.setName(txtTenSach.getText());
            selectedRow.setTacGia(txtTacGia.getText());
            selectedRow.setMoTa(txtMoTa.getText());
            selectedRow.setNamXB(Integer.parseInt(txtNamXB.getText()));
            selectedRow.setNoiXB(txtNoiXB.getText());
            
            LocalDate localDate = txtNgayNhap.getValue();
            Date date = Date.valueOf(localDate);
            selectedRow.setNgayNhap(date);
            
            selectedRow.setViTri(cbViTri.getValue().toString());
            selectedRow.setCategoryId(cbCategory.getSelectionModel().getSelectedItem().getId());
            s.updateBooks(selectedRow);

            Alert a = MessageBox.getBox("Question", "Bạn có chắc muốn cập nhât sách không? ", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    stageOut = (Stage) scenePane.getScene().getWindow();
                    stageOut.close();
                }
            });
        } catch (SQLException ex) {
            MessageBox.getBox("Notification", "Cập nhật sách thất bại", Alert.AlertType.ERROR).show();
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearHandler(ActionEvent evt) {
        this.txtMoTa.clear();
        this.txtNamXB.clear();
        this.txtNoiXB.clear();
        this.txtTacGia.clear();
        this.txtTenSach.clear();
        this.txtNgayNhap.setValue(null);
        this.cbViTri.setValue(null);
        this.cbCategory.setValue(null);
    }

}
