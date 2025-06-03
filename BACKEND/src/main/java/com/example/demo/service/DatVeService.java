package com.example.demo.service;

import com.example.demo.DTO.CreatePaypalOrderRequest;
import com.example.demo.entity.Ghe;
import com.example.demo.entity.ve.DatVe;
import com.example.demo.entity.ve.Ve;
import com.example.demo.repository.*;
import com.example.demo.service.Ipml.IDatVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DatVeService implements IDatVeService {
    @Autowired
    private DatVeRepository datVeRepository;

    @Autowired
    private VeRepository veRepo;

    @Autowired
    private LichChieuRepository lichChieuRepo;

    @Autowired
    private GheRepository gheRepo;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    @Transactional
    public DatVe createBooking(CreatePaypalOrderRequest request) {

        // 1. Kiểm tra ghế trùng lặp
        Set<String> uniqueSeats = new HashSet<>(request.getSelectedSeats());
        if (uniqueSeats.size() < request.getSelectedSeats().size()) {
            throw new IllegalArgumentException("Có ghế bị chọn trùng lặp.");
        }

        // 2. Lấy ID của các ghế từ mã ghế
        List<Long> gheIds = new ArrayList<>();
        for (String seatCode : uniqueSeats) {
            Ghe ghe = gheRepo.findByPhongIdAndMaGhe(request.getRoomId(), seatCode)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy ghế " + seatCode));
            gheIds.add(ghe.getId());
        }

        // 3. Kiểm tra xem có đủ số vé còn trống không
        int availableTicketCount = veRepo.countEmptyTicketsByLichChieuAndGheIds(request.getScheduleId(), gheIds);
        if (availableTicketCount < gheIds.size()) {
            throw new RuntimeException("Một hoặc nhiều ghế đã được đặt cho lịch chiếu này.");
        }

        // 4. Tạo đơn đặt vé
        String bookingCode = "BK" + System.currentTimeMillis();
        DatVe datVe = new DatVe();
        datVe.setMaDatVe(bookingCode);
        datVe.setNgayDat(LocalDateTime.now());
        datVe.setTongTien(request.getTotalAmount());
        datVe.setTrangThai("Chưa thanh toán");
        datVe.setMaKhachHang("KH001");
        datVe = datVeRepository.save(datVe);

        // 5. Cập nhật các vé đã chọn
        int updatedTickets = veRepo.updateTicketsForBooking(
                datVe.getId(),
                "Đã bán",
                request.getScheduleId(),
                gheIds
        );

        // Kiểm tra số vé đã cập nhật
        if (updatedTickets != gheIds.size()) {
            throw new RuntimeException("Không thể cập nhật tất cả các vé đã chọn. Có thể một số ghế đã được đặt.");
        }

        return datVe;


        // 1. Kiểm tra ghế đã được đặt chưa
//        Set<String> uniqueSeats = new HashSet<>(request.getSelectedSeats());
//        if (uniqueSeats.size() < request.getSelectedSeats().size()) {
//            throw new RuntimeException("Có ghế bị chọn trùng lặp.");
//        }
//
//        // Kiểm tra từng ghế
//        for (String seatCode : uniqueSeats) {
//            Ghe ghe = gheRepo.findByPhongIdAndMaGhe(request.getRoomId(), seatCode)
//                    .orElseThrow(() -> new RuntimeException("Không tìm thấy ghế " + seatCode));
//
//            // Kiểm tra xem ghế đã được đặt chưa
//            Optional<Ve> existingTicket = veRepo.findVeDaDat(request.getScheduleId(), ghe.getId());
//            if (existingTicket.isPresent()) {
//                throw new RuntimeException("Ghế " + seatCode + " đã được đặt cho lịch chiếu này.");
//            }
//        }
//
//        // 2. Tạo đơn đặt vé
//        String bookingCode = "BK" + System.currentTimeMillis();
//        DatVe datVe = new DatVe();
//        datVe.setMaDatVe(bookingCode);
//        datVe.setNgayDat(LocalDateTime.now());
//        datVe.setTongTien(request.getTotalAmount());
//        datVe.setTrangThai("Chưa thanh toán");
//        datVe.setMaKhachHang("KH001"); // bạn có thể truyền từ client hoặc lấy từ session
//        datVe = datVeRepository.save(datVe);
//        for (String seatCode : uniqueSeats) {
//            Ghe ghe = gheRepo.findByMaGheAndPhongId(seatCode, request.getRoomId())
//                    .orElseThrow(() -> new RuntimeException("Không tìm thấy ghế " + seatCode));
//
//            // Đã kiểm tra ghế được đặt ở trên
//
//            Ve ve = new Ve();
//            ve.setGhe(ghe);
//            ve.setLichChieu(lichChieuRepo.findById(request.getScheduleId()).orElseThrow());
//            ve.setDatVe(datVe);
//            ve.setMaVe("TK" + UUID.randomUUID().toString().replace("-", "").substring(0, 17));
//            ve.setGia(new BigDecimal("120000"));
//            ve.setTrangThai("Đã bán");
//            ve.setMaLoaiVe("THUONG");
//
//            veRepo.save(ve);
//        }
//
//
//
//        return datVe;
    }

    @Transactional
    public DatVe taoMoiDatVe(DatVe datVe) {
        // Kiểm tra khách hàng có tồn tại không
        khachHangRepository.findByMaKh(datVe.getMaKhachHang())
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));

        // Kiểm tra trùng mã đặt vé
        if (datVeRepository.findByMaDatVe(datVe.getMaDatVe()).isPresent()) {
            throw new RuntimeException("Mã đặt vé đã tồn tại");
        }


        // Đặt ngày đặt vé là thời gian hiện tại nếu chưa được set
        if (datVe.getNgayDat() == null) {
            datVe.setNgayDat(LocalDateTime.now());
        }

        return datVeRepository.save(datVe);
    }

    public List<DatVe> layDanhSachDatVe() {
        return datVeRepository.findAll();
    }

    public List<DatVe> timDatVeTheoKhachHang(String maKhachHang) {
        return datVeRepository.findByMaKhachHang(maKhachHang);
    }

    public Optional<DatVe> timDatVeTheoMa(String maDatVe) {
        return datVeRepository.findByMaDatVe(maDatVe);
    }

    public List<DatVe> timDatVeTheoTrangThai(String trangThai) {
        return datVeRepository.findByTrangThai(trangThai);
    }

    public List<DatVe> timDatVeTrongKhoangThoiGian(LocalDateTime start, LocalDateTime end) {
        return datVeRepository.findByNgayDatBetween(start, end);
    }

    @Transactional
    public DatVe capNhatTrangThaiDatVe(String maDatVe, String trangThai) {
        DatVe datVe = datVeRepository.findByMaDatVe(maDatVe)
                .orElseThrow(() -> new RuntimeException("Đặt vé không tồn tại"));

        datVe.setTrangThai(trangThai);
        return datVeRepository.save(datVe);
    }
}
