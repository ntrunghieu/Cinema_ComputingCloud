package com.example.demo.service;

import com.example.demo.DTO.GheDTO;
import com.example.demo.entity.Ghe;
import com.example.demo.entity.LichChieu;
import com.example.demo.entity.phim.Phim;
import com.example.demo.entity.ve.Ve;
import com.example.demo.repository.*;
import com.example.demo.service.Ipml.IGheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GheService implements IGheService {
    @Autowired
    private GheRepository gheRepository;

    @Autowired
    private PhongRepository phongRepository;

    @Autowired
    private PhimRepository phimRepository;

    @Autowired
    private LichChieuRepository lichChieuRepository;

    @Autowired
    private VeRepository veRepository;

    @Override
    public GheDTO getSeatInfo(Long scheduleId, Long roomId, Long phimId) {
        // Lấy thông tin phim
        Phim phim = phimRepository.findById(phimId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phim"));

        // Lấy lịch chiếu (giả định bạn có entity LichChieu)
        LichChieu lichChieu = lichChieuRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch chiếu"));

        // Lấy tên phòng từ LichChieu hoặc từ Room entity
        String tenPhong = lichChieu.getPhong().getTenPhong(); // Giả định bạn có getPhong()

        // Lấy tổng số ghế trong phòng
        int tongSoGhe = gheRepository.countByPhongId(roomId);

        // Lấy danh sách ghế đã được đặt trong lịch chiếu
        List<String> danhSachVe = veRepository.findMaGheDaDatByLichChieuId(scheduleId);

        return new GheDTO(
                phim.getTenPhim(),
                phim.getAnh(),
                lichChieu.getNgayChieu(),
                lichChieu.getGioBatDau(),
                lichChieu.getGioKetThuc(),
                tenPhong,
                tongSoGhe,
                danhSachVe.size(),
                danhSachVe
        );
    }

    @Transactional
    public Ghe taoGhe(Ghe ghe) {
        // Kiểm tra phòng có tồn tại không
        phongRepository.findById(ghe.getPhong().getId())
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));

        // Kiểm tra trùng mã ghế trong cùng một phòng
        if (gheRepository.findByPhongIdAndMaGhe(ghe.getPhong().getId(), ghe.getMaGhe()).isPresent()) {
            throw new RuntimeException("Mã ghế đã tồn tại trong phòng này");
        }



        return gheRepository.save(ghe);
    }

    public List<Ghe> layDanhSachGhe() {
        return gheRepository.findAll();
    }

    public List<Ghe> timGheTheoPhong(Long phongId) {
        return gheRepository.findByPhong_Id(phongId);
    }

    public Optional<Ghe> timGheTheoPhongVaMaGhe(Long phongId, String maGhe) {
        return gheRepository.findByPhongIdAndMaGhe(phongId, maGhe);
    }

    public List<Ghe> timGheTheoTrangThai(String trangThai) {
        return gheRepository.findByTrangThai(trangThai);
    }

    public List<Ghe> timGheTheoLoai(String loaiGhe) {
        return gheRepository.findByLoaiGhe(loaiGhe);
    }

    @Transactional
    public Ghe capNhatTrangThaiGhe(Long phongId, String maGhe, String trangThai) {
        Ghe ghe = gheRepository.findByPhongIdAndMaGhe(phongId, maGhe)
                .orElseThrow(() -> new RuntimeException("Ghế không tồn tại"));

        ghe.setTrangThai(trangThai);
        return gheRepository.save(ghe);
    }
}
