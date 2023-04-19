/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

import com.dht.utils.MessageBox;
import com.mycompany.pojo.BoPhan;
import com.mycompany.pojo.DocGia;
import com.mycompany.services.DocGiaService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author dell
 */
public class DocGiaController implements Initializable {

    static DocGia selectedRow = new DocGia();
    static DocGiaService g = new DocGiaService();

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
    private TableView<DocGia> tbViewDocGia;
    private ObservableList<DocGia> tableDocGiaList;
    DocGia dg = new DocGia();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<BoPhan> boPhanList = g.getBoPhan();
            this.cbBoPhan.setItems(FXCollections.observableList(boPhanList));
            this.loadTableColumns();
            this.loadTableData(null);

            ToggleGroup toggleGroup = new ToggleGroup();
            rdbNam.setToggleGroup(toggleGroup);
            rdbNu.setToggleGroup(toggleGroup);
            // Lắng nghe sự kiện khi RadioButton được chọn
            toggleGroup.selectedToggleProperty().addListener((var observable, var oldValue, var newValue) -> {
                if (toggleGroup.getSelectedToggle() != null) {
                    String result = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                    dg.setGioiTinh(result);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addDocGiaHandler(ActionEvent evt) throws SQLException {
        if (txtDiaChi.getText() == null
                || txtEmail.getText() == null
                || txtHoTen.getText() == null
                || txtMaDocGia.getText() == null
                || txtNgaySinh.getValue() == null
                || txtPhone.getText() == null
                || cbBoPhan.getValue() == null) {
            MessageBox.getBox("Notification", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.WARNING).show();
            return;
        }
        try {
            LocalDate localDate = txtNgaySinh.getValue();
            Date dateNgaySinh = Date.valueOf(localDate);

            dg.setId(txtMaDocGia.getText());
            dg.setName(txtHoTen.getText());
            dg.setNgaySinh(dateNgaySinh);
            dg.setBoPhan(cbBoPhan.getValue().toString());
            dg.setEmail(txtEmail.getText());
            dg.setDiaChi(txtDiaChi.getText());
            dg.setPhoneNumber(txtPhone.getText());

            if (this.tableDocGiaList == null) {
                this.tableDocGiaList = FXCollections.observableArrayList();
            }
            this.tableDocGiaList.add(dg);
            g.addDocGia(dg);
            this.txtMaDocGia.clear();
            this.txtHoTen.clear();
            this.txtEmail.clear();
            this.txtDiaChi.clear();
            this.txtPhone.clear();
            this.txtNgaySinh.setValue(null);
            this.cbBoPhan.setValue(null);
            if (rdbNam.isSelected()) {
                rdbNam.setSelected(false);
            } else if (rdbNu.isSelected()) {
                // chọn RadioButton
                rdbNu.setSelected(false);
            }
            this.loadTableData(null);
            MessageBox.getBox("Notification", "Thêm độc giả thành công!", Alert.AlertType.INFORMATION).show();
        } catch (SQLException ex) {
            MessageBox.getBox("Notification", "Thêm độc giả thất bại!", Alert.AlertType.ERROR).show();
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteDocGiaHandler(ActionEvent evt) throws SQLException {
        if (tbViewDocGia.getSelectionModel().getSelectedItem() == null) {
            MessageBox.getBox("Notification", "Vui lòng tích chọn độc giả trong danh sách để xóa", Alert.AlertType.WARNING).show();
            return;
        }
        try {
            int selectedIndex = tbViewDocGia.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                tableDocGiaList = tbViewDocGia.getItems();  // tbViewBook.getItems() trả về 1 đối tượng kiểu Book
                DocGia docGiaDelete = tableDocGiaList.get(selectedIndex);
                tableDocGiaList.remove(docGiaDelete);
                g.deleteDocGia(docGiaDelete);
            }

            MessageBox.getBox("Notification", "Xóa độc giả thành công!", Alert.AlertType.INFORMATION).show();
        } catch (SQLException ex) {
            MessageBox.getBox("Notification", "Xóa độc giả thất bại!", Alert.AlertType.ERROR).show();
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

    public void TableFocusClick() {
        this.tbViewDocGia.setOnMouseClicked(e -> {
            // Lấy dòng được chọn
            DocGia selectedItem = (DocGia) this.tbViewDocGia.getSelectionModel().getSelectedItem();
            // Kiểm tra xem dòng được chọn có tồn tại hay không
            if (selectedItem != null) {
                // Hiển thị dữ liệu lên các TextField
                txtMaDocGia.setText(selectedItem.getId());
                txtHoTen.setText(selectedItem.getName());
                txtNgaySinh.setValue(selectedItem.getNgaySinh().toLocalDate());
                cbBoPhan.setValue(new BoPhan(selectedItem.getBoPhan()));
                txtEmail.setText(selectedItem.getEmail());
                txtDiaChi.setText(selectedItem.getDiaChi());
                txtPhone.setText(selectedItem.getPhoneNumber());
                // Lấy giá trị của trường "gioi tinh" trong dòng được chọn
                String gioiTinh = selectedItem.getGioiTinh();
                if (gioiTinh.equals("Nam")) {
                    rdbNam.setSelected(true);
                } else if (gioiTinh.equals("Nữ")) {
                    rdbNu.setSelected(true);
                } else {
                    // Giá trị không hợp lệ, không tích vào bất kỳ RadioButton nào
                    rdbNam.setSelected(false);
                    rdbNu.setSelected(false);
                }
            }
        });

    }

    private void loadTableColumns() {
        TableColumn colID = new TableColumn("Mã độc giả");
        colID.setCellValueFactory(new PropertyValueFactory("id"));
        colID.setPrefWidth(110);

        TableColumn colHoTen = new TableColumn("Họ tên");
        colHoTen.setCellValueFactory(new PropertyValueFactory("name"));
        colHoTen.setPrefWidth(130);

        TableColumn colGioiTinh = new TableColumn("Giới tính");
        colGioiTinh.setCellValueFactory(new PropertyValueFactory("gioiTinh"));
        colGioiTinh.setPrefWidth(110);

        TableColumn colNgaySinh = new TableColumn("Ngày sinh");
        colNgaySinh.setCellValueFactory(new PropertyValueFactory("ngaySinh"));
        colNgaySinh.setPrefWidth(110);

        TableColumn colBoPhan = new TableColumn("Bộ phận");
        colBoPhan.setCellValueFactory(new PropertyValueFactory("boPhan"));
        colBoPhan.setPrefWidth(110);

        TableColumn colNgayLapThe = new TableColumn("Ngày lập thẻ");
        colNgayLapThe.setCellValueFactory(new PropertyValueFactory("ngayLapThe"));
        colNgayLapThe.setPrefWidth(110);

        TableColumn colDiaChi = new TableColumn("Địa chỉ");
        colDiaChi.setCellValueFactory(new PropertyValueFactory("diaChi"));
        colDiaChi.setPrefWidth(110);

        TableColumn colEmail = new TableColumn("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colEmail.setPrefWidth(110);

        TableColumn colSDT = new TableColumn("Số điện thoại");
        colSDT.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        colSDT.setPrefWidth(110);

        this.tbViewDocGia.setRowFactory(tv -> {
            TableRow<DocGia> row = new TableRow<>();
            // Tạo ContextMenu
            ContextMenu contextMenu = new ContextMenu();
            // Tạo updateItem "update" để update sách
            MenuItem updateItem = new MenuItem("Cập nhật thông tin");
            updateItem.setOnAction(e -> {
                if (this.tbViewDocGia.getSelectionModel().getSelectedItem() != null) {
                    try {
                        selectedRow = this.tbViewDocGia.getSelectionModel().getSelectedItem();
                        // Tạo Stage mới
                        Stage stage = new Stage();
                        // Tạo Scene mới
                        Parent root = FXMLLoader.load(getClass().getResource("updateDocGia.fxml"));
                        Scene scene = new Scene(root);
                        // Thiết lập Scene cho Stage mới
                        stage.setScene(scene);
                        stage.setTitle("Cập nhật sách");
                        // Xử lý sự kiện khi Stage mới bị đóng lại
                        stage.setOnHidden(event -> {
                            try {
                                loadTableData(null);
                            } catch (SQLException ex) {
                                MessageBox.getBox("Notification", "Cập nhật thất bại !", Alert.AlertType.ERROR).show();
                                Logger.getLogger(DocGiaController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(DocGiaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            // Thêm MenuItem vào ContextMenu
            contextMenu.getItems().addAll(updateItem);
            // Thiết lập ContextMenu cho TableRow
            row.setContextMenu(contextMenu);
            return row;
        });

        this.tbViewDocGia.getColumns().addAll(colID, colHoTen, colGioiTinh, colNgaySinh, colBoPhan, colNgayLapThe, colDiaChi, colEmail, colSDT);
    }

    private void loadTableData(String kw) throws SQLException {
        List<DocGia> docGias = g.getDocGias(kw);
        this.tbViewDocGia.getItems().clear();
        this.tbViewDocGia.setItems(FXCollections.observableList(docGias));
    }
}
