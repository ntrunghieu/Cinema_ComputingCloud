package com.example.demo.controller;

import com.example.demo.DTO.CreatePaypalOrderRequest;
import com.example.demo.DTO.TicketHistoryResponse;
import com.example.demo.entity.Ghe;
import com.example.demo.entity.LichChieu;
import com.example.demo.entity.Phong;
import com.example.demo.entity.phim.Phim;
import com.example.demo.entity.thanhtoan.ThanhToan;
import com.example.demo.entity.ve.DatVe;
import com.example.demo.entity.ve.Ve;
import com.example.demo.repository.*;
import com.example.demo.service.DatVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {
    @Autowired
    private DatVeService datVeService;

    @Autowired
    private DatVeRepository datVeRepository;

    @Autowired
    private VeRepository veRepository;

    @Autowired
    private GheRepository gheRepository;

    @Autowired
    private PhongRepository phongRepository;

    @Autowired
    private LichChieuRepository lichChieuRepository;

    @Autowired
    private PhimRepository phimRepository;

    @Autowired
    private ThanhToanRepository thanhToanRepository;

    @PostMapping("/create-paypal-order")
    public ResponseEntity<?> createPaypalOrder(@RequestBody CreatePaypalOrderRequest request) {
        try {
            // 1. Tạo đơn đặt vé (DatVe)
            DatVe datVe = datVeService.createBooking(request);

            // 2. Gọi service tích hợp PayPal để tạo đơn hàng (mock/demo ở đây)
            String fakePaypalOrderId = "PAY-" + UUID.randomUUID().toString().replace("-", "").substring(0, 5);;

            // 3. Trả về thông tin đơn hàng cho frontend
            Map<String, Object> response = new HashMap<>();
            response.put("paypalOrderId", fakePaypalOrderId);
            response.put("bookingCode", datVe.getMaDatVe());

            return ResponseEntity.ok(response); //tra ve trang thai thanh cong
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đặt vé thất bại: " + e.getMessage());
        }
    }

    @PostMapping("/payment-success")
    public ResponseEntity<?> confirmPayment(@RequestParam String bookingCode, @RequestParam String paypalOrderId) {
        DatVe datVe = datVeRepository.findByMaDatVe(bookingCode)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã đặt vé"));

        datVe.setTrangThai("Đã thanh toán");
        datVeRepository.save(datVe);

        ThanhToan payment = new ThanhToan();
        payment.setMaDatVe(bookingCode);
        payment.setNgayThanhToan(LocalDateTime.now());
        payment.setSoTien(datVe.getTongTien());
        payment.setHinhThuc("PayPal");
        payment.setMaThanhToan(paypalOrderId);
        payment.setTrangThai("Thành công");

        thanhToanRepository.save(payment);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Đã xác nhận thanh toán.");

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/history")
//    public ResponseEntity<List<TicketHistoryResponse>> getTicketHistory() {
//        try {
//            String currentUserId = "KH001"; // Tạm hardcode
//
//            // Lấy danh sách đặt vé đã thanh toán
//            List<DatVe> bookings = datVeRepository.findByMaKhachHangAndTrangThaiOrderByNgayDatDesc(
//                    currentUserId, "Đã thanh toán"
//            );
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            List<TicketHistoryResponse> historyList = new ArrayList<>();
//
//            for (DatVe booking : bookings) {
//                List<Ve> danhSachVe = veRepository.findByDatVeId(booking.getId());
//                if (danhSachVe.isEmpty()) continue;
//
//                Ve firstTicket = danhSachVe.get(0);
//                LichChieu lichChieu = lichChieuRepository.findById(firstTicket.getLichChieu().getId()).orElse(null);
//                if (lichChieu == null) continue;
//
//                Phim phim = phimRepository.findById(lichChieu.getPhim().getId()).orElse(null);
//                Phong phong = phongRepository.findById(lichChieu.getPhong().getId()).orElse(null);
//
//                List<String> seatNames = danhSachVe.stream()
//                        .map(ve -> {
//                            Ghe ghe = gheRepository.findById(ve.getGhe().getId()).orElse(null);
//                            return ghe != null ? ghe.getHang() + ghe.getSo() : "N/A";
//                        })
//                        .collect(Collectors.toList());
//
//                TicketHistoryResponse historyItem = new TicketHistoryResponse();
//                historyItem.setMovieTitle(phim != null ? phim.getTenPhim() : "N/A");
//                historyItem.setShowtime(lichChieu.getNgayChieu() + " " + lichChieu.getGioBatDau());
//                historyItem.setSeats(seatNames);
//                historyItem.setTotalAmount(booking.getTongTien());
//                historyItem.setPurchaseDate(booking.getNgayDat().format(formatter));
//                historyItem.setRoomName(phong != null ? phong.getTenPhong() : "N/A");
//
//                historyList.add(historyItem);
//            }
//
//            return ResponseEntity.ok(historyList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    @GetMapping("/history")
    public ResponseEntity<List<TicketHistoryResponse>> getTicketHistory() {
        try {
            String currentUserId = "KH001"; // Tạm hardcode

            // Lấy danh sách mã đặt vé đã thanh toán từ bảng thanh_toan
            List<String> maDatVeDaThanhToan = thanhToanRepository.findMaDatVeByTrangThai("Thành công");

            if (maDatVeDaThanhToan.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            // Lấy danh sách DatVe theo maKhachHang và có maDatVe trong danh sách đã thanh toán
            List<DatVe> bookings = datVeRepository.findByMaKhachHangAndMaDatVeInOrderByNgayDatDesc(
                    currentUserId, maDatVeDaThanhToan
            );

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            List<TicketHistoryResponse> historyList = new ArrayList<>();

            for (DatVe booking : bookings) {
                List<Ve> danhSachVe = veRepository.findByDatVeId(booking.getId());
                if (danhSachVe.isEmpty()) continue;

                Ve firstTicket = danhSachVe.get(0);
                LichChieu lichChieu = lichChieuRepository.findById(firstTicket.getLichChieu().getId()).orElse(null);
                if (lichChieu == null) continue;

                Phim phim = phimRepository.findById(lichChieu.getPhim().getId()).orElse(null);
                Phong phong = phongRepository.findById(lichChieu.getPhong().getId()).orElse(null);

                List<String> seatNames = danhSachVe.stream()
                        .map(ve -> {
                            Ghe ghe = gheRepository.findById(ve.getGhe().getId()).orElse(null);
                            return ghe != null ? ghe.getHang() + ghe.getSo() : "N/A";
                        })
                        .collect(Collectors.toList());

                TicketHistoryResponse historyItem = new TicketHistoryResponse();
                historyItem.setMovieTitle(phim != null ? phim.getTenPhim() : "N/A");
                historyItem.setShowtime(lichChieu.getNgayChieu() + " " + lichChieu.getGioBatDau());
                historyItem.setSeats(seatNames);
                historyItem.setTotalAmount(booking.getTongTien());
                historyItem.setPurchaseDate(booking.getNgayDat().format(formatter));
                historyItem.setRoomName(phong != null ? phong.getTenPhong() : "N/A");

                historyList.add(historyItem);
            }

            return ResponseEntity.ok(historyList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


//    @GetMapping("/history")
//    public ResponseEntity<List<TicketHistoryResponse>> getTicketHistory() {
//        try {
//            String currentUserId = "KH001"; // Giả lập người dùng
//
//            List<DatVe> bookings = datVeRepository.findByMaKhachHangAndTrangThaiOrderByNgayDatDesc(
//                    currentUserId, "Đã thanh toán"
//            );
//
//            List<TicketHistoryResponse> historyList = new ArrayList<>();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//            for (DatVe booking : bookings) {
//                TicketHistoryResponse historyItem = new TicketHistoryResponse();
//
//                // Truy xuất danh sách vé theo mã đặt vé
//                List<Ve> danhSachVe = veRepository.findByMaDatVe(booking.getMaDatVe());
//
//                // Lấy danh sách tên ghế
//                List<String> seatNames = danhSachVe.stream()
//                        .map(ve -> {
//                            Ghe ghe = (Ghe) gheRepository.findGheByMaGhe(ve.getId());
//                            return ghe != null ? ghe.getMaGhe() : "N/A";
//                        })
//                        .collect(Collectors.toList());
//
//                // Lấy mã lịch chiếu từ vé đầu tiên (giả định cùng một suất chiếu)
//                Long maLichChieu = danhSachVe.get(0).getLichChieu().getId();
//                LichChieu lichChieu = (LichChieu) lichChieuRepository.findByMaLichChieu1(maLichChieu);
//                Phim phim = phimRepository.findById(lichChieu.getPhim().getId()).orElse(null);
//                Phong phong = phongRepository.findById(lichChieu.getPhong().getId()).orElse(null);
//
//                String showtime = lichChieu.getNgayChieu().toString() + " " + lichChieu.getGioBatDau().toString();
//
//                historyItem.setMovieTitle(phim != null ? phim.getTenPhim() : "N/A");
//                historyItem.setShowtime(showtime);
//                historyItem.setSeats(seatNames);
//                historyItem.setTotalAmount(booking.getTongTien());
//                historyItem.setPurchaseDate(booking.getNgayDat().format(formatter));
//                historyItem.setRoomName(phong != null ? phong.getTenPhong() : "N/A");
//
//                historyList.add(historyItem);
//            }
//
//            return ResponseEntity.ok(historyList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }


}
