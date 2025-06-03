package com.example.demo.controller;

import com.example.demo.DTO.CapNhatNguoiDungDTO;
import com.example.demo.DTO.DangKyDTO;
import com.example.demo.DTO.DangNhapDTO;
import com.example.demo.entity.account.NguoiDung;
import com.example.demo.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nguoi-dung")
public class NguoiDungController {

    @Autowired
    private NguoiDungService nguoiDungService;

    // Lấy tất cả người dùng
    @GetMapping
    public ResponseEntity<List<NguoiDung>> getAllNguoiDung() {
        List<NguoiDung> nguoiDungs = nguoiDungService.findAll();
        return new ResponseEntity<>(nguoiDungs, HttpStatus.OK);
    }

    // Lấy người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<NguoiDung> getNguoiDungById(@PathVariable("id") Long id) {
        Optional<NguoiDung> nguoiDungData = nguoiDungService.findById(id);

        if (nguoiDungData.isPresent()) {
            return new ResponseEntity<>(nguoiDungData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Tìm kiếm người dùng theo email
    @GetMapping("/search")
    public ResponseEntity<NguoiDung> getNguoiDungByEmail(@RequestParam("email") String email) {
        Optional<NguoiDung> nguoiDungData = nguoiDungService.findByEmail(email);

        if (nguoiDungData.isPresent()) {
            return new ResponseEntity<>(nguoiDungData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Tạo người dùng mới
    @PostMapping
    public ResponseEntity<NguoiDung> createNguoiDung(@RequestBody NguoiDung nguoiDung) {
        try {
            NguoiDung _nguoiDung = nguoiDungService.save(nguoiDung);
            return new ResponseEntity<>(_nguoiDung, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cập nhật thông tin người dùng
//    @PutMapping("/{id}")
//    public ResponseEntity<NguoiDung> updateNguoiDung(@PathVariable("id") Long id, @RequestBody NguoiDung nguoiDung) {
//        Optional<NguoiDung> nguoiDungData = nguoiDungService.findById(id);
//
//        if (nguoiDungData.isPresent()) {
//            NguoiDung _nguoiDung = nguoiDungData.get();
//            _nguoiDung.setEmail(nguoiDung.getEmail());
//            _nguoiDung.setMatKhau(nguoiDung.getMatKhau());
//            _nguoiDung.setTenNd(nguoiDung.getTenNd());
//            _nguoiDung.setMaNd(nguoiDung.getMaNd());
//            _nguoiDung.setSoDienThoai(nguoiDung.getSoDienThoai());
//            _nguoiDung.setNgaySinh(nguoiDung.getNgaySinh());
//            _nguoiDung.setGioiTinh(nguoiDung.getGioiTinh());
//            _nguoiDung.setTuoi(nguoiDung.getTuoi());
//            _nguoiDung.setGioiHanTuoi(nguoiDung.getGioiHanTuoi());
//            _nguoiDung.setDoTuoiXacThuc(nguoiDung.isDoTuoiXacThuc());
//
//            return new ResponseEntity<>(nguoiDungService.save(_nguoiDung), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    // Xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNguoiDung(@PathVariable("id") Long id) {
        try {
            nguoiDungService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Xóa tất cả người dùng
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllNguoiDungs() {
        try {
            nguoiDungService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Xác thực tuổi người dùng
    @PatchMapping("/{id}/xac-thuc-tuoi")
    public ResponseEntity<NguoiDung> xacThucTuoi(@PathVariable("id") Long id) {
        Optional<NguoiDung> nguoiDungData = nguoiDungService.findById(id);

        if (nguoiDungData.isPresent()) {
            NguoiDung _nguoiDung = nguoiDungData.get();
            _nguoiDung.setDoTuoiXacThuc(true);
            return new ResponseEntity<>(nguoiDungService.save(_nguoiDung), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/dang-ky")
    public ResponseEntity<?> register(@RequestBody DangKyDTO request) {
        NguoiDung user = nguoiDungService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/dang-nhap")
    public ResponseEntity<?> login(@RequestBody DangNhapDTO request) {
        NguoiDung user = nguoiDungService.login(request);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/cap-nhat/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody CapNhatNguoiDungDTO request) {
        NguoiDung user = nguoiDungService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }
}
