/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

import com.dht.utils.MessageBox;
import com.mycompany.pojo.BoPhan;
import com.mycompany.pojo.Book;
import com.mycompany.pojo.Category;
import com.mycompany.pojo.DocGia;
import com.mycompany.pojo.PhieuMuon;
import static com.mycompany.qlthuvien.AddBookController.c;
import static com.mycompany.qlthuvien.AddBookController.s;
import static com.mycompany.qlthuvien.DocGiaController.g;
import static com.mycompany.qlthuvien.DocGiaController.selectedRow;
import com.mycompany.services.BookService;
import com.mycompany.services.PhieuMuonService;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
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
public class MuonTraController implements Initializable {

    static PhieuMuon selectedRow = new PhieuMuon();
    static PhieuMuonService p = new PhieuMuonService();
    PhieuMuon pm = new PhieuMuon();
    @FXML
    private DatePicker txtHanTra;
    @FXML
    private ComboBox<DocGia> cbDocGia;
    @FXML
    private TableView<Book> tbViewBook;
    @FXML
    private TableView<PhieuMuon> tbViewPhieuMuon;
    @FXML
    private TextField txtSearch;
    @FXML
    private RadioButton rdbTenSach;
    @FXML
    private RadioButton rdbMaSach;
    @FXML
    private RadioButton rdbTheLoai;
    @FXML
    private ComboBox<BoPhan> cbBoPhan;
    @FXML
    private Label lbShowName;
    @FXML
    private TextField txtSearchPM;
    @FXML
    private RadioButton rdbMaPhieu;
    @FXML

    private ObservableList<PhieuMuon> tablePhieuMuonList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println("Init : " + pm.isActive());
            lbShowName.setText("Tất cả");
            List<DocGia> docGiaList = g.getIdDocGia(null);
            List<BoPhan> boPhanList = g.getBoPhan();
            this.cbBoPhan.setItems(FXCollections.observableList(boPhanList));
            this.cbDocGia.setItems(FXCollections.observableList(docGiaList));

            // Thêm sự kiện lắng nghe vào ComboBox
            cbDocGia.valueProperty().addListener((observable, oldValue, newValue) -> {
                pm.setActive(true); // Đặt lại giá trị của biến pm.isActive() về true
                System.out.println(pm.isActive());
            });

            loadTableColumnsBook();
            loadTableDataBook(null);
            loadTableColumnsPhieuMuon();
            loadTableDataPhieuMuon(null);
        } catch (SQLException ex) {
            Logger.getLogger(MuonTraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchPhieuMuons(ActionEvent evt) throws SQLException {
        ToggleGroup filterGroup = new ToggleGroup();
        this.rdbMaPhieu.setToggleGroup(filterGroup);
        if (this.rdbMaPhieu.isSelected()) {
            loadTableDataPhieuMuonId(this.txtSearchPM.getText());
        }
    }

    public void searchBooks(ActionEvent evt) throws SQLException {
        ToggleGroup filterGroup = new ToggleGroup();
        this.rdbMaSach.setToggleGroup(filterGroup);
        this.rdbTenSach.setToggleGroup(filterGroup);
        this.rdbTheLoai.setToggleGroup(filterGroup);
        if (this.rdbMaSach.isSelected()) {
            loadTableDataBookId(this.txtSearch.getText());
        } else if (this.rdbTenSach.isSelected()) {
            loadTableDataBook(this.txtSearch.getText());
        } else if (this.rdbTheLoai.isSelected()) {
            loadTableDataBookCate(this.txtSearch.getText());
        }
    }

    public void searchDocGia(ActionEvent evt) throws SQLException {
        String selectedCombobox = cbBoPhan.getValue().toString();
        loadTableDataDocGiaId(selectedCombobox);
    }

    public void searchPhieuMuonByIdDocGia(ActionEvent evt) throws SQLException {
        if (cbDocGia.getValue() != null) {
            lbShowName.setText(cbDocGia.getValue().toString());
        } else {
            lbShowName.setText("Tất cả");
        }

        if (cbDocGia.getValue() == null) {
            loadTableDataPhieuMuon(null);
        } else {
            loadTableDataPhieuMuon(cbDocGia.getValue().toString());
        }
    }

    public void addPhieuMuonHandler(ActionEvent evt) throws SQLException {
        System.out.println("Them : " + pm.isActive());
        if (cbDocGia.getValue().toString().equals(pm.getDocGiaId()) && pm.isActive() == false) {
            MessageBox.getBox("Notification", "Bạn đã đặt sách rồi! đừng mượn", Alert.AlertType.WARNING).show();
            return;
        }
        //Kiem tra so luong phieu muon cua doc gia
        if (p.checkSoLuongPhieuMuon(cbDocGia.getValue().toString()) == true) {
            MessageBox.getBox("Notification", "Số lượng phiếu mượn của độc giả không được vượt quá 5", Alert.AlertType.WARNING).show();
            return;
        }

        if (tbViewBook.getSelectionModel().getSelectedItem() == null || cbDocGia.getSelectionModel().getSelectedItem() == null || txtHanTra.getValue() == null) {
            MessageBox.getBox("Notification", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.WARNING).show();
            return;
        }

        try {
            Book selectedBook = tbViewBook.getSelectionModel().getSelectedItem();
            DocGia selectedCombobox = cbDocGia.getSelectionModel().getSelectedItem();
            LocalDate localDate = txtHanTra.getValue();
            Date date = Date.valueOf(localDate);
            pm.setBookId(selectedBook.getId());
            pm.setTenSach(selectedBook.getName());
            pm.setDocGiaId(cbDocGia.getValue().toString());
            pm.setTenDocGia(selectedCombobox.getName());
            pm.setHanTra(date);
            pm.setActive(true);
            //Upate So Luong cua Phieu Muon
            boolean found = false;
            for (PhieuMuon pmItem : tbViewPhieuMuon.getItems()) {
                if (pmItem.getBookId() == selectedBook.getId() && pmItem.getDocGiaId().equals(cbDocGia.getValue().toString())) {
                    found = true;
                    p.updateSLPhieuMuon(pmItem.getSoLuong() + 1, pm.getHanTra(), selectedBook.getId(), cbDocGia.getValue().toString());
                    this.loadTableDataPhieuMuon(null);

                    break;
                }
            }

            //Kiem tra dieu kien ngay muon > han tra
            LocalDate dateNgayMuon = LocalDate.now();
            if (localDate.isBefore(dateNgayMuon)) {
                MessageBox.getBox("Notification", "Ngày mượn phải lớn hơn hạn trả!", Alert.AlertType.WARNING).show();
                return;
            }
            if (!found) {
                p.addPhieuMuon(pm);
            }
            if (this.tablePhieuMuonList == null) {
                this.tablePhieuMuonList = FXCollections.observableArrayList();
            }
            this.loadTableDataPhieuMuon(null);
            MessageBox.getBox("Notification", "Thêm phiếu mượn thành công!", Alert.AlertType.INFORMATION).show();
        } catch (SQLException ex) {
            MessageBox.getBox("Notification", "Thêm phiếu mượn thất bại!", Alert.AlertType.ERROR).show();
            Logger.getLogger(MuonTraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void datTruocHandler(ActionEvent evt) throws SQLException {
        System.out.println("Dat truoc: " + pm.isActive());
        if (cbDocGia.getValue().toString().equals(pm.getDocGiaId()) && pm.isActive() == true) {
            MessageBox.getBox("Notification", "Mượn rồi, đừng đặt trước", Alert.AlertType.WARNING).show();
            return;
        }
        //Kiem tra so luong phieu muon cua doc gia
        if (p.checkSoLuongPhieuMuon(cbDocGia.getValue().toString()) == true) {
            MessageBox.getBox("Notification", "Số lượng phiếu mượn của độc giả không được vượt quá 5", Alert.AlertType.WARNING).show();
            return;
        }

        if (tbViewBook.getSelectionModel().getSelectedItem() == null || cbDocGia.getSelectionModel().getSelectedItem() == null || txtHanTra.getValue() == null) {
            MessageBox.getBox("Notification", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.WARNING).show();
            return;
        }
        try {
            Book selectedBook = tbViewBook.getSelectionModel().getSelectedItem();
            DocGia selectedCombobox = cbDocGia.getSelectionModel().getSelectedItem();
            LocalDate localDate = txtHanTra.getValue();
            Date date = Date.valueOf(localDate);
            pm.setBookId(selectedBook.getId());
            pm.setTenSach(selectedBook.getName());
            pm.setDocGiaId(cbDocGia.getValue().toString());
            pm.setTenDocGia(selectedCombobox.getName());
            pm.setHanTra(date);
            pm.setActive(false);
            //Upate So Luong cua Phieu Muon
            boolean found = false;
            for (PhieuMuon pmItem : tbViewPhieuMuon.getItems()) {
                if (pmItem.getBookId() == selectedBook.getId() && pmItem.getDocGiaId().equals(cbDocGia.getValue().toString())) {
                    found = true;
                    p.updateSLPhieuMuon(pmItem.getSoLuong() + 1, pm.getHanTra(), selectedBook.getId(), cbDocGia.getValue().toString());
                    this.loadTableDataPhieuMuon(null);

                    break;
                }
            }

            //Kiem tra dieu kien ngay muon > han tra
            LocalDate dateNgayMuon = LocalDate.now();
            if (localDate.isBefore(dateNgayMuon)) {
                MessageBox.getBox("Notification", "Ngày mượn phải lớn hơn hạn trả!", Alert.AlertType.WARNING).show();
                return;
            }
            if (!found) {
                p.addPhieuMuon(pm);
            }
            if (this.tablePhieuMuonList == null) {
                this.tablePhieuMuonList = FXCollections.observableArrayList();
            }
            this.loadTableDataPhieuMuon(null);
            MessageBox.getBox("Notification", "Đặt trước sách thành công! Vui lòng đến lấy sách trước 48h", Alert.AlertType.INFORMATION).show();
        } catch (SQLException ex) {
            MessageBox.getBox("Notification", "Thêm phiếu mượn thất bại!", Alert.AlertType.ERROR).show();
            Logger.getLogger(MuonTraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletePhieuMuonHandler(ActionEvent evt) throws SQLException {
        if (tbViewPhieuMuon.getSelectionModel().getSelectedItem() == null) {
            MessageBox.getBox("Notification", "Vui lòng tích chọn phiếu mượn trong danh sách để trả", Alert.AlertType.WARNING).show();
            return;
        }
        try {
            int selectedIndex = tbViewPhieuMuon.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                tablePhieuMuonList = tbViewPhieuMuon.getItems();  // tbViewPhieuMuon.getItems() trả về 1 đối tượng kiểu PhieuMuon
                PhieuMuon phieuMuonDelete = tablePhieuMuonList.get(selectedIndex);
                tablePhieuMuonList.remove(phieuMuonDelete);
                p.deletePhieuMuon(phieuMuonDelete);
            }

            MessageBox.getBox("Notification", "Trả phiếu mượn thành công!", Alert.AlertType.INFORMATION).show();
        } catch (SQLException ex) {
            MessageBox.getBox("Notification", "Trả phiếu mượn thất bại!", Alert.AlertType.ERROR).show();
            Logger.getLogger(MuonTraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//     public void deletePhieuMuonHandler(ActionEvent evt) throws SQLException {
//        if (tbViewPhieuMuon.getSelectionModel().getSelectedItem() == null) {
//            MessageBox.getBox("Notification", "Vui lòng tích chọn phiếu mượn trong danh sách để trả", Alert.AlertType.WARNING).show();
//            return;
//        }
//        try {
//            int selectedIndex = tbViewPhieuMuon.getSelectionModel().getSelectedIndex();
//            if (selectedIndex >= 0) {
//                tablePhieuMuonList = tbViewPhieuMuon.getItems();  // tbViewPhieuMuon.getItems() trả về 1 đối tượng kiểu PhieuMuon
//                PhieuMuon phieuMuonDelete = tablePhieuMuonList.get(selectedIndex);
//                tablePhieuMuonList.remove(phieuMuonDelete);
//                p.traPhieuMuon(2, phieuMuonDelete.getId());
//            }
//            MessageBox.getBox("Notification", "Trả phiếu mượn thành công!", Alert.AlertType.INFORMATION).show();
//        } catch (SQLException ex) {
//            MessageBox.getBox("Notification", "Trả phiếu mượn thất bại!", Alert.AlertType.ERROR).show();
//            Logger.getLogger(MuonTraController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private void loadTableColumnsBook() throws SQLException {
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

        this.tbViewBook.getColumns().addAll(colId, colName, colTacGia, colMoTa, colNamXB, colNoiXB, colNgayNhap, colViTri, colCategory);
    }

    private void loadTableDataBook(String kw) throws SQLException {
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

    private void loadTableDataBookId(String kw) throws SQLException {
        List<Book> books = s.getBooksId(kw);
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

    private void loadTableDataBookCate(String kw) throws SQLException {
        List<Book> books = s.getBooksCategory(kw);
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

    private void loadTableColumnsPhieuMuon() throws SQLException {
        TableColumn colId = new TableColumn("Mã phiếu");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(70);

        TableColumn colMaSach = new TableColumn("Mã sách");
        colMaSach.setCellValueFactory(new PropertyValueFactory("bookId"));
        colMaSach.setPrefWidth(80);

        TableColumn colMaDocGia = new TableColumn("Mã độc giả");
        colMaDocGia.setCellValueFactory(new PropertyValueFactory("docGiaId"));
        colMaDocGia.setPrefWidth(100);

        TableColumn colTenDocGia = new TableColumn("Tên độc giả");
        colTenDocGia.setCellValueFactory(new PropertyValueFactory("tenDocGia"));
        colTenDocGia.setPrefWidth(150);

        TableColumn colTenSach = new TableColumn("Tên sách");
        colTenSach.setCellValueFactory(new PropertyValueFactory("tenSach"));
        colTenSach.setPrefWidth(120);

        TableColumn colSoLuong = new TableColumn("Số lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soLuong"));
        colSoLuong.setPrefWidth(70);

        TableColumn colNgayMuon = new TableColumn("Ngày mượn");
        colNgayMuon.setCellValueFactory(new PropertyValueFactory("ngayMuon"));
        colNgayMuon.setPrefWidth(120);

        TableColumn colHanTra = new TableColumn("Hạn trả");
        colHanTra.setCellValueFactory(new PropertyValueFactory("hanTra"));
        colHanTra.setPrefWidth(130);

        TableColumn colActive = new TableColumn("Trạng thái");
        colActive.setCellValueFactory(new PropertyValueFactory("active"));
        colActive.setPrefWidth(120);
        //Create and Check active of PhieuMuon
        colActive.setCellFactory(column -> new TableCell<PhieuMuon, Boolean>() {
            private boolean isActive;
            private final Button btnAdd = new Button("Thêm");
            private final Button btnDatTruoc = new Button("Đặt trước");

            {
                btnAdd.setOnAction(event -> {
                    isActive = true;
                    setText("Đang mượn");
                });

                btnDatTruoc.setOnAction(event -> {
                    isActive = false;
                    setText("Đặt trước");
                });
            }

            @Override
            protected void updateItem(Boolean isActive, boolean empty) {
                super.updateItem(isActive, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (isActive ) {
                        setText("Đang mượn");
                    } else {
                        setText("Đặt trước");
                    }
                    setGraphic(null);
                }
            }
        });
        TableColumn colTienPhat = new TableColumn("Tiền phạt");
        colTienPhat.setCellValueFactory(new PropertyValueFactory("tienPhat"));
        colTienPhat.setPrefWidth(100);

        this.tbViewPhieuMuon.setRowFactory(tv -> {
            TableRow<PhieuMuon> row = new TableRow<>();
            // Tạo ContextMenu
            ContextMenu contextMenu = new ContextMenu();
            // Tạo updateItem "update" để update sách
            MenuItem updateItem = new MenuItem("Thêm tiền phạt");
            updateItem.setOnAction(e -> {
                if (this.tbViewPhieuMuon.getSelectionModel().getSelectedItem() != null) {
                    try {
                        selectedRow = this.tbViewPhieuMuon.getSelectionModel().getSelectedItem();
                        // Tạo Stage mới
                        Stage stage = new Stage();
                        // Tạo Scene mới
                        Parent root = FXMLLoader.load(getClass().getResource("updateTienPhat.fxml"));
                        Scene scene = new Scene(root);
                        // Thiết lập Scene cho Stage mới
                        stage.setScene(scene);
                        stage.setTitle("Cập nhật tiền phạt");
                        // Xử lý sự kiện khi Stage mới bị đóng lại
                        stage.setOnHidden(event -> {
                            try {
                                loadTableDataPhieuMuon(null);
                                MessageBox.getBox("Notification", "Cập nhật tiền phạt thành công!", Alert.AlertType.INFORMATION).show();
                            } catch (SQLException ex) {
                                MessageBox.getBox("Notification", "Cập nhật thất bại !", Alert.AlertType.ERROR).show();
                                Logger.getLogger(MuonTraController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(MuonTraController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            // Thêm MenuItem vào ContextMenu
            contextMenu.getItems().addAll(updateItem);
            // Thiết lập ContextMenu cho TableRow
            row.setContextMenu(contextMenu);
            return row;
        });
        this.tbViewPhieuMuon.getColumns().addAll(colId, colMaSach, colMaDocGia, colTenDocGia, colTenSach, colSoLuong, colNgayMuon, colHanTra, colActive, colTienPhat);
    }

    private void loadTableDataDocGiaId(String kw) throws SQLException {
        List<DocGia> docGiaIds = g.getIdDocGia(kw);
        this.cbDocGia.getItems().clear();
        this.cbDocGia.setItems(FXCollections.observableList(docGiaIds));
    }

    private void loadTableDataPhieuMuon(String kw) throws SQLException {
        List<PhieuMuon> phieumuonList = p.getPhieuMuon(kw);
        this.tbViewPhieuMuon.getItems().clear();
        this.tbViewPhieuMuon.setItems(FXCollections.observableList(phieumuonList));
    }
    
    private void loadTableDataPhieuMuonId(String kw) throws SQLException {
        List<PhieuMuon> phieumuonList = p.searchPhieuMuonId(kw);
        this.tbViewPhieuMuon.getItems().clear();
        this.tbViewPhieuMuon.setItems(FXCollections.observableList(phieumuonList));
    }

    public void reFresh(ActionEvent evt) throws SQLException {
        List<DocGia> docGiaIds = g.getDocGias(null);
        List<BoPhan> boPhanList = g.getBoPhan();
        List<PhieuMuon> phieuMuonList = p.getPhieuMuon(null);
        // xóa toàn bộ dữ liệu cũ
        this.tbViewPhieuMuon.getItems().clear();
        this.tbViewPhieuMuon.setItems(FXCollections.observableList(phieuMuonList));
        this.cbBoPhan.getItems().clear();
        this.cbBoPhan.setItems(FXCollections.observableList(boPhanList));
        this.cbDocGia.getItems().clear();
        this.cbDocGia.setItems(FXCollections.observableList(docGiaIds));
        this.txtHanTra.setValue(null);
        loadTableDataBook(null);
    }
}
