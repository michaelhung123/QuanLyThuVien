/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import java.sql.Date;

/**
 *
 * @author dell
 */
public class PhieuMuon {
    private int id;
    private int bookId;
    private String docGiaId;
    private int soLuong = 1;
    private Date ngayMuon;
    private Date hanTra;
    private String tenDocGia;
    private String tenSach;

    public PhieuMuon() {
    }
    
    public PhieuMuon(int soLuong,Date hanTra, int bookId, String docGiaId){
        this.soLuong = soLuong;
        this.hanTra = hanTra;
        this.bookId = bookId;
        this.docGiaId = docGiaId;
    }

    public PhieuMuon(int id, int bookId, String docGiaId, int soLuong, Date ngayMuon, Date hanTra) {
        this.id = id;
        this.bookId = bookId;
        this.docGiaId = docGiaId;
        this.soLuong = soLuong;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
    }

    public PhieuMuon(int id, int bookId, String docGiaId, int soLuong, Date ngayMuon, Date hanTra, String tenDocGia, String tenSach) {
        this.id = id;
        this.bookId = bookId;
        this.docGiaId = docGiaId;
        this.soLuong = soLuong;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
        this.tenDocGia = tenDocGia;
        this.tenSach = tenSach;
    }
    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the bookId
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * @return the docGiaId
     */
    public String getDocGiaId() {
        return docGiaId;
    }

    /**
     * @param docGiaId the docGiaId to set
     */
    public void setDocGiaId(String docGiaId) {
        this.docGiaId = docGiaId;
    }

    /**
     * @return the soLuong
     */
    public int getSoLuong() {
        return soLuong;
    }

    /**
     * @param soLuong the soLuong to set
     */
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    /**
     * @return the ngayMuon
     */
    public Date getNgayMuon() {
        return ngayMuon;
    }

    /**
     * @param ngayMuon the ngayMuon to set
     */
    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    /**
     * @return the hanTra
     */
    public Date getHanTra() {
        return hanTra;
    }

    /**
     * @param hanTra the hanTra to set
     */
    public void setHanTra(Date hanTra) {
        this.hanTra = hanTra;
    }

    /**
     * @return the tenDocGia
     */
    public String getTenDocGia() {
        return tenDocGia;
    }

    /**
     * @param tenDocGia the tenDocGia to set
     */
    public void setTenDocGia(String tenDocGia) {
        this.tenDocGia = tenDocGia;
    }

    /**
     * @return the tenSach
     */
    public String getTenSach() {
        return tenSach;
    }

    /**
     * @param tenSach the tenSach to set
     */
    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
