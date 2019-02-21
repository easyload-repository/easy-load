package com.oocl.easyload.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.Entity;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
public class ElMonitorApplication {

  public static void main(String[] args) {
    SpringApplication.run(ElMonitorApplication.class, args);
  }
}
