package com.example.demo.service;

import com.example.demo.entity.phim.Phim;
import com.example.demo.repository.PhimRepository;
import com.example.demo.service.Ipml.IPhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PhimService implements IPhimService {
    @Autowired
    private PhimRepository phimRepository;

    @Transactional
    public Phim taoPhim(Phim phim) {
        // Kiểm tra trùng mã phim
        if (phimRepository.findByMaPhim(phim.getMaPhim()).isPresent()) {
            throw new RuntimeException("Mã phim đã tồn tại");
        }


        return phimRepository.save(phim);
    }

    public List<Phim> layDanhSachPhim() {
        return phimRepository.findAll();
    }

    public Optional<Phim> timPhimTheoMa(String maPhim) {
        return phimRepository.findByMaPhim(maPhim);
    }

    public List<Phim> timPhimTheoTrangThai(String trangThai) {
        return phimRepository.findByTrangThai(trangThai);
    }

    public List<Phim> timPhimSapChieu() {
        return phimRepository.findByTrangThai("sắp chiếu");
    }

    public List<Phim> timPhimTrongKhoangThoiGian(LocalDate start, LocalDate end) {
        return phimRepository.findByNgayPhatHanhBetween(start, end);
    }

    @Transactional
    public Phim capNhatPhim(Phim phim) {
        // Kiểm tra phim có tồn tại không
        phimRepository.findById(phim.getId())
                .orElseThrow(() -> new RuntimeException("Phim không tồn tại"));

        return phimRepository.save(phim);
    }

    @Transactional
    public void xoaPhim(Long id) {
        phimRepository.deleteById(id);
    }
}
