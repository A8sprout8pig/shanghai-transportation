package com.cunjunwang.shanghai.transportation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cunjunwang.shanghai.transportation.dao")
public class ShanghaiTransportationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShanghaiTransportationApplication.class, args);
    }

}

