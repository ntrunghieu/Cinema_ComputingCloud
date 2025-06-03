package com.example.demo.service;

import com.example.demo.entity.thanhtoan.ThanhToan;
import com.example.demo.repository.DatVeRepository;
import com.example.demo.repository.ThanhToanRepository;
import com.example.demo.service.Ipml.IThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ThanhToanService implements IThanhToanService {
    @Autowired
    private ThanhToanRepository thanhToanRepository;

    @Autowired
    private DatVeRepository datVeRepository;

    @Transactional
    public ThanhToan taoThanhToan(ThanhToan thanhToan) {
        // Kiểm tra đặt vé có tồn tại không
        datVeRepository.findByMaDatVe(thanhToan.getMaDatVe())
                .orElseThrow(() -> new RuntimeException("Đặt vé không tồn tại"));

        // Kiểm tra trùng mã thanh toán
        if (thanhToanRepository.findByMaThanhToan(thanhToan.getMaThanhToan()).isPresent()) {
            throw new RuntimeException("Mã thanh toán đã tồn tại");
        }

        // Mặc định trạng thái là Đang xử lý
        if (thanhToan.getTrangThai() == null) {
            thanhToan.setTrangThai("Đang xử lý");
        }

        // Đặt ngày thanh toán là thời gian hiện tại nếu chưa được set
        if (thanhToan.getNgayThanhToan() == null) {
            thanhToan.setNgayThanhToan(LocalDateTime.now());
        }

        return thanhToanRepository.save(thanhToan);
    }

    public List<ThanhToan> layDanhSachThanhToan() {
        return thanhToanRepository.findAll();
    }

    public List<ThanhToan> timThanhToanTheoDatVe(String maDatVe) {
        return thanhToanRepository.findByMaDatVe(maDatVe);
    }

    public Optional<ThanhToan> timThanhToanTheoMa(String maThanhToan) {
        return thanhToanRepository.findByMaThanhToan(maThanhToan);
    }

    public List<ThanhToan> timThanhToanTheoTrangThai(String trangThai) {
        return thanhToanRepository.findByTrangThai(trangThai);
    }

    public List<ThanhToan> timThanhToanTrongKhoangThoiGian(LocalDateTime start, LocalDateTime end) {
        return thanhToanRepository.findByNgayThanhToanBetween(start, end);
    }

    @Transactional
    public ThanhToan capNhatTrangThaiThanhToan(String maThanhToan, String trangThai) {
        ThanhToan thanhToan = thanhToanRepository.findByMaThanhToan(maThanhToan)
                .orElseThrow(() -> new RuntimeException("Thanh toán không tồn tại"));

        thanhToan.setTrangThai(trangThai);
        return thanhToanRepository.save(thanhToan);
    }
}
