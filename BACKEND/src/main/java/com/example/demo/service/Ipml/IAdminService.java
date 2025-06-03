package com.example.demo.service.Ipml;

import com.example.demo.entity.account.Admin;

import java.util.List;
import java.util.Optional;

public interface IAdminService {
    List<Admin> findAll();
    Optional<Admin> findById(int id);
    Optional<Admin> findByMaAdmin(String maAdmin);
    Admin save(Admin admin);
    void deleteById(int id);
    void deleteAll();
}
