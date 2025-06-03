package com.example.demo.entity.ve;

import com.example.demo.entity.Ghe;
import com.example.demo.entity.LichChieu;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ve")
public class Ve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lich_chieu_id", nullable = false)
    private LichChieu lichChieu;

    @ManyToOne
    @JoinColumn(name = "ghe_id", nullable = false)
    private Ghe ghe;

    @ManyToOne
    @JoinColumn(name = "dat_ve_id")
    private DatVe datVe;

    @Column(name = "ma_ve", unique = true, length = 20)
    private String maVe;

    @Column(name = "gia", precision = 10, scale = 2, nullable = false)
    private BigDecimal gia;

    @Column(name = "trang_thai", length = 20)
    private String trangThai;

    @Column(name = "ma_loai_ve", length = 20)
    private String maLoaiVe;

    public Ve() {
    }

    public Ve(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LichChieu getLichChieu() {
        return lichChieu;
    }

    public void setLichChieu(LichChieu lichChieu) {
        this.lichChieu = lichChieu;
    }

    public Ghe getGhe() {
        return ghe;
    }

    public void setGhe(Ghe ghe) {
        this.ghe = ghe;
    }

    public DatVe getDatVe() {
        return datVe;
    }

    public void setDatVe(DatVe datVe) {
        this.datVe = datVe;
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaLoaiVe() {
        return maLoaiVe;
    }

    public void setMaLoaiVe(String maLoaiVe) {
        this.maLoaiVe = maLoaiVe;
    }



}
