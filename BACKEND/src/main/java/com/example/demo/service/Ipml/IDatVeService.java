package com.example.demo.service.Ipml;

import com.example.demo.DTO.CreatePaypalOrderRequest;
import com.example.demo.entity.ve.DatVe;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IDatVeService {
    DatVe createBooking(CreatePaypalOrderRequest request);
    DatVe taoMoiDatVe(DatVe datVe);

    List<DatVe> layDanhSachDatVe();

    List<DatVe> timDatVeTheoKhachHang(String maKhachHang);

    Optional<DatVe> timDatVeTheoMa(String maDatVe);

    List<DatVe> timDatVeTheoTrangThai(String trangThai);

    List<DatVe> timDatVeTrongKhoangThoiGian(LocalDateTime start, LocalDateTime end);

    DatVe capNhatTrangThaiDatVe(String maDatVe, String trangThai);
}
