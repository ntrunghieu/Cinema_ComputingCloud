package com.example.demo.entity.account;

import com.example.demo.entity.account.NguoiDung;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "khach_hang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_kh", unique = true, length = 20)
    private String maKh;

    @Column(name = "trang_thai", length = 20)
    private String trangThai;

    @OneToOne
    @JoinColumn(name = "ma_kh", referencedColumnName = "ma_nd", insertable = false, updatable = false)
    private NguoiDung nguoiDung;

    public KhachHang() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
}
