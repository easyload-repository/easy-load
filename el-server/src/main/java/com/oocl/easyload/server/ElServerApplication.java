package com.oocl.easyload.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
@EnableScheduling
@EntityScan(basePackages = {"com.oocl.easyload.model.entity"})
public class ElServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ElServerApplication.class, args);
  }
}
