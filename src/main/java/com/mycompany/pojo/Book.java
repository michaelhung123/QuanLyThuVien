/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import java.sql.Date;
import java.util.UUID;

/**
 *
 * @author dell
 */
public class Book {

    private int id;
    private String name;
    private String tacGia;
    private String moTa;
    private int namXB;
    private String noiXB;
    private Date ngayNhap;
    private String viTri;
    private int categoryId;
    private String categoryString;


//    {
//        id = UUID.randomUUID().toString();
//    }

    public Book(int id,String name, String tacGia, String moTa, int namXB, String noiXB, Date ngayNhap, String viTri, int categoryId) {
        this.id = id;
        this.name = name;
        this.tacGia = tacGia;
        this.moTa = moTa;
        this.namXB = namXB;
        this.noiXB = noiXB;
        this.ngayNhap = ngayNhap;
        this.viTri = viTri;
        this.categoryId = categoryId;
    }
    
    public Book(String name, String tacGia, String moTa, int namXB, String noiXB, Date ngayNhap, String viTri, int categoryId) {
        this.name = name;
        this.tacGia = tacGia;
        this.moTa = moTa;
        this.namXB = namXB;
        this.noiXB = noiXB;
        this.ngayNhap = ngayNhap;
        this.viTri = viTri;
        this.categoryId = categoryId;
    }
    
     public Book(int id,String name, String tacGia, String moTa, int namXB, String noiXB, Date ngayNhap, String viTri, String categoryString) {
        this.id = id;
        this.name = name;
        this.tacGia = tacGia;
        this.moTa = moTa;
        this.namXB = namXB;
        this.noiXB = noiXB;
        this.ngayNhap = ngayNhap;
        this.viTri = viTri;
        this.categoryString = categoryString;
    }
    
    public Book() {
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the tacGia
     */
    public String getTacGia() {
        return tacGia;
    }

    /**
     * @param tacGia the tacGia to set
     */
    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    /**
     * @return the moTa
     */
    public String getMoTa() {
        return moTa;
    }

    /**
     * @param moTa the moTa to set
     */
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    /**
     * @return the namXB
     */
    public int getNamXB() {
        return namXB;
    }

    /**
     * @param namXB the namXB to set
     */
    public void setNamXB(int namXB) {
        this.namXB = namXB;
    }

    /**
     * @return the noiXB
     */
    public String getNoiXB() {
        return noiXB;
    }

    /**
     * @param noiXB the noiXB to set
     */
    public void setNoiXB(String noiXB) {
        this.noiXB = noiXB;
    }

    /**
     * @return the ngayNhap
     */
    public Date getNgayNhap() {
        return ngayNhap;
    }

    /**
     * @param ngayNhap the ngayNhap to set
     */
    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    /**
     * @return the viTri
     */
    public String getViTri() {
        return viTri;
    }

    /**
     * @param viTri the viTri to set
     */
        /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    /**
     * @return the categoryString
     */
    public String getCategoryString() {
        return categoryString;
    }

    /**
     * @param categoryString the categoryString to set
     */
    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }
    
   

}
