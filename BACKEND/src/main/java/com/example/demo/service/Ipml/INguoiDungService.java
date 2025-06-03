package com.example.demo.service.Ipml;

import com.example.demo.DTO.CapNhatNguoiDungDTO;
import com.example.demo.DTO.DangKyDTO;
import com.example.demo.DTO.DangNhapDTO;
import com.example.demo.entity.account.NguoiDung;

import java.util.List;
import java.util.Optional;

public interface INguoiDungService {

    NguoiDung register(DangKyDTO request);

    NguoiDung login(DangNhapDTO request);

    NguoiDung updateUser(Long id, CapNhatNguoiDungDTO request);
    List<NguoiDung> findAll();
    Optional<NguoiDung> findById(Long id);
    Optional<NguoiDung> findByEmail(String email);
    NguoiDung save(NguoiDung nguoiDung);
    void deleteById(Long id);
    void deleteAll();

}
