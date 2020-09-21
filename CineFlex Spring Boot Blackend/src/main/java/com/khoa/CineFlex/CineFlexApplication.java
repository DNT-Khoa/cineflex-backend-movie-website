package com.khoa.CineFlex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class CineFlexApplication {

	public static void main(String[] args) {
		SpringApplication.run(CineFlexApplication.class, args);
	}

}
