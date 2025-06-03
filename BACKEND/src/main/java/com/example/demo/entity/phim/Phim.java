package com.example.demo.entity.phim;

import com.example.demo.entity.account.TuoiGioiHan;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "phim")
public class Phim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_phim", unique = true, length = 20)
    private String maPhim;

    @Column(name = "ten_phim", nullable = false, length = 255)
    private String tenPhim;

    @Column(name = "thoi_luong")
    private Integer thoiLuong;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "dao_dien", length = 100)
    private String daoDien;

    @Column(name = "dien_vien", columnDefinition = "TEXT")
    private String dienVien;

    @Column(name = "quoc_gia", length = 50)
    private String quocGia;

    @Column(name = "ngay_phat_hanh")
    private LocalDate ngayPhatHanh;

    @Column(name = "trang_thai", length = 20)
    private String trangThai;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "gioi_han_tuoi_id", referencedColumnName = "id")
    private TuoiGioiHan gioiHanTuoi;

    @Column(name = "anh_poster", length = 255)
    private String anh;

    @Column(name = "trailer", length = 255)
    private String trailer;

    public Phim() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public Integer getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(Integer thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getDaoDien() {
        return daoDien;
    }

    public void setDaoDien(String daoDien) {
        this.daoDien = daoDien;
    }

    public String getDienVien() {
        return dienVien;
    }

    public void setDienVien(String dienVien) {
        this.dienVien = dienVien;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public LocalDate getNgayPhatHanh() {
        return ngayPhatHanh;
    }

    public void setNgayPhatHanh(LocalDate ngayPhatHanh) {
        this.ngayPhatHanh = ngayPhatHanh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public TuoiGioiHan getGioiHanTuoi() {
        return gioiHanTuoi;
    }

    public void setGioiHanTuoi(TuoiGioiHan gioiHanTuoi) {
        this.gioiHanTuoi = gioiHanTuoi;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
