package com.example.demo.controller;

import com.example.demo.entity.Ghe;
import com.example.demo.entity.LichChieu;
import com.example.demo.entity.phim.Phim;
import com.example.demo.repository.GheRepository;
import com.example.demo.repository.LichChieuRepository;
import com.example.demo.repository.PhimRepository;
import com.example.demo.service.LichChieuService;
import com.example.demo.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lichchieu")
@CrossOrigin(origins = "http://localhost:4200")
public class LichChieuController {
    @Autowired
    private LichChieuService lichChieuService;

    @Autowired
    private LichChieuRepository lichChieuRepository;

    @Autowired
    private GheRepository gheRepository;

    @GetMapping
    public List<LichChieu> getAllLichChieu() {
//        List<LichChieu> list = li.layDanhSachPhim();
//        list.forEach(phim -> {
//            System.out.println(phim.getGioiHanTuoi()); // check nếu null hoặc có dữ liệu
//
//        });

        return lichChieuRepository.findAll();

    }

    @GetMapping("/{id}")
    public List<LichChieu> getLichChieuById(@PathVariable Long id) {
        return lichChieuRepository.findLichChieuByIdPhim1(id);
    }

    @GetMapping("/{id}/ghe")
    public ResponseEntity<?> getDanhSachGheTheoLichChieu(@PathVariable Long id) {
        Optional<LichChieu> optional = lichChieuRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy lịch chiếu");
        }
    }
}
