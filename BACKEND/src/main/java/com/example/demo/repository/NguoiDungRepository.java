package com.example.demo.repository;

import com.example.demo.entity.account.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {
    Optional<NguoiDung> findByEmail(String email);

    @Query(value = "SELECT * FROM nguoi_dung WHERE email=?1", nativeQuery = true)
    NguoiDung findByEmail1(String email);

    Optional<NguoiDung> findByMaNd(String maNd);
    boolean existsByEmail(String email);
    List<NguoiDung> findAll();
    Optional<NguoiDung> findById(Long id);
}

