package com.example.demo.repository;

import com.example.demo.entity.account.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    Optional<KhachHang> findByMaKh(String maKh);
    List<KhachHang> findByTrangThai(String trangThai);
}
