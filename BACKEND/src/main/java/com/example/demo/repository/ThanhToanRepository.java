package com.example.demo.repository;

import com.example.demo.entity.thanhtoan.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Long> {
    Optional<ThanhToan> findByMaThanhToan(String maThanhToan);
    List<ThanhToan> findByMaDatVe(String maDatVe);
    List<ThanhToan> findByTrangThai(String trangThai);
    List<ThanhToan> findByNgayThanhToanBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT t.ma_dat_ve FROM thanh_toan t WHERE t.trang_thai = :trangThai", nativeQuery = true)
    List<String> findMaDatVeByTrangThai(@Param("trangThai") String trangThai);
}
