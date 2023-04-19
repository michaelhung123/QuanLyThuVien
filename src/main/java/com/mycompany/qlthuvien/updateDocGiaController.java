/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

import com.dht.utils.MessageBox;
import com.mycompany.pojo.BoPhan;
import com.mycompany.pojo.DocGia;
import static com.mycompany.qlthuvien.DocGiaController.selectedRow;
import static com.mycompany.qlthuvien.DocGiaController.g;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author dell
 */
public class updateDocGiaController implements Initializable {

    @FXML
    private TextField txtMaDocGia;
    @FXML
    private TextField txtHoTen;
    @FXML
    private DatePicker txtNgaySinh;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private TextField txtPhone;
    @FXML
    private ComboBox<BoPhan> cbBoPhan;
    @FXML
    private RadioButton rdbNam;
    @FXML
    private RadioButton rdbNu;
    @FXML
    private AnchorPane scenePane;
    Stage stageOut;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<BoPhan> boPhanList = g.getBoPhan();
            this.cbBoPhan.setItems(FXCollections.observableList(boPhanList));
        } catch (SQLException ex) {
            Logger.getLogger(updateDocGiaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BoPhan bp = new BoPhan(selectedRow.getBoPhan());
        cbBoPhan.setValue(bp);
        txtMaDocGia.setText(selectedRow.getId());
        txtEmail.setText(selectedRow.getEmail());
        txtHoTen.setText(selectedRow.getName());
        txtNgaySinh.setValue(selectedRow.getNgaySinh().toLocalDate());
        cbBoPhan.setValue(new BoPhan(selectedRow.getBoPhan()));
        txtDiaChi.setText(selectedRow.getDiaChi());
        txtPhone.setText(selectedRow.getPhoneNumber());
        String gioiTinh = selectedRow.getGioiTinh();
        if (gioiTinh.equals("Nam")) {
            rdbNam.setSelected(true);
        } else if (gioiTinh.equals("Nữ")) {
            rdbNu.setSelected(true);
        } else {
            // Giá trị không hợp lệ, không tích vào bất kỳ RadioButton nào
            rdbNam.setSelected(false);
            rdbNu.setSelected(false);
        }

        ToggleGroup toggleGroup = new ToggleGroup();
        rdbNam.setToggleGroup(toggleGroup);
        rdbNu.setToggleGroup(toggleGroup);
        // Lắng nghe sự kiện khi RadioButton được chọn
        toggleGroup.selectedToggleProperty().addListener((var observable, var oldValue, var newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                String result = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                selectedRow.setGioiTinh(result);
            }
        });
    }

    public void updateDocGiaHandler(ActionEvent evt) {
        try {
            LocalDate localDate = txtNgaySinh.getValue();
            Date dateNgaySinh = Date.valueOf(localDate);

            selectedRow.setBoPhan(cbBoPhan.getValue().toString());
            selectedRow.setId(txtMaDocGia.getText());
            selectedRow.setName(txtHoTen.getText());
            selectedRow.setPhoneNumber(txtPhone.getText());
            selectedRow.setEmail(txtEmail.getText());
            selectedRow.setDiaChi(txtDiaChi.getText());
            selectedRow.setNgaySinh(dateNgaySinh);
            g.updateDocGia(selectedRow);

            Alert a = MessageBox.getBox("Question", "Bạn có chắc muốn cập nhât thông tin độc giả không? ", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    stageOut = (Stage) scenePane.getScene().getWindow();
                    stageOut.close();
                }
            });
        } catch (SQLException ex) {
            MessageBox.getBox("Notification", "Cập nhật thông tin độc giả thất bại", Alert.AlertType.ERROR).show();
            Logger.getLogger(DocGiaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearHandler(ActionEvent evt) {
        this.txtMaDocGia.clear();
        this.txtHoTen.clear();
        this.txtEmail.clear();
        this.txtDiaChi.clear();
        this.txtPhone.clear();
        if (rdbNam.isSelected()) {
            rdbNam.setSelected(false);
        } else if (rdbNu.isSelected()) {
            // chọn RadioButton
            rdbNu.setSelected(false);
        }
        this.txtNgaySinh.setValue(null);
        this.cbBoPhan.setValue(null);
    }

}
