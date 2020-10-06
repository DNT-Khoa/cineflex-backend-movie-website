package com.khoa.CineFlex;

import com.khoa.CineFlex.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class CineFlexApplication {

	public static void main(String[] args) {
		SpringApplication.run(CineFlexApplication.class, args);
	}

}
