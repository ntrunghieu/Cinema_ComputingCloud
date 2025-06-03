package com.example.demo.DTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GheDTO {
    private String tenPhim;
    private String anhPoster;
    private LocalDate ngayChieu;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private String phong;
    private int tongSoGhe;
    private int soGheDaDat;
    private List<String> danhSachGheDaDat;

    public GheDTO() {
    }

    public GheDTO(String tenPhim, String anhPoster, LocalDate ngayChieu, LocalTime gioBatDau, LocalTime gioKetThuc, String phong, int tongSoGhe, int soGheDaDat, List<String> danhSachGheDaDat) {
        this.tenPhim = tenPhim;
        this.anhPoster = anhPoster;
        this.ngayChieu = ngayChieu;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.phong = phong;
        this.tongSoGhe = tongSoGhe;
        this.soGheDaDat = soGheDaDat;
        this.danhSachGheDaDat = danhSachGheDaDat;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getAnhPoster() {
        return anhPoster;
    }

    public void setAnhPoster(String anhPoster) {
        this.anhPoster = anhPoster;
    }

    public LocalDate getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(LocalDate ngayChieu) {
        this.ngayChieu = ngayChieu;
    }

    public LocalTime getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(LocalTime gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public LocalTime getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(LocalTime gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public int getTongSoGhe() {
        return tongSoGhe;
    }

    public void setTongSoGhe(int tongSoGhe) {
        this.tongSoGhe = tongSoGhe;
    }

    public int getSoGheDaDat() {
        return soGheDaDat;
    }

    public void setSoGheDaDat(int soGheDaDat) {
        this.soGheDaDat = soGheDaDat;
    }

    public List<String> getDanhSachGheDaDat() {
        return danhSachGheDaDat;
    }

    public void setDanhSachGheDaDat(List<String> danhSachGheDaDat) {
        this.danhSachGheDaDat = danhSachGheDaDat;
    }
}
