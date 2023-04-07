/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

import com.dht.utils.MessageBox;
import com.mycompany.pojo.Book;
import com.mycompany.pojo.Category;
import com.mycompany.pojo.ViTri;
import com.mycompany.services.BookService;
import com.mycompany.services.CategoryService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author dell
 */
public class AddBookController implements Initializable {

    static Book selectedRow = new Book();
    static BookService s = new BookService();
    static CategoryService c = new CategoryService();
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
    private TableView<Book> tbViewBook;
    @FXML
    private ComboBox<ViTri> cbViTri;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<Category> cbCategory;
    @FXML
    private Button btnHome;
    @FXML
    private VBox sceneVBox;
    private ObservableList<Book> tableBookList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnHome.setOnAction(e -> {
            // Tạo Stage mới
            Stage stage = new Stage();
            // Tạo Scene mới
            Parent root;
            try {
                Stage stages = (Stage) btnHome.getScene().getWindow();
                stages.close();

                root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene scene = new Scene(root);
                // Thiết lập Scene cho Stage mới
                stage.setScene(scene);
                stage.setTitle("Login");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        try {
            List<ViTri> viTriList = s.getViTri();
            List<Category> categoryList = c.getCategories();
            this.cbViTri.setItems(FXCollections.observableList(viTriList));
            this.cbCategory.setItems(FXCollections.observableList(categoryList));
            this.loadTableColumns();
            this.loadTableData(null);
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Hàm tìm kiếm sách 
        this.txtSearch.textProperty().addListener(e -> {
            try {
                this.loadTableData(this.txtSearch.getText());
            } catch (SQLException ex) {
                Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addBookHandler(ActionEvent evt) throws SQLException {
        Book b = new Book();
        if (txtTenSach.getText() == null
                || txtTacGia.getText() == null
                || txtNamXB.getText() == null
                || txtMoTa.getText() == null
                || cbViTri.getValue() == null
                || txtNoiXB.getText() == null
                || txtNgayNhap.getValue() == null
                || cbCategory.getValue() == null) {
            MessageBox.getBox("Notification", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.WARNING).show();
            return;
        }
        try {
            b.setName(txtTenSach.getText());
            b.setTacGia(txtTacGia.getText());
            b.setMoTa(txtMoTa.getText());
            b.setNamXB(Integer.parseInt(txtNamXB.getText()));
            b.setNoiXB(txtNoiXB.getText());
            b.setViTri(cbViTri.getValue().toString());

            LocalDate localDate = txtNgayNhap.getValue();
            Date date = Date.valueOf(localDate);
            b.setNgayNhap(date);

            b.setCategoryId(cbCategory.getSelectionModel().getSelectedItem().getId());

            if (this.tableBookList == null) {
                this.tableBookList = FXCollections.observableArrayList();
            }
            this.tableBookList.add(b);
            s.addBook(b);
            this.txtMoTa.clear();
            this.txtNamXB.clear();
            this.txtNoiXB.clear();
            this.txtTacGia.clear();
            this.txtTenSach.clear();
            this.txtNgayNhap.setValue(null);
            this.cbViTri.setValue(null);
            this.cbCategory.setValue(null);
            this.loadTableData(null);
            MessageBox.getBox("Notification", "Add book successful", Alert.AlertType.INFORMATION).show();
        } catch (SQLException ex) {
            MessageBox.getBox("Notification", "Add book failed", Alert.AlertType.ERROR).show();
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteBookHandler(ActionEvent evt) throws SQLException {
        if (tbViewBook.getSelectionModel().getSelectedItem() == null) {
            MessageBox.getBox("Notification", "Vui lòng tích chọn sách trong danh sách để xóa", Alert.AlertType.WARNING).show();
            return;
        }
        try {
            int selectedIndex = tbViewBook.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                tableBookList = tbViewBook.getItems();  // tbViewBook.getItems() trả về 1 đối tượng kiểu Book
                Book bookDelete = tableBookList.get(selectedIndex);
                tableBookList.remove(bookDelete);
                s.deleteBooks(bookDelete);
            }

            MessageBox.getBox("Notification", "Remove book successfull", Alert.AlertType.INFORMATION).show();
        } catch (SQLException ex) {
            MessageBox.getBox("Notification", "Remove book failed", Alert.AlertType.ERROR).show();
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

    private void loadTableColumns() {
        TableColumn colId = new TableColumn("Mã sách");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(70);

        TableColumn colName = new TableColumn("Tên sách");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(150);

        TableColumn colTacGia = new TableColumn("Tác giả");
        colTacGia.setCellValueFactory(new PropertyValueFactory("tacGia"));
        colTacGia.setPrefWidth(100);

        TableColumn colMoTa = new TableColumn("Mô tả");
        colMoTa.setCellValueFactory(new PropertyValueFactory("moTa"));
        colMoTa.setPrefWidth(150);

        TableColumn colNamXB = new TableColumn("Năm xuất bản");
        colNamXB.setCellValueFactory(new PropertyValueFactory("namXB"));
        colNamXB.setPrefWidth(120);

        TableColumn colNoiXB = new TableColumn("Nơi xuất bản");
        colNoiXB.setCellValueFactory(new PropertyValueFactory("noiXB"));
        colNoiXB.setPrefWidth(120);

        TableColumn colNgayNhap = new TableColumn("Ngày nhập");
        colNgayNhap.setCellValueFactory(new PropertyValueFactory("ngayNhap"));
        colNgayNhap.setPrefWidth(130);

        TableColumn colViTri = new TableColumn("Vị trí");
        colViTri.setCellValueFactory(new PropertyValueFactory("viTri"));
        colViTri.setPrefWidth(100);
        TableColumn colCategory = new TableColumn<>("Thể loại");
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryString"));

        this.tbViewBook.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            // Tạo ContextMenu
            ContextMenu contextMenu = new ContextMenu();
            // Tạo updateItem "update" để update sách
            MenuItem updateItem = new MenuItem("Cập nhật thông tin");
            updateItem.setOnAction(e -> {
                if (this.tbViewBook.getSelectionModel().getSelectedItem() != null) {
                    try {
                        selectedRow = this.tbViewBook.getSelectionModel().getSelectedItem();
                        // Tạo Stage mới
                        Stage stage = new Stage();
                        // Tạo Scene mới
                        Parent root = FXMLLoader.load(getClass().getResource("updateBook.fxml"));
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
                                Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            // Thêm MenuItem vào ContextMenu
            contextMenu.getItems().addAll(updateItem);
            // Thiết lập ContextMenu cho TableRow
            row.setContextMenu(contextMenu);
            return row;
        });

        this.tbViewBook.getColumns().addAll(colId, colName, colTacGia, colMoTa, colNamXB, colNoiXB, colNgayNhap, colViTri, colCategory);
    }

    private void loadTableData(String kw) throws SQLException {
        List<Book> books = s.getBooks(kw);
        List<Book> booksTemp = new ArrayList<>();
        List<Category> cates = c.getCategories();
        for (Book item : books) {
            Book bAdd = new Book();
            bAdd.setName(item.getName());
            bAdd.setId(item.getId());
            bAdd.setMoTa(item.getMoTa());
            bAdd.setNamXB(item.getNamXB());
            bAdd.setNgayNhap(item.getNgayNhap());
            bAdd.setNoiXB(item.getNoiXB());
            bAdd.setTacGia(item.getTacGia());
            bAdd.setViTri(item.getViTri());
            String cateName = "";
            for (Category cate : cates) {
                if (item.getCategoryId() == cate.getId()) {
                    cateName = cate.getName();
                }
            }
            bAdd.setCategoryString(cateName);
            booksTemp.add(bAdd);
        }
        this.tbViewBook.getItems().clear();
        this.tbViewBook.setItems(FXCollections.observableList(booksTemp));
    }

    public void TableFocusClick() {
        this.tbViewBook.setOnMouseClicked(e -> {
            // Lấy dòng được chọn
            Book selectedItem = this.tbViewBook.getSelectionModel().getSelectedItem();
            // Kiểm tra xem dòng được chọn có tồn tại hay không
            if (selectedItem != null) {
                // Hiển thị dữ liệu lên các TextField
                txtTenSach.setText(selectedItem.getName());
                txtNamXB.setText(Integer.toString(selectedItem.getNamXB()));
                txtMoTa.setText(selectedItem.getMoTa());
                txtNoiXB.setText(selectedItem.getNoiXB());
                txtTacGia.setText(selectedItem.getTacGia());
                txtNgayNhap.setValue(selectedItem.getNgayNhap().toLocalDate()); // toLocalDate() là phương thức để chuyển đổi từ đối tượng Date sang đối tượng LocalDate 
                cbViTri.setValue(new ViTri(selectedItem.getViTri()));
                cbCategory.setValue(new Category(selectedItem.getCategoryString()));
            }
        });

    }
}
