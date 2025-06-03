package com.example.demo;

import com.example.demo.entity.account.NguoiDung;
import com.example.demo.repository.NguoiDungRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class NguoiDungRepositoryTest {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;



    @Test
    void testFindAll() {

        System.out.println("hello");
        //Lấy tất cả người dùng
        List<NguoiDung> users = nguoiDungRepository.findAll();

        users.forEach(System.out::println);
        System.out.println("test");

        //Assert: Kiểm tra số lượng dữ liệu
        assertThat(users).hasSize(2);
    }
}

