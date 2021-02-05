package org.design;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages="org.design.mapper")
public class HrmSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrmSystemApplication.class, args);
    }
}
