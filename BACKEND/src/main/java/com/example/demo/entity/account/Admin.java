package com.example.demo.entity.account;

import com.example.demo.entity.account.NguoiDung;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_admin", unique = true, length = 20)
    private String maAdmin;

    @Column(name = "vai_tro", length = 50)
    private String vaiTro;

    @OneToOne
    @JoinColumn(name = "ma_admin", referencedColumnName = "ma_nd", insertable = false, updatable = false)
    private NguoiDung nguoiDung;
}
