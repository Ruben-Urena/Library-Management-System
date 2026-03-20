package com.ruben.sigebi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SigebiApplication {
	public static void main(String[] args) {
		SpringApplication.run(SigebiApplication.class, args);
	}
}
