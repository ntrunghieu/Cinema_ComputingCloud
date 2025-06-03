package com.example.demo.entity.account;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tuoi_gioi_han")
public class TuoiGioiHan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_gioi_han", unique = true, length = 10, nullable = false)
    private String maGioiHan;

    @Column(name = "mo_ta", length = 255)
    private String moTa;

    @Column(name = "tuoi_toi_thieu")
    private Integer tuoiToiThieu;

    @Column(name = "tuoi_toi_da")
    private Integer tuoiToiDa;

    @Column(name = "mo_ta_chi_tiet", columnDefinition = "TEXT")
    private String moTaChiTiet;

    public TuoiGioiHan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaGioiHan() {
        return maGioiHan;
    }

    public void setMaGioiHan(String maGioiHan) {
        this.maGioiHan = maGioiHan;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getTuoiToiThieu() {
        return tuoiToiThieu;
    }

    public void setTuoiToiThieu(Integer tuoiToiThieu) {
        this.tuoiToiThieu = tuoiToiThieu;
    }

    public Integer getTuoiToiDa() {
        return tuoiToiDa;
    }

    public void setTuoiToiDa(Integer tuoiToiDa) {
        this.tuoiToiDa = tuoiToiDa;
    }

    public String getMoTaChiTiet() {
        return moTaChiTiet;
    }

    public void setMoTaChiTiet(String moTaChiTiet) {
        this.moTaChiTiet = moTaChiTiet;
    }
}
