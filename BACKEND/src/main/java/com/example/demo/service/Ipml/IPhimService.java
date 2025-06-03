package com.example.demo.service.Ipml;

import com.example.demo.entity.phim.Phim;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPhimService {

    Phim taoPhim(Phim phim);

    List<Phim> layDanhSachPhim();

    Optional<Phim> timPhimTheoMa(String maPhim);

    List<Phim> timPhimTheoTrangThai(String trangThai);

    List<Phim> timPhimSapChieu();

    List<Phim> timPhimTrongKhoangThoiGian(LocalDate start, LocalDate end);

    Phim capNhatPhim(Phim phim);

    void xoaPhim(Long id);
}
