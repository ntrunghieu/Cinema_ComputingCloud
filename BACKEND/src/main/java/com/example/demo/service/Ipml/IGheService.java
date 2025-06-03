package com.example.demo.service.Ipml;

import com.example.demo.DTO.GheDTO;
import com.example.demo.entity.Ghe;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IGheService {
    GheDTO getSeatInfo(Long scheduleId, Long roomId, Long phimId);
    Ghe taoGhe(Ghe ghe);

    List<Ghe> layDanhSachGhe();

    List<Ghe> timGheTheoPhong(Long phongId);

    Optional<Ghe> timGheTheoPhongVaMaGhe(Long phongId, String maGhe);

    List<Ghe> timGheTheoTrangThai(String trangThai);

    List<Ghe> timGheTheoLoai(String loaiGhe);

    Ghe capNhatTrangThaiGhe(Long phongId, String maGhe, String trangThai);
}
