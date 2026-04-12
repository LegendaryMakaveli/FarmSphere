package com.farmSphere;

import com.farmSphere.auth.data.model.GENDER;
import com.farmSphere.auth.data.model.ROLE;
import com.farmSphere.auth.data.model.User;
import com.farmSphere.auth.data.repository.UserRepository;
import com.farmSphere.auth.util.PasswordHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.Collections;


@SpringBootApplication
@EntityScan(basePackages = {
        "com.farmSphere.auth.data.model",
        "com.farmSphere.estate.data.model",
        "com.farmSphere.tool.data.model",
        "com.farmSphere.marketplace.data.model",
        "com.farmSphere.investment.data.model",
        "com.farmSphere.core"
})
@EnableJpaRepositories(basePackages = {
        "com.farmSphere.auth.data.repository",
        "com.farmSphere.estate.data.repository",
        "com.farmSphere.tool.data.repository",
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

    @Bean
    CommandLineRunner createAdmin(UserRepository repository) {
        return args -> {
            if(!repository.existsByEmail(adminEmail)) {
                User admin = new User();
                admin.setFirstName("Brian");
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
            }
        };
    }

}
