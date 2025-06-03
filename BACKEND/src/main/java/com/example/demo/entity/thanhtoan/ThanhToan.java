package com.example.demo.entity.thanhtoan;

import com.example.demo.entity.ve.DatVe;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "thanh_toan")
public class ThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "so_tien", precision = 10, scale = 2, nullable = false)
    private BigDecimal soTien;

    @Column(name = "ngay_thanh_toan", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ngayThanhToan;

    @Column(name = "hinh_thuc", length = 50)
    private String hinhThuc;

    @Column(name = "ma_thanh_toan", unique = true, length = 50)
    private String maThanhToan;

    @Column(name = "trang_thai", length = 20)
    private String trangThai;

    @Column(name = "ma_dat_ve", length = 50)
    private String maDatVe;

    @ManyToOne
    @JoinColumn(name = "ma_dat_ve", referencedColumnName = "ma_dat_ve", insertable = false, updatable = false)
    private DatVe datVe;

    public ThanhToan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSoTien() {
        return soTien;
    }

    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }

    public LocalDateTime getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDateTime ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getHinhThuc() {
        return hinhThuc;
    }

    public void setHinhThuc(String hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public String getMaThanhToan() {
        return maThanhToan;
    }

    public void setMaThanhToan(String maThanhToan) {
        this.maThanhToan = maThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaDatVe() {
        return maDatVe;
    }

    public void setMaDatVe(String maDatVe) {
        this.maDatVe = maDatVe;
    }

    public DatVe getDatVe() {
        return datVe;
    }

    public void setDatVe(DatVe datVe) {
        this.datVe = datVe;
    }
}
