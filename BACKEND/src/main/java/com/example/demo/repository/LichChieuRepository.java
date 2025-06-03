package com.example.demo.repository;

import com.example.demo.entity.LichChieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LichChieuRepository extends JpaRepository<LichChieu, Long> {

    @Query(value = "SELECT * FROM lich_chieu where phim_id =?1;", nativeQuery = true)
    List<LichChieu> findLichChieuByIdPhim1(Long phimId);

    List<LichChieu> findByPhim_Id(Long phimId);
    List<LichChieu> findByPhong_Id(Long phongId);
    List<LichChieu> findByNgayChieu(LocalDate ngayChieu);
//    List<LichChieu> findByMaLichChieu(Long maLichChieu);
    Optional<LichChieu> findByMaLichChieu(String maLichChieu);

    @Query(value = "SELECT * FROM lich_chieu where id =?1;", nativeQuery = true)
    List<LichChieu> findByMaLichChieu1(Long id);

    List<LichChieu> findByTrangThai(String trangThai);
}
