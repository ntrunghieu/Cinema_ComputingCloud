package com.example.demo.entity.phim;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "the_loai_phim")
public class TheLoaiPhim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_the_loai", nullable = false, length = 100)
    private String tenTheLoai;

    @Column(name = "ma_the_loai", unique = true, length = 20)
    private String maTheLoai;
}