package com.example.demo.repository;

import com.example.demo.entity.Ghe;
import com.example.demo.entity.LichChieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GheRepository extends JpaRepository<Ghe, Long> {
    int countByPhongId(Long phongId);
    List<Ghe> findByPhong_Id(Long phongId);
    Optional<Ghe> findByPhongIdAndMaGhe(Long phongId, String maGhe);
    List<Ghe> findByTrangThai(String trangThai);
    List<Ghe> findByLoaiGhe(String loaiGhe);

    @Query(value = "SELECT * FROM ghe where id =?1;", nativeQuery = true)
    List<LichChieu> findGheByMaGhe(Long id);

    Optional<Ghe> findByMaGheAndPhongId(String seatCode, Long roomId);
}
