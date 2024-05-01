package com.tibame.group1.admin;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("config")
@Getter
@Setter
public class ConfigProperties extends com.tibame.group1.common.ConfigProperties {}
