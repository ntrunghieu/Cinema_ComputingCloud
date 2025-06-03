package com.example.demo.service.Ipml;

import com.example.demo.entity.LichChieu;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ILichChieuService {
    LichChieu taoLichChieu(LichChieu lichChieu);

    List<LichChieu> layDanhSachLichChieu();

    List<LichChieu> timLichChieuTheoPhim(Long phimId);

    List<LichChieu> timLichChieuTheoPhong(Long phongId);

    List<LichChieu> timLichChieuTheoNgay(LocalDate ngayChieu);

    LichChieu capNhatTrangThaiLichChieu(String maLichChieu, String trangThai);
}
