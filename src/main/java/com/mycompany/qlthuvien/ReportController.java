/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien;

import com.mycompany.pojo.PhieuMuon;
import static com.mycompany.qlthuvien.AddBookController.s;
import static com.mycompany.qlthuvien.DocGiaController.g;
import com.mycompany.services.PhieuMuonService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author dell
 */
public class ReportController implements Initializable {

    PhieuMuonService p = new PhieuMuonService();
    @FXML
    private PieChart chartReport;
    @FXML
    private TableView<PhieuMuon> tbViewPhieuMuon;
    ObservableList<PieChart.Data> pieChartData;
    @FXML
    private Label lbThongkeSach;
    @FXML
    private Label lbThongkeDocGia;
    @FXML
    private Label lbThongkePhieuMuon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadTableColumnsPhieuMuon();
            loadTableDataPhieuMuon(null);
            loadPieChartData();

            this.lbThongkeSach.setText(String.valueOf(s.getTotalBook()));
            this.lbThongkeDocGia.setText(String.valueOf(g.getTotalDocGia()));
            this.lbThongkePhieuMuon.setText(String.valueOf(p.getTotalPhieuMuon()));
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    if (isActive) {
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

        this.tbViewPhieuMuon.getColumns()
                .addAll(colId, colMaSach, colMaDocGia, colTenDocGia, colTenSach, colSoLuong, colNgayMuon, colHanTra, colActive, colTienPhat);
    }

    private void loadTableDataPhieuMuon(String kw) throws SQLException {
        List<PhieuMuon> phieumuonList = p.getPhieuMuon(kw);
        this.tbViewPhieuMuon.getItems().clear();
        this.tbViewPhieuMuon.setItems(FXCollections.observableList(phieumuonList));
    }

    private void loadPieChartData() throws SQLException {
        pieChartData = chartReport.getData();

        double total = p.getActiveTotal();
//        double percentDaTra = ((p.getActiveTotal() - (p.getActiveDangMuon() - p.getActiveDatTruoc())) / total) * 100;
        double percentDangMuon = (p.getActiveDangMuon() / total) * 100;
        double percentDatTruoc = (p.getActiveDatTruoc() / total) * 100;

        // Tạo các đối tượng PieChart.Data mới
        PieChart.Data dataDangMuon = new PieChart.Data("Đang mượn (" + String.format("%.2f", percentDangMuon) + "%)", p.getActiveDangMuon());
        PieChart.Data dataDatTruoc = new PieChart.Data("Đặt trước (" + String.format("%.2f", percentDatTruoc) + "%)", p.getActiveDatTruoc());
//        PieChart.Data dataDaTra = new PieChart.Data("Đã trả (" + String.format("%.2f", percentDaTra) + "%)", p.getActiveTotal() - (p.getActiveDangMuon() - p.getActiveDatTruoc()));

        // Thêm các đối tượng mới vào danh sách pieChartData
        pieChartData.add(dataDangMuon);
        pieChartData.add(dataDatTruoc);
//        pieChartData.add(dataDaTra);

        // Cập nhật PieChart với dữ liệu mới
        chartReport.setData(pieChartData);
    }

    public void reFresh(ActionEvent evt) throws SQLException {
        List<PhieuMuon> phieuMuonList = p.getPhieuMuon(null);
        // xóa toàn bộ dữ liệu cũ
        this.tbViewPhieuMuon.getItems().clear();
        this.tbViewPhieuMuon.setItems(FXCollections.observableList(phieuMuonList));
        loadTableDataPhieuMuon(null);

        chartReport.getData().clear();
        chartReport.setData(pieChartData);
        loadPieChartData();

        this.lbThongkeSach.setText(String.valueOf(s.getTotalBook()));
        this.lbThongkeDocGia.setText(String.valueOf(g.getTotalDocGia()));
        this.lbThongkePhieuMuon.setText(String.valueOf(p.getTotalPhieuMuon()));
    }

}
