package com.example.demo.service;

import com.example.demo.entity.ve.DatVe;
import com.example.demo.entity.ve.Ve;
import com.example.demo.repository.GheRepository;
import com.example.demo.repository.LichChieuRepository;
import com.example.demo.repository.VeRepository;
import com.example.demo.service.Ipml.IDatVeService;
import com.example.demo.service.Ipml.IVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VeService implements IVeService {
    @Autowired
    private VeRepository veRepository;

    @Autowired
    private LichChieuRepository lichChieuRepository;

    @Autowired
    private GheRepository gheRepository;

    @Transactional
    public Ve taoVe(Ve ve) {
        // Kiểm tra lịch chiếu và ghế có tồn tại không
        lichChieuRepository.findById(ve.getLichChieu().getId())
                .orElseThrow(() -> new RuntimeException("Lịch chiếu không tồn tại"));

        gheRepository.findById(ve.getGhe().getId())
                .orElseThrow(() -> new RuntimeException("Ghế không tồn tại"));

        // Kiểm tra trùng mã vé
        if (veRepository.findByMaVe(ve.getMaVe()).isPresent()) {
            throw new RuntimeException("Mã vé đã tồn tại");
        }



        return veRepository.save(ve);
    }

    public List<Ve> layDanhSachVe() {
        return veRepository.findAll();
    }

    public List<Ve> timVeTheoLichChieu(Long lichChieuId) {
        return veRepository.findByLichChieu_Id(lichChieuId);
    }

    public List<Ve> timVeTheoGhe(Long gheId) {
        return veRepository.findByGhe_Id(gheId);
    }

    public Optional<Ve> timVeTheoMa(String maVe) {
        return veRepository.findByMaVe(maVe);
    }

    public List<Ve> timVeTheoTrangThai(String trangThai) {
        return veRepository.findByTrangThai(trangThai);
    }

    public List<Ve> timVeTheoDatVe(Long datVeId) {
        return veRepository.findByDatVe_Id(datVeId);
    }

    @Transactional
    public Ve capNhatTrangThaiVe(String maVe, String trangThai) {
        Ve ve = veRepository.findByMaVe(maVe)
                .orElseThrow(() -> new RuntimeException("Vé không tồn tại"));

        ve.setTrangThai(trangThai);
        return veRepository.save(ve);
    }

    @Transactional
    public Ve datVe(String maVe, Long datVeId) {
        Ve ve = veRepository.findByMaVe(maVe)
                .orElseThrow(() -> new RuntimeException("Vé không tồn tại"));

        // Kiểm tra trạng thái vé
        if (ve.getTrangThai() != "Còn trống") {
            throw new RuntimeException("Vé không thể đặt");
        }

        ve.setTrangThai("Đã đặt");
        ve.setDatVe(new DatVe(datVeId));
        return veRepository.save(ve);
    }
}
