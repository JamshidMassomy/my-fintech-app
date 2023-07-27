package com.fintech.config;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.fintech.repository.mapper")
public class MyBatisConfig { }
