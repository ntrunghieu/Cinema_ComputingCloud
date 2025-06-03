package com.example.demo.repository;

import com.example.demo.entity.LichChieu;
import com.example.demo.entity.ve.Ve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VeRepository extends JpaRepository<Ve, Long> {

    @Query(value = "SELECT ghe.ma_ghe " +
            "FROM ve " +
            "JOIN ghe ON ve.ghe_id = ghe.id " +
            "WHERE ve.lich_chieu_id = :lichChieuId AND ve.trang_thai != 'Còn trống'",
            nativeQuery = true)
    List<String> findMaGheDaDatByLichChieuId(@Param("lichChieuId") Long lichChieuId);

    List<Ve> findByLichChieuId(Long lichChieuId);
    List<Ve> findByLichChieu_Id(Long lichChieuId);
    List<Ve> findByGhe_Id(Long gheId);
    Optional<Ve> findByMaVe(String maVe);
    List<Ve> findByTrangThai(String trangThai);
    List<Ve> findByDatVe_Id(Long datVeId);

//    Optional<Ve> findByLichChieuIdAndGheId(Long lichChieuId, Long gheId);

    @Query(value = "SELECT * FROM ve WHERE lich_chieu_id = :lichChieuId AND ghe_id = :gheId AND trang_thai != 'Còn trống'  LIMIT 1", nativeQuery = true)
    Optional<Ve> findVeDaDat(@Param("lichChieuId") Long lichChieuId, @Param("gheId") Long gheId);

//    List<Ve> findByMaGheAndPhongId(Long , Long );

    /**
     * Tìm vé theo lịch chiếu và ghế
     */
    @Query("SELECT v FROM Ve v WHERE v.lichChieu.id = :lichChieuId AND v.ghe.id = :gheId")
    Optional<Ve> findByLichChieuIdAndGheId(@Param("lichChieuId") Long lichChieuId, @Param("gheId") Long gheId);

    /**
     * Tìm các vé còn trống theo lịch chiếu và danh sách ID ghế
     */
    @Query("SELECT v FROM Ve v WHERE v.lichChieu.id = :lichChieuId AND v.ghe.id IN :gheIds AND v.trangThai = 'Còn trống'")
    List<Ve> findEmptyTicketsByLichChieuAndGheIds(@Param("lichChieuId") Long lichChieuId, @Param("gheIds") List<Long> gheIds);

    /**
     * Đếm số vé còn trống theo lịch chiếu và danh sách ID ghế
     */
    @Query("SELECT COUNT(v) FROM Ve v WHERE v.lichChieu.id = :lichChieuId AND v.ghe.id IN :gheIds AND v.trangThai = 'Còn trống'")
    int countEmptyTicketsByLichChieuAndGheIds(@Param("lichChieuId") Long lichChieuId, @Param("gheIds") List<Long> gheIds);

    /**
     * Cập nhật trạng thái và đơn đặt vé cho nhiều vé
     */
    @Modifying
    @Query("UPDATE Ve v SET v.datVe.id = :datVeId, v.trangThai = :trangThai WHERE v.lichChieu.id = :lichChieuId AND v.ghe.id IN :gheIds AND v.trangThai = 'Còn trống'")
    int updateTicketsForBooking(@Param("datVeId") Long datVeId, @Param("trangThai") String trangThai,
                                @Param("lichChieuId") Long lichChieuId, @Param("gheIds") List<Long> gheIds);



    @Query(value = "SELECT * FROM ve where id =?1;", nativeQuery = true)
    List<Ve> findByDatVeId(Long id);
}