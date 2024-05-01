package com.tibame.group1.web;

import com.tibame.group1.web.service.InitTestMemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableCaching
@EntityScan("com.tibame.group1.db.entity")
@EnableJpaRepositories("com.tibame.group1.db.repository")
public class MmdfWebApplication implements CommandLineRunner {

    @Autowired private InitTestMemberService initTestMemberService;

    public static void main(String[] args) {
        SpringApplication.run(MmdfWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        initTestMemberService.init();
    }
}
