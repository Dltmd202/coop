package org.kt.parttime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 근장마다 시급이 다를 수 있는 상황
 */
@EnableJpaAuditing
@SpringBootApplication
public class PartTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartTimeApplication.class, args);
    }

}
