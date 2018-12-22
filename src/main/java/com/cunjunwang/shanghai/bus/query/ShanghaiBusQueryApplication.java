package com.cunjunwang.shanghai.bus.query;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cunjunwang.shanghai.bus.query.dao")
public class ShanghaiBusQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShanghaiBusQueryApplication.class, args);
    }

}

