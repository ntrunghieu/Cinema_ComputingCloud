package com.example.demo.controller;

import com.example.demo.entity.account.NguoiDung;
import com.example.demo.repository.NguoiDungRepository;
import com.example.demo.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin // Cho phép frontend gọi
public class AuthController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        NguoiDung nguoiDung = nguoiDungRepository.findByEmail1(email);

        if (nguoiDung == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email không tồn tại"));
        }

        // Giả sử mật khẩu được mã hoá trong DB
//        if (!passwordEncoder.matches(password, nguoiDung.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("error", "Mật khẩu không đúng"));
//        }

        // (Tuỳ chọn) Tạo JWT Token hoặc trả về thông tin người dùng
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Đăng nhập thành công");
        response.put("nguoiDung", Map.of(
                "id", nguoiDung.getId(),
                "email", nguoiDung.getEmail(),
                "tenNd", nguoiDung.getTenNd(),
                "maNd", nguoiDung.getMaNd(),
                "soDienThoai", nguoiDung.getSoDienThoai(),
                "gioiHanTuoi", nguoiDung.getGioiHanTuoi(),
                "tuoi", nguoiDung.getTuoi()
        ));
        return ResponseEntity.ok(response);

    }

    static class LoginRequest {
        private String email;
        private String password;

        public LoginRequest() {
        }

        public LoginRequest(String email , String password) {
            this.email = email;
            this.password = password;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
