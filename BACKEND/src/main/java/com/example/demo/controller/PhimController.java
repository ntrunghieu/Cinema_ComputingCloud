package com.example.demo.controller;

import com.example.demo.entity.account.TuoiGioiHan;
import com.example.demo.entity.phim.Phim;
import com.example.demo.repository.PhimRepository;
import com.example.demo.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/phim")
@CrossOrigin(origins = "http://localhost:4200")
public class PhimController {

    @Autowired
    private PhimService   phimService;

    @Autowired
    private PhimRepository phimRepository;

    @GetMapping
    public ResponseEntity<List<Phim>> getAllMovies() {
        try {
            List<Phim> movies = phimService.layDanhSachPhim();
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phim> getMovieById(@PathVariable Long id) {
        try {
            Optional<Phim> movie = phimRepository.findById(id);
            return movie.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public String generateMovieCode() {
        // Giả sử bạn có một bảng phim và lấy mã phim cuối cùng
        String latestCode = phimRepository.findLatestCode(); // Lấy mã phim mới nhất từ DB, ví dụ "MOV002"
        if (latestCode == null) {
            return "MOV001";  // Trường hợp chưa có phim nào
        }

        // Tạo mã phim tiếp theo (MOV003 từ MOV002)
        String prefix = "MOV";
        int number = Integer.parseInt(latestCode.substring(3)) + 1; // Lấy số cuối của mã phim, cộng thêm 1
        return prefix + String.format("%03d", number); // Trả về "MOV003"
    }

    @PostMapping
    public ResponseEntity<Phim> createMovie(@RequestBody Phim movie) {
        try {
            // Tạo mã phim tự động
            movie.setMaPhim(generateMovieCode());

            Phim createdMovie = phimService.taoPhim(movie);
            return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            e.printStackTrace();  // In ra lỗi chi tiết
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();  // In ra lỗi chi tiết
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @PostMapping
//    public ResponseEntity<Phim> createMovie(@RequestBody Phim movie) {
//        try {
//            Phim createdMovie = phimService.taoPhim(movie);
//            return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            e.printStackTrace();  // In ra lỗi chi tiết
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        } catch (Exception e) {
//            e.printStackTrace();  // In ra lỗi chi tiết
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Phim> updateMovie(@PathVariable Long id, @RequestBody Phim movieDetails) {
        try {
            movieDetails.setId(id);
            Phim updatedMovie = phimService.capNhatPhim(movieDetails);
            return ResponseEntity.ok(updatedMovie);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        try {
            phimService.xoaPhim(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

