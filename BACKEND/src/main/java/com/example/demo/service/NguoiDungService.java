package com.example.demo.service;

import com.example.demo.DTO.CapNhatNguoiDungDTO;
import com.example.demo.DTO.DangKyDTO;
import com.example.demo.DTO.DangNhapDTO;
import com.example.demo.entity.account.NguoiDung;
import com.example.demo.repository.NguoiDungRepository;
import com.example.demo.service.Ipml.INguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class NguoiDungService implements INguoiDungService {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public NguoiDung register(DangKyDTO request) {
        if (nguoiDungRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng!");
        }

        NguoiDung user = new NguoiDung();
        user.setEmail(request.getEmail());
        user.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        user.setTenNd(request.getTenNd());
        user.setSoDienThoai(request.getSoDienThoai());

        return nguoiDungRepository.save(user);
    }

    @Override
    public NguoiDung login(DangNhapDTO request) {
        Optional<NguoiDung> userOpt = nguoiDungRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getMatKhau(), userOpt.get().getMatKhau())) {
            throw new RuntimeException("Sai email hoặc mật khẩu!");
        }

        return userOpt.get();
    }

    @Override
    public NguoiDung updateUser(Long id, CapNhatNguoiDungDTO request) {
        NguoiDung user = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        if (request.getTenNd() != null) user.setTenNd(request.getTenNd());
        if (request.getSoDienThoai() != null) user.setSoDienThoai(request.getSoDienThoai());
        if (request.getGioiTinh() != null) user.setGioiTinh(request.getGioiTinh());
        if (request.getTuoi() != null) user.setTuoi(request.getTuoi());

        return nguoiDungRepository.save(user);
    }

    @Override
    public List<NguoiDung> findAll() {
        return nguoiDungRepository.findAll();
    }

    @Override
    public Optional<NguoiDung> findById(Long id) {
        return nguoiDungRepository.findById(id);
    }

    @Override
    public Optional<NguoiDung> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public NguoiDung save(NguoiDung nguoiDung) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}