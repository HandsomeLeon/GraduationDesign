package org.design;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages="org.design.mapper")
public class HrmSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrmSystemApplication.class, args);
    }
}
