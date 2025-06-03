package com.example.demo.repository;

import com.example.demo.entity.ve.DatVe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DatVeRepository extends JpaRepository<DatVe, Long> {
    List<DatVe> findByMaKhachHang(String maKhachHang);
    Optional<DatVe> findByMaDatVe(String maDatVe);
    List<DatVe> findByTrangThai(String trangThai);
    List<DatVe> findByNgayDatBetween(LocalDateTime start, LocalDateTime end);
    @Query(value = "SELECT * FROM dat_ve dv WHERE dv.ma_khach_hang =?1 AND dv.trang_thai =?2 ORDER BY dv.ngay_dat DESC;", nativeQuery = true)
    List<DatVe> findByMaKhachHangAndTrangThaiOrderByNgayDatDesc(String maKhachHang, String trangThai);

    List<DatVe> findByMaKhachHangAndMaDatVeInOrderByNgayDatDesc(String maKhachHang, List<String> maDatVeList);

}
