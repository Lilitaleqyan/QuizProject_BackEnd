package com.example.demo.admin;

import com.example.demo.entity.Admin;
import com.example.demo.entity.enums.Role;
import com.example.demo.securityConfig.SecurityConfiguration;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminComponent implements ApplicationRunner {
    private final AdminService adminService;
    private final SecurityConfiguration securityConfiguration;

    @Value("${app.admin.username}")
    private String userName;

    @Value("${app.admin.password}")
    private String password;

    public AdminComponent(AdminService adminService, SecurityConfiguration securityConfiguration) {
        this.adminService = adminService;
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
       if(adminService.existsByUserName(userName)) {
           return;
       }
       Admin admin = new Admin();
       admin.setUserName(userName);
       admin.setPassword(securityConfiguration.passwordEncoder().encode(password));
       admin.setRole(Role.ADMIN);
       adminService.save(admin);
    }
}
