package com.example.demo.repository;

import com.example.demo.entity.phim.Phim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhimRepository extends JpaRepository<Phim, Long> {

    Optional<Phim> findById(Long id);
    Optional<Phim> findByMaPhim(String maPhim);

    List<Phim> findByTrangThai(String trangThai);
    List<Phim> findByNgayPhatHanhBetween(LocalDate start, LocalDate end);
    List<Phim> findByTenPhimContaining(String tenPhim);

    @Query(value = "select gioi_han_tuoi_id from phim", nativeQuery = true)
    List<Phim> gioiHanTuoi();
    @Query(value = "SELECT ma_phim FROM phim ORDER BY ma_phim DESC LIMIT 1", nativeQuery = true)
    String findLatestCode();
}
