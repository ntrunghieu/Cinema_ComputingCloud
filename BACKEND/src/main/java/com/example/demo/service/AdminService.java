package com.example.demo.service;

import com.example.demo.entity.account.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.Ipml.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Admin> findById(int id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Admin> findByMaAdmin(String maAdmin) {
        return adminRepository.findByMaAdmin(maAdmin);
    }

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void deleteById(int id) {
        adminRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        adminRepository.deleteAll();
    }
}
