package com.example.demo;

import com.example.demo.entity.account.NguoiDung;
import com.example.demo.repository.NguoiDungRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private NguoiDungRepository nguoiDungRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testFindAll() {


		// 🔹 Act: Lấy tất cả người dùng
		List<NguoiDung> users = nguoiDungRepository.findAll();

		// 🔹 Assert: Kiểm tra số lượng dữ liệu
		assertThat(users).isEmpty();
	}

}
