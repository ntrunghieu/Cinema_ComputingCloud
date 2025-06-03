package com.example.demo.service.Ipml;

import com.example.demo.entity.thanhtoan.ThanhToan;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IThanhToanService {
    ThanhToan taoThanhToan(ThanhToan thanhToan);

    List<ThanhToan> layDanhSachThanhToan();

    List<ThanhToan> timThanhToanTheoDatVe(String maDatVe);

    Optional<ThanhToan> timThanhToanTheoMa(String maThanhToan);

    List<ThanhToan> timThanhToanTheoTrangThai(String trangThai);

    List<ThanhToan> timThanhToanTrongKhoangThoiGian(LocalDateTime start, LocalDateTime end);

    ThanhToan capNhatTrangThaiThanhToan(String maThanhToan, String trangThai);
}
