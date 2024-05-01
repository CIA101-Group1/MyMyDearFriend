package com.tibame.group1.admin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EntityScan("com.tibame.group1.db.entity")
@EnableJpaRepositories("com.tibame.group1.db.repository")
public class MmdfAdminApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MmdfAdminApplication.class, args);
    }

    @Override
    public void run(String... args) {}
}
