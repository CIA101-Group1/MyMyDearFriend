package com.tibame.group1.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan("com.tibame.group1.db.dao")
public class CustomDaoScanConfig {
}