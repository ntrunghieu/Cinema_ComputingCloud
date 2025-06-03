package com.example.demo.service;

import com.example.demo.entity.LichChieu;
import com.example.demo.repository.LichChieuRepository;
import com.example.demo.repository.PhimRepository;
import com.example.demo.repository.PhongRepository;
import com.example.demo.service.Ipml.ILichChieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LichChieuService implements ILichChieuService {
    @Autowired
    private LichChieuRepository lichChieuRepository;

    @Autowired
    private PhimRepository phimRepository;

    @Autowired
    private PhongRepository phongRepository;

    @Transactional
    public LichChieu taoLichChieu(LichChieu lichChieu) {
        // Kiểm tra phim và phòng có tồn tại không
        phimRepository.findById(lichChieu.getPhim().getId())
                .orElseThrow(() -> new RuntimeException("Phim không tồn tại"));

        phongRepository.findById(lichChieu.getPhong().getId())
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));

        // Kiểm tra trùng mã lịch chiếu
        if (lichChieuRepository.findByMaLichChieu(lichChieu.getMaLichChieu()).isPresent()) {
            throw new RuntimeException("Mã lịch chiếu đã tồn tại");
        }


        return lichChieuRepository.save(lichChieu);
    }

    public List<LichChieu> layDanhSachLichChieu() {
        return lichChieuRepository.findAll();
    }

    public List<LichChieu> timLichChieuTheoPhim(Long phimId) {
        return lichChieuRepository.findByPhim_Id(phimId);
    }

    public List<LichChieu> timLichChieuTheoPhong(Long phongId) {
        return lichChieuRepository.findByPhong_Id(phongId);
    }

    public List<LichChieu> timLichChieuTheoNgay(LocalDate ngayChieu) {
        return lichChieuRepository.findByNgayChieu(ngayChieu);
    }

    @Transactional
    public LichChieu capNhatTrangThaiLichChieu(String maLichChieu, String trangThai) {
        LichChieu lichChieu = lichChieuRepository.findByMaLichChieu(maLichChieu)
                .orElseThrow(() -> new RuntimeException("Lịch chiếu không tồn tại"));

        lichChieu.setTrangThai(trangThai);
        return lichChieuRepository.save(lichChieu);
    }
}
