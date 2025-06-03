package com.example.demo.service.Ipml;

import com.example.demo.entity.account.KhachHang;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IKhachHangService {
    KhachHang taoKhachHang(KhachHang khachHang);

    List<KhachHang> layDanhSachKhachHang();

    Optional<KhachHang> timKhachHangTheoMa(String maKh);

    List<KhachHang> timKhachHangTheoTrangThai(String trangThai);

    KhachHang capNhatTrangThaiKhachHang(String maKh, String trangThai);
}
