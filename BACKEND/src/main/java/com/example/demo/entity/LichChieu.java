package com.example.demo.entity;

import com.example.demo.entity.phim.Phim;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "lich_chieu")
public class LichChieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "phim_id", nullable = false)
    private Phim phim;

    @ManyToOne
    @JoinColumn(name = "phong_id", nullable = false)
    private Phong phong;

    @Column(name = "ngay_chieu", nullable = false)
    private LocalDate ngayChieu;

    @Column(name = "gio_bat_dau", nullable = false)
    private LocalTime gioBatDau;

    @Column(name = "gio_ket_thuc", nullable = false)
    private LocalTime gioKetThuc;

    @Column(name = "ma_lich_chieu", unique = true, length = 20)
    private String maLichChieu;

    @Column(name = "gia_ve_mac_dinh", precision = 10, scale = 2)
    private BigDecimal giaVeMacDinh;

    @Column(name = "trang_thai", length = 20)
    private String trangThai;

    public LichChieu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Phim getPhim() {
        return phim;
    }

    public void setPhim(Phim phim) {
        this.phim = phim;
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
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

    public String getMaLichChieu() {
        return maLichChieu;
    }

    public void setMaLichChieu(String maLichChieu) {
        this.maLichChieu = maLichChieu;
    }

    public BigDecimal getGiaVeMacDinh() {
        return giaVeMacDinh;
    }

    public void setGiaVeMacDinh(BigDecimal giaVeMacDinh) {
        this.giaVeMacDinh = giaVeMacDinh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
