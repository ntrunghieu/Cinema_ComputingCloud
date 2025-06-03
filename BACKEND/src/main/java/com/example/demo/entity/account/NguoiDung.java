package com.example.demo.entity.account;

import com.example.demo.converter.GioiTinhConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "nguoi_dung")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "mat_khau", nullable = false, length = 255)
    private String matKhau;

    @Column(name = "ten_nd", length = 100)
    private String tenNd;

    @Column(name = "ma_nd", unique = true, length = 20)
    private String maNd;

    @Column(name = "so_dien_thoai", length = 15)
    private String soDienThoai;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "gioi_tinh", length = 20)
    private String gioiTinh;

    @Column(name = "tuoi")
    private Integer tuoi;


    @Column(name = "gioi_han_tuoi", length = 20)
    private String gioiHanTuoi;

    @Column(name = "do_tuoi_xac_thuc")
    private Boolean doTuoiXacThuc;

    public NguoiDung() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenNd() {
        return tenNd;
    }

    public void setTenNd(String tenNd) {
        this.tenNd = tenNd;
    }

    public String getMaNd() {
        return maNd;
    }

    public void setMaNd(String maNd) {
        this.maNd = maNd;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Integer getTuoi() {
        return tuoi;
    }

    public void setTuoi(Integer tuoi) {
        this.tuoi = tuoi;
    }

    public String getGioiHanTuoi() {
        return gioiHanTuoi;
    }

    public void setGioiHanTuoi(String gioiHanTuoi) {
        this.gioiHanTuoi = gioiHanTuoi;
    }

    public Boolean getDoTuoiXacThuc() {
        return doTuoiXacThuc;
    }

    public void setDoTuoiXacThuc(Boolean doTuoiXacThuc) {
        this.doTuoiXacThuc = doTuoiXacThuc;
    }
}
