package com.farmSphere;

import com.farmSphere.auth.data.model.GENDER;
import com.farmSphere.auth.data.model.ROLE;
import com.farmSphere.auth.data.model.User;
import com.farmSphere.auth.data.repository.UserRepository;
import com.farmSphere.auth.util.PasswordHash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = {
        "com.farmSphere.auth.data.model",
        "com.farmSphere.estate.data.model",
        "com.farmSphere.farming.data.model",
        "com.farmSphere.tool.data.model",
        "com.farmSphere.marketplace.data.model",
        "com.farmSphere.investment.data.model",
        "com.farmSphere.core"
})
@EnableJpaRepositories(basePackages = {
        "com.farmSphere.auth.data.repository",
        "com.farmSphere.estate.data.repository",
        "com.farmSphere.tool.data.repository",
        "com.farmSphere.farming.data.repository",
        "com.farmSphere.marketplace.data.repository",
        "com.farmSphere.investment.data.repository",
        "com.farmSphere.core",
})
public class FarmSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmSphereApplication.class, args);
    }

    @Value("${app.admin.email}")
    private String adminEmail;
    @Value("${app.admin.password}")
    private String adminPassword;
    @Value("${app.admin.address}")
    private String adminAddress;
    @Value("${app.admin.phoneNumber}")
    private String adminPhoneNumber;

    @Autowired
    private UserRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void createAdminOnReady() {
        log.info(" App fully ready - creating admin");

        try {
            if(!repository.existsByEmail(adminEmail)) {
                log.info(">>> Admin not found, creating...");
                User admin = new User();
                admin.setFirstName("Mary");
                admin.setSecondName("Kachelhoffer");
                admin.setEmail(adminEmail);
                admin.setPassword(PasswordHash.hash(adminPassword));
                admin.setAddress(adminAddress);
                admin.setRoles(Collections.singleton(ROLE.ADMIN));
                admin.setAge(30);
                admin.setPhoneNumber(adminPhoneNumber);
                admin.setActive(true);
                admin.setGender(GENDER.MALE);
                admin.setVerified(true);
                admin.setDateCreated(LocalDateTime.now());
                admin.setLastLogin(LocalDateTime.now());

                repository.save(admin);
                log.info(" Admin created successfully");
            }else {
                log.info(" Admin already exists: {}", adminEmail);
            }
        } catch (Exception e) {
            log.warn(" Admin creation failed (safe): {}", e.getMessage());
        }
    }


    @Scheduled(fixedRate = 150000)
    public void keepAlive() {
        log.debug(" FarmSphere alive: {}", LocalDateTime.now());
    }

}
