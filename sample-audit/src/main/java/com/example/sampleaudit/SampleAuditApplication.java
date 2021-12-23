package com.example.sampleaudit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SampleAuditApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleAuditApplication.class, args);
	}

}
