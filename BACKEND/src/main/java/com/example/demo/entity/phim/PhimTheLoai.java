package com.example.demo.entity.phim;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "phim_the_loai")
public class PhimTheLoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "phim_id", referencedColumnName = "id")
    private Phim phim;

    @ManyToOne
    @JoinColumn(name = "the_loai_id", referencedColumnName = "id")
    private TheLoaiPhim theLoai;
}
