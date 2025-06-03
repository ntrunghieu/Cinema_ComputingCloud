package com.example.demo.service;

import com.example.demo.entity.account.KhachHang;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.NguoiDungRepository;
import com.example.demo.service.Ipml.IKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class KhachHangService implements IKhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Transactional
    public KhachHang taoKhachHang(KhachHang khachHang) {
        // Kiểm tra xem người dùng có tồn tại không
        nguoiDungRepository.findByMaNd(khachHang.getMaKh())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        return khachHangRepository.save(khachHang);
    }

    public List<KhachHang> layDanhSachKhachHang() {
        return khachHangRepository.findAll();
    }

    public Optional<KhachHang> timKhachHangTheoMa(String maKh) {
        return khachHangRepository.findByMaKh(maKh);
    }

    public List<KhachHang> timKhachHangTheoTrangThai(String trangThai) {
        return khachHangRepository.findByTrangThai(trangThai);
    }

    @Transactional
    public KhachHang capNhatTrangThaiKhachHang(String maKh, String trangThai) {
        KhachHang khachHang = khachHangRepository.findByMaKh(maKh)
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));

        khachHang.setTrangThai(trangThai);
        return khachHangRepository.save(khachHang);
    }
}
