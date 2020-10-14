package com.khoa.CineFlex;

import com.khoa.CineFlex.controller.AdminController;
import com.khoa.CineFlex.controller.AuthController;
import com.khoa.CineFlex.controller.CategoryController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CineFlexApplicationTests {

	@Autowired
	private AdminController adminController;

	@Autowired
	private AuthController authController;

	@Autowired
	private CategoryController categoryController;

	@Test
	void contextLoads() {
		Assertions.assertThat(adminController).isNotNull();
		Assertions.assertThat(authController).isNotNull();
		Assertions.assertThat(categoryController).isNotNull();
	}

}
