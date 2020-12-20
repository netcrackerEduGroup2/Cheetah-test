package com.ncedu.cheetahtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CheetahTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheetahTestApplication.class, args);
	}
}
